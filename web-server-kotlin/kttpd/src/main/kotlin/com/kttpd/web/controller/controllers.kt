package com.kttpd.web.controller

import com.google.common.io.Files.getFileExtension
import com.kttpd.common.Constants
import com.kttpd.common.Method
import com.kttpd.common.Status
import com.kttpd.exception.UnsupportedMethodException
import com.kttpd.extensionToContentType
import com.kttpd.getDocumentRoot
import com.kttpd.message.Request
import com.kttpd.message.Response
import com.kttpd.readErrorPage
import com.kttpd.web.RequestMapping
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.Arrays

/**
 * コントローラの抽象クラス。
 * このクラスの実装クラスは POST リクエストの処理
 */
abstract class Controller {
    /**
     * POST リクエストを受け取った場合に実行されるメソッド
     * @param request HTTP Request
     * @return HTTP Response
     */
    abstract fun doPost(request: Request): Response

    /**
     * POST リクエストを受け取った場合に実行されるメソッド
     * @param request HTTP Request
     * @return HTTP Response
     */
    abstract fun doGet(request: Request): Response

    /**
     * request の HTTP メソッドによってメソッドの振り分けを行い、レスポンスを返す。
     * HTTP メソッドは GET, POST にのみ対応。
     * @param request HTTP Request
     * @return HTTP Response
     * @throws UnsupportedMethodException GET, POST 以外のメソッドのリクエストが渡された場合
     */
    fun handle(request: Request): Response {
        val response: Response = when (val method: Method = request.method) {
            Method.GET -> doGet(request)
            Method.POST -> doPost(request)
            else -> throw UnsupportedMethodException(method.name)
        }
        return response
    }
}

/**
 * リクエストボディをそのまま返すコントローラ
 */
@RequestMapping("/webapp/echo")
class EchoController : Controller() {
    override fun doGet(request: Request): Response {
        return echo(request)
    }

    override fun doPost(request: Request): Response {
        return echo(request)
    }

    private fun echo(request: Request): Response {
        val response = Response(version = Constants.HTTP_VERSION_1, status = Status.OK)
        response.body = request.body
        response.addHeaderField("Content-Length", response.body.size.toString())
        return response
    }
}


/**
 * 通常の HTTP リクエストを処理するコントローラ
 */
class BasicHttpController : Controller() {
    override fun doGet(request: Request): Response {
        var target: Path = Paths.get(getDocumentRoot(), request.target).normalize()

        // ドキュメントルート以下のみアクセス可能にする
        if (!target.startsWith(getDocumentRoot())) {
            return Response(version = Constants.HTTP_VERSION_1, status = Status.BAD_REQUEST)
        }
        if (Files.isDirectory(target)) {
            target = target.resolve("index.html")
        }
        var response: Response
        try {
            response = Response(version = Constants.HTTP_VERSION_1, status = Status.OK)
            response.body = Files.readAllBytes(target)
            response.addHeaderField("Content-Length", response.body.size.toString())
            val ext: String = getFileExtension(target.fileName.toString())
            val contentType: String = extensionToContentType(ext)
            response.addHeaderField("Content-Type", contentType)
        } catch (e: IOException) {
            response = Response(version = Constants.HTTP_VERSION_1, status = Status.NOT_FOUND)
            response.body = readErrorPage(Status.NOT_FOUND)
            response.addHeaderField("Content-Length", response.body.size.toString())
        }
        return response
    }

    override fun doPost(request: Request): Response {
        // nothing to do
        println("POST body: " + String(request.body, StandardCharsets.UTF_8))
        return Response(version = Constants.HTTP_VERSION_1, status = Status.NO_CONTENT)
    }
}

/**
 * BasicHttpController と同様の処理を行うがレスポンスボディを 20 byte 単位で分割する
 */
@RequestMapping("/chunked")
class ChunkedResponseController : Controller() {
    private val basicController: Controller

    init {
        basicController = BasicHttpController()
    }

    override fun doGet(request: Request): Response {
        val response: Response = basicController.doGet(request)
        return chunk(response)
    }

    override fun doPost(request: Request): Response {
        return basicController.doPost(request)
    }

    private fun chunk(response: Response): Response {
        val body: ByteArray = response.body
        val out = ByteArrayOutputStream()
        try {
            var offset = 0
            while (offset < body.size) {
                val chunk: ByteArray = body.copyOfRange(offset, offset + CHUNK_SIZE)
                val lengthHex = Integer.toHexString(chunk.size)
                out.write(lengthHex.toByteArray())
                out.write(CRLF)
                out.write(chunk)
                out.write(CRLF)
                offset += CHUNK_SIZE
            }
            out.write("0".toByteArray())
            out.write(CRLF)
            out.write(CRLF)
        } catch (e: IOException) {
            return response
        }
        response.headers.remove("Content-Length")
        response.addHeaderField("Transfer-Encoding", "chunked")
        response.body = out.toByteArray()
        return response
    }

    companion object {
        const val CHUNK_SIZE = 20
        private val CRLF = byteArrayOf(0x0D, 0x0A)
    }
}

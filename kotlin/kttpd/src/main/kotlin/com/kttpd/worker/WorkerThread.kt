package com.kttpd.worker

import com.kttpd.common.Constants
import com.kttpd.common.Status
import com.kttpd.web.Router
import com.kttpd.exception.ParseException
import com.kttpd.exception.UnsupportedMethodException
import com.kttpd.message.Request
import com.kttpd.message.Response
import com.kttpd.message.parseRequest
import com.kttpd.message.serializeResponse
import com.kttpd.readErrorPage
import com.kttpd.web.controller.Controller
import java.io.IOException
import java.io.InputStream
import java.net.Socket
import java.text.SimpleDateFormat
import java.util.Date

class WorkerThread(
    private val socket: Socket,
    private val router: Router = Router.getInstance(),
): Thread() {

    companion object {
        private val dateFormat = SimpleDateFormat(Constants.DATETIME_FORMAT)
    }

    override fun run() {
        try {
            socket.getInputStream().use { `in` ->
                socket.getOutputStream().use { out ->
                    val response: Response = handleRequest(`in`)
                    val message: ByteArray = serializeResponse(response)
                    out.write(message)
                }
            }
        } catch (e: IOException) {
            errorLog("socket closed by client?")
        }
    }

    private fun handleRequest(`in`: InputStream): Response {
        var response: Response
        try {
            val request: Request = parseRequest(`in`)
            val controller: Controller = router.route(request.target)
            response = controller.handle(request)
            accessLog(request.getStartLine(), response.status.code)
        } catch (e: ParseException) {
            response = Response(version = Constants.HTTP_VERSION_1, status = Status.BAD_REQUEST)
            response.body = readErrorPage(Status.BAD_REQUEST)
            errorLog(e.message ?: Constants.EMPTY)
        } catch (e: UnsupportedMethodException) {
            response = Response(version = Constants.HTTP_VERSION_1, status = Status.BAD_REQUEST)
            response.body = readErrorPage(Status.BAD_REQUEST)
            errorLog(e.message ?: Constants.EMPTY)
        }
        return response
    }

    /**
     * アクセスログを出力します
     *
     * @param requestLine
     * @param responseCode
     */
    private fun accessLog(requestLine: String, responseCode: Int) {
        val date = Date()
        System.out.printf("[%s] \"%s\" %d%n", dateFormat.format(date), requestLine, responseCode)
    }

    /**
     * エラーログを出力します
     *
     * @param message
     */
    private fun errorLog(message: String) {
        val date = Date()
        System.out.printf("[%s] [ERROR] %s%n", dateFormat.format(date), message)
    }
}
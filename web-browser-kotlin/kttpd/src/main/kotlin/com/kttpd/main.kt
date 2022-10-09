/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package com.kttpd

import com.kttpd.common.Constants
import com.kttpd.common.Status
import com.kttpd.common.Variables
import com.kttpd.worker.WorkerThread
import java.io.IOException
import java.net.ServerSocket
import java.net.Socket
import java.nio.file.Files
import java.nio.file.Path
import java.util.concurrent.Executors

fun main() {
    val server = ServerSocket(8080)
    val executor = Executors.newCachedThreadPool()

    while (true) {
        val socket: Socket = server.accept()
        // socket オブジェクトを渡して各リクエストの処理は別スレッドで
         executor.submit(WorkerThread(socket))
    }
}

fun getDocumentRoot(): String {
    return Variables.DocumentRoot
}

/**
 * 拡張子に対応した Content-Type を返します
 *
 * @param ext 拡張子
 * @return Content-Type
 */
fun extensionToContentType(ext: String): String {
    return Variables.MimeTypes.getOrDefault(ext, "")
}

fun readErrorPage(status: Status): ByteArray {
    if (!Variables.ErrorPages.containsKey(status)) {
        return Constants.EMPTY_BYTE_ARRAY
    }
    var ret: ByteArray
    try {
        val path: Path? = Variables.ErrorPages[status]
        ret = Files.readAllBytes(path)
    } catch (e: IOException) {
        ret = Constants.EMPTY_BYTE_ARRAY
    }
    return ret
}


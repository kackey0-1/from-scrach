package com.kttpd.message

import com.kttpd.common.Constants
import com.kttpd.common.Method
import com.kttpd.common.Regexp
import com.kttpd.exception.ParseException
import java.io.BufferedReader
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.regex.Matcher
import java.util.regex.Pattern


private val requestLinePattern = Pattern.compile(Regexp.REQUEST_LINE_PATTERN)
private val headerPattern = Pattern.compile(Regexp.HANDLER_PATTERN)

/**
 * parse HTTP Request
 *
 * @param in
 * @return
 * @throws ParseException
 * @throws IOException
 */
fun parseRequest(`in`: InputStream): Request {
    val br = BufferedReader(InputStreamReader(`in`))
    val request = parseRequestLine(br)
    parseHeaderLines(br, request)
    parseBody(br, request)
    return request
}

/**
 * parse request line
 * @param br
 * @return
 * @throws IOException
 * @throws ParseException
 */
private fun parseRequestLine(br: BufferedReader): Request {
    val requestLine = br.readLine()
    val matcher: Matcher = requestLinePattern.matcher(requestLine)
    if (!matcher.matches()) {
        throw ParseException(requestLine)
    }
    val method: Method = Method.valueOf(matcher.group("method"))
    val target: String = matcher.group("target")
    val version: String = matcher.group("version")
    return Request(method = method, target = target, version = version)
}

/**
 * parse header lines.
 * @param br
 * @param request
 * @throws IOException
 * @throws ParseException
 */
private fun parseHeaderLines(br: BufferedReader, request: Request) {
    while (true) {
        val headerField = br.readLine()
        if (Constants.EMPTY == headerField.trim { it <= ' ' }) break // header と body の区切りまで読む
        val matcher: Matcher = headerPattern.matcher(headerField)
        if (matcher.matches()) {
            request.addHeaderField(matcher.group("name").lowercase(), matcher.group("value"))
        } else {
            throw ParseException(headerField)
        }
    }
}

/**
 * parse request body.
 * @param br
 * @param request
 * @throws IOException
 */
private fun parseBody(br: BufferedReader, request: Request) {
    if (request.headers.containsKey("transfer-encoding")) {
        parseChunkedBody(br, request)
    } else if (request.headers.containsKey("content-length")) {
        parseSimpleBody(br, request)
    } else {
        // nothing to read
    }
}

private fun parseChunkedBody(br: BufferedReader, request: Request) {
    val transferEncoding = request.headers["transfer-encoding"]
    if (transferEncoding == "chunked") { // only accept "chunked"
        var length = 0
        val body = ByteArrayOutputStream()
        var chunkSizeHex = br.readLine().replaceFirst(" .*$".toRegex(), "") // ignore chunk-ext
        var chunkSize = chunkSizeHex.toInt(16)
        while (chunkSize > 0) {
            val chunk = CharArray(chunkSize)
            br.read(chunk, 0, chunkSize)
            br.skip(2) // CRLF
            body.write(String(chunk).toByteArray())
            length += chunkSize
            chunkSizeHex = br.readLine().replaceFirst(" .*$".toRegex(), "")
            chunkSize = chunkSizeHex.toInt(16)
        }
        request.addHeaderField("content-length", length.toString())
        request.headers.remove("transfer-encoding")
        request.body = body.toByteArray()
    }
}

private fun parseSimpleBody(br: BufferedReader, request: Request) {
    val contentLength = Integer.valueOf(request.headers["content-length"])
    val body = CharArray(contentLength)
    br.read(body, 0, contentLength)
    request.body = String(body).toByteArray()
}

fun serializeResponse(response: Response): ByteArray {
    val message = ByteArrayOutputStream()
    val version = response.version
    val statusCode: Int = response.status.code
    val reasonPhrase: String = response.status.reasonPhrase
    message.write((version + Constants.SP + statusCode + Constants.SP + reasonPhrase + Constants.CRLF).toByteArray())
    for ((key, value) in response.headers) {
        message.write((key + ": " + value + Constants.CRLF).toByteArray())
    }
    message.write(Constants.CRLF.toByteArray())

    // ボディはファイルから読み取ったバイト列をそのまま書き込む
    message.write(response.body)
    return message.toByteArray()
}

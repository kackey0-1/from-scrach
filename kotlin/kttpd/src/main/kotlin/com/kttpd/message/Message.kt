package com.kttpd.message

import com.kttpd.common.Method
import com.kttpd.common.Status
import java.nio.charset.StandardCharsets

abstract class AbstractHttpMessage(
    var headers: MutableMap<String, String> = mutableMapOf(),
    var body: ByteArray = ByteArray(0),
) {

    fun addHeaderField(name: String, value: String) {
        headers[name] = value
    }

    abstract fun getStartLine(): String

    override fun toString(): String {
        return getStartLine() + " headers: " + headers + " body: " + String(body, StandardCharsets.UTF_8)
    }
}

class Request(
    headers: MutableMap<String, String> = mutableMapOf(),
    body: ByteArray = ByteArray(0),
    val method: Method,
    val target: String,
    val version: String,
): AbstractHttpMessage(headers, body) {

    override fun getStartLine(): String {
        return "$method $target $version"
    }
}

class Response(
    headers: MutableMap<String, String> = mutableMapOf(),
    body: ByteArray = ByteArray(0),
    val version: String,
    val status: Status,
): AbstractHttpMessage(headers, body) {

    override fun getStartLine(): String {
        return "$version ${status.code} ${status.reasonPhrase}"
    }
}

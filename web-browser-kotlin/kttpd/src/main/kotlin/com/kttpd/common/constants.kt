package com.kttpd.common


object Constants {
    const val DATETIME_FORMAT = "yyyy/MM/dd HH:mm:ss.SSS"
    const val HTTP_VERSION_1 = "HTTP/1.1"
    const val EMPTY = ""
    const val SP = " "
    const val LF = "\n"
    const val CRLF = "\r\n"
    val EMPTY_BYTE_ARRAY = byteArrayOf()
}

object Regexp {
    const val REQUEST_LINE_PATTERN = "^(?<method>\\S+) (?<target>\\S+) (?<version>\\S+)$"
    const val HANDLER_PATTERN = "^(?<name>\\S+):[ \\t]?(?<value>.+)[ \\t]?$"
}

enum class Status(val code: Int, val reasonPhrase: String) {
    OK(200, "OK"),
    NO_CONTENT(204, "No Content"),
    BAD_REQUEST(400, "Bad Request"),
    NOT_FOUND(404, "Not Found"),
    ;
}

enum class Method {
    GET,
    HEAD,
    POST,
    PUT,
    DELETE,
    CONNECT,
    OPTIONS,
    TRACE,
    PATCH,
    ;
}

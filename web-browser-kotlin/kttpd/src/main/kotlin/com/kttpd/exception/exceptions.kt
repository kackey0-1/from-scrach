package com.kttpd.exception

/**
 * HTTP メッセージの解析に失敗したことを表す例外
 */
class ParseException(startLine: String) : Exception("CANNOT PARSE REQUEST LINE: $startLine") {
    private val requestLine: String

    init {
        requestLine = startLine
    }

    /**
     * 解析できなかった request-line を返します
     */
    fun getRequestLine(): String {
        return requestLine
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}


/**
 * 未対応の HTTP メソッドを受け取った場合の例外
 */
class UnsupportedMethodException(method: String) : java.lang.Exception("unknown HTTP method: $method") {
    companion object {
        private const val serialVersionUID = 1L
    }
}

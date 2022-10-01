package com.kttpd.web

/**
 * リクエストパスと [com.khttpd.controller.Controller] を紐付けるアノテーション
 */
annotation class RequestMapping(val value: String)

package com.kttpd.common

import java.nio.file.Path
import java.nio.file.Paths

object Variables {
    val DocumentRoot: String = Paths.get(System.getProperty("user.dir"), "files", "www").toString();
    val MimeTypes: Map<String, String> = mutableMapOf(
        "html" to "text/html",
        "css" to "text/css",
        "js" to "application/js",
        "png" to "image/png",
        "txt" to "text/plain",
    )
    val ErrorPages: Map<Status, Path> = mutableMapOf(
        Status.BAD_REQUEST to Paths.get(DocumentRoot, "error/400.html"),
        Status.BAD_REQUEST to Paths.get(DocumentRoot, "error/400.html"),
    )
}
package com.kttpd.web

import java.io.IOException
import java.net.URISyntaxException
import java.net.URL
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths


/**
 * リクエストパスとコントローラの紐づけ
 */
class Router private constructor() {
    private val routingTable: Map<String, Controller>

    init {
        instance = this
        routingTable = searchRoutedHandler()
    }

    /**
     * コントローラの探索。[ksugimori.http.webapp.Router.CONTROLLER_PACKAGE_NAME]
     * で指定されたパッケージのクラスで、[ksugimori.http.webapp.RequestMapping] アノテーションが付いたクラスを探し、リクエストパスと紐付ける。
     *
     * @return
     */
    private fun searchRoutedHandler(): Map<String, Controller> {
        val table: MutableMap<String, Controller> = HashMap()
        val classLoader = Thread.currentThread().contextClassLoader!!
        val resource: URL = classLoader.getResource(CONTROLLER_PACKAGE_NAME.replace('.', '/'))
        val packageDir: Path = try {
            Paths.get(resource.toURI())
        } catch (e: URISyntaxException) {
            return emptyMap()
        }
        try {
            Files.newDirectoryStream(packageDir, "*.class").use { filePaths ->
                for (filepath in filePaths) {
                    val simpleName: String = filepath.fileName.toString().replace("\\.class$", "")
                    val className = "$CONTROLLER_PACKAGE_NAME.$simpleName"
                    try {
                        val clazz = Class.forName(className)
                        if (!clazz.isAnnotationPresent(RequestMapping::class.java)) continue
                        val path: String = clazz.getDeclaredAnnotation(RequestMapping::class.java).value
                        val controller = clazz.getConstructor().newInstance() as Controller
                        table[path] = controller
                    } catch (e: InstantiationException) {
                        // do nothing
                    } catch (e: IllegalAccessException) {
                    } catch (e: ClassNotFoundException) {
                    }
                }
            }
        } catch (e: IOException) {
            System.err.println("fail loading routed classes.")
        }
        return table
    }

    /**
     * ルーティングが設定されていればそのハンドラ、なければデフォルトのハンドラを返す
     *
     * @param path
     * @return
     */
    fun route(path: String?): Controller {
        val normalized: String = Paths.get(path).normalize().toString()
        for ((key, value) in routingTable) {
            if (normalized.startsWith(key)) {
                return value
            }
        }
        return BasicHttpController()
    }

    companion object {
        /**
         * コントローラを探索するパッケージ。サブパッケージは探索しません
         */
        const val CONTROLLER_PACKAGE_NAME = "com.kttpd.controller"
        private lateinit var instance: Router
        fun getInstance(): Router {
            return instance
        }
    }
}

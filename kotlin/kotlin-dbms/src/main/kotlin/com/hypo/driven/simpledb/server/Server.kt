package com.hypo.driven.simpledb.server

import org.apache.derby.jdbc.EmbeddedDriver
import java.sql.Connection
import java.sql.SQLException

fun main(args: Array<String>) {
    val url = "jdbc:derby:localhost/testdb;create=true"
    val driver = EmbeddedDriver()

    try {
        val conn = driver.connect(url, null)
        println("database created")
        conn.close()
    } catch (e: SQLException) {
        println("database connection failed")
        e.printStackTrace()
    }

}
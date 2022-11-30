package com.hypo.driven.simpledb.server

import org.apache.derby.jdbc.EmbeddedDriver
import java.sql.Connection
import java.sql.SQLException
import java.util.Properties

fun main(args: Array<String>) {
    val url = "jdbc:derby:localhost/testdb;create=true"
    val prop = Properties().apply {
        put("user", "einstein")
        put("password", "emc2")
    }
    val driver = EmbeddedDriver()
    try {
        driver.connect(url, null).also {conn ->
            val stmt = conn.createStatement()

            val drop = "DROP TABLE STUDENT"
            stmt.executeUpdate(drop)

            val create = "CREATE TABLE STUDENT(SId int, SName VARCHAR(10), MajorId int, GradYear int)"
            stmt.executeUpdate(create)
            println("Created table STUDENT")

            val insert = "INSERT INTO STUDENT VALUES"
            val students = arrayOf(
                "(1, 'Zhang', 10, 2004)",
                "(2, 'Wang', 20, 2005)",
                "(3, 'Li', 10, 2004)",
                "(4, 'Cheng', 20, 2005)",
                "(5, 'He', 30, 2004)",
                "(6, 'Wu', 10, 2004)",
                "(7, 'Zhao', 10, 2004)",
                "(8, 'Qian', 20, 2005)",
                "(9, 'Sun', 30, 2004)",
                "(10, 'Liu', 20, 2004)",
                "(11, 'Feng', 30, 2005)"
            )
            for (s in students)
                stmt.executeUpdate(insert + s)
            println("STUDENT records created")

            val query = "SELECT SName, MajorId, GradYear FROM STUDENT"
            val rs = stmt.executeQuery(query)
            while (rs.next()) {
                val sname = rs.getString("SName")
                val majorid = rs.getInt("MajorId")
                val gradyear = rs.getInt("GradYear")
                println("Name: $sname, MajorId: $majorid, GradYear: $gradyear")
            }

            conn.close()
        }

    } catch (e: SQLException) {
        println("database connection failed")
        e.printStackTrace()
    }

}
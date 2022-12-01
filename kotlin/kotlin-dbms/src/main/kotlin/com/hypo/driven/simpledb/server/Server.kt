package com.hypo.driven.simpledb.server

import org.apache.derby.jdbc.ClientDriver
import org.apache.derby.jdbc.ClientDriver40
import org.apache.derby.jdbc.EmbeddedDriver
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement
import java.sql.Types
import java.util.Properties
import java.util.Scanner

fun main(args: Array<String>) {
    val sc = Scanner(System.`in`)
    println("Connect> ")
    val s = sc.nextLine()
    val driver = EmbeddedDriver()
    try {
        driver.connect(s, null).also { conn ->
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

            val update = "UPDATE STUDENT SET GradYear = 2005 WHERE SName = 'He'"
            stmt.executeUpdate(update)
            println("STUDENT records updated")

            val query = "SELECT SName, MajorId, GradYear FROM STUDENT"
            val rs = stmt.executeQuery(query)
            printSchema(rs)
            while (rs.next()) {
                val sName = rs.getString("SName")
                val majorId = rs.getInt("MajorId")
                val gradYear = rs.getInt("GradYear")
                println("Name: $sName, MajorId: $majorId, GradYear: $gradYear")
            }

            conn.close()
        }

    } catch (e: SQLException) {
        println("database connection failed")
        e.printStackTrace()
    }
}

private fun printSchema(rs: ResultSet) {
    val md = rs.metaData
    for (i in 1..md.columnCount) {
        val name = md.getColumnName(i)
        val size = md.getColumnDisplaySize(i)
        val typeCode = md.getColumnType(i)
        val type = when (typeCode) {
            Types.VARCHAR -> "string"
            else -> "other"
        }
        println("$name ($type, $size)")
    }
}

private fun doQuery(stmt: Statement, cmd: String) {
    try {
        stmt.executeQuery(cmd).also { rs ->
            val md = rs.metaData
            val numCols = md.columnCount
            var totalWidth = 0
            // print header
            for (i in 1..numCols) {
                val fieldName = md.getColumnName(i)
                val width = md.getColumnDisplaySize(i)
                totalWidth += width
                val fmt = "%${width}s"
                println(String.format(fmt, fieldName))
            }
            println()
            for (i in 0..totalWidth) print("-")
            println()

            // print records
            while (rs.next()) {
                for (i in 1..numCols) {
                    val fieldName = md.getColumnName(i)
                    val fieldType = md.getColumnType(i)
                    val width = md.getColumnDisplaySize(i)
                    val fmt = "%${width}s"
                    when (fieldType) {
                        Types.INTEGER -> {
                            val ival = rs.getInt(fieldName)
                            println(String.format(fmt, ival))
                        }
                        else -> {
                            val sval = rs.getString(fieldName)
                            println(String.format(fmt, sval))
                        }
                    }
                        val value = rs.getString(i)
                    println(String.format(fmt, value))
                }
            }
        }
    } catch (e: SQLException) {
        println("SQL Exception: ${e.message}")
    }
}

private fun doUpdate(stmt: Statement, cmd: String) {
    try {
        val howMany = stmt.executeUpdate(cmd)
        println("$howMany records affected")
    } catch (e: SQLException) {
        println("SQL Exception: ${e.message}")
    }
}

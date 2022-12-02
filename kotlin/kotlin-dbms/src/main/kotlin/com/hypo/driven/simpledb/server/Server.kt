package com.hypo.driven.simpledb.server

import org.apache.derby.jdbc.ClientDataSource
import org.apache.derby.jdbc.EmbeddedDriver
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement
import java.sql.Types
import java.util.Scanner

fun main(args: Array<String>) {
    val sc = Scanner(System.`in`)
    println()
    print("Connect> ")
    val s = sc.nextLine()
    val driver = EmbeddedDriver()
    try {
        driver.connect(s, null).also { conn ->
            val stmt = conn.createStatement()
            init(stmt)
            println()
            print("SQL> ")
            while (sc.hasNextLine()) {
                // process one line of input
                val cmd = sc.nextLine().trim().lowercase()
                if (cmd.startsWith("exit"))
                    break
                else if (cmd.startsWith("select"))
                    doQuery(stmt, cmd)
                else if (cmd.startsWith("insert"))
                    doUpdate(stmt, cmd)
                else if (cmd.startsWith("update"))
                    doUpdate(stmt, cmd)
                else
                    println("invalid command")
                println()
                print("SQL> ")
            }

            conn.close()
        }

    } catch (e: SQLException) {
        println("database connection failed")
        e.printStackTrace()
    }
}

private fun init(stmt: Statement) {
    try {
        val initTables: List<Pair<String, Array<String>>> = listOf(
            Pair(
                "CREATE TABLE STUDENT (SId int, SName VARCHAR(10), MajorId int, GradYear int)",
                arrayOf(
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
                    "(11, 'Feng', 30, 2005)",
                )
            ),
            Pair(
                "CREATE TABLE DEPT (DId int, DName VARCHAR(8))",
                arrayOf(
                    "(10, 'CompSc')",
                    "(20, 'Math')",
                    "(30, 'Eng')",
                    "(40, 'Physics')",
                )
            ),
            Pair(
                "CREATE TABLE COURSE (CId int, Title VARCHAR(20), DeptId int)",
                arrayOf(
                    "(12, 'Database Systems', 10)",
                    "(22, 'Formal Languages', 10)",
                    "(32, 'Advanced Algorithms', 10)",
                    "(42, 'Scientific Computing', 40)",
                    "(52, 'Numerical Analysis', 40)",
                    "(62, 'Biology', 40)",
                )
            ),
            Pair(
                "CREATE TABLE SECTION (SectId int, CourseId int, Prof varchar(8), YearOffered int)",
                arrayOf(
                    "(13, 12, 'turing', 2018)",
                    "(23, 12, 'turing', 2019)",
                    "(33, 32, 'newton', 2019)",
                    "(43, 32, 'einstein', 2017)",
                    "(53, 62, 'brando', 2018)",
                )
            ),
        )
        for (table in initTables) {
            val tableName = table.first.split(" ")[2]
            stmt.executeUpdate("DROP TABLE $tableName")
            stmt.executeUpdate(table.first)
            println("Created table $tableName")
            for (record in table.second)
                stmt.executeUpdate("INSERT INTO $tableName VALUES $record")
            println("Inserted " + table.second.size + " rows into $tableName")
        }

    } catch (e: SQLException) {
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
                print(String.format(fmt, fieldName))
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
                            print(String.format(fmt, ival))
                        }

                        else -> {
                            val sval = rs.getString(fieldName)
                            print(String.format(fmt, sval))
                        }
                    }
                }
                println()
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

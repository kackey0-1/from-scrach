package com.hypo.driven.simpledb.log

import com.hypo.driven.simpledb.file.FileManager
import com.hypo.driven.simpledb.file.Page
import kotlin.test.Test
import java.io.File

class LogTest {

    @Test
    fun `test_log_manager`() {
        val fm = FileManager(File("filetest"), 400)
        val lm = LogManager(fm, "logtest")
        printLogRecords(lm, "The initial empty log file:") // print an empty log file
        println("done")
        createRecords(lm, 1, 35)
        printLogRecords(lm, "The log file now has these records:")
        createRecords(lm, 36, 70)
        lm.flush(65)
        printLogRecords(lm, "The log file now has these records:")
    }

    private fun printLogRecords(lm: LogManager, msg: String) {
        println(msg)
        val iter: Iterator<ByteArray> = lm.iterator()
        while (iter.hasNext()) {
            val rec = iter.next()
            val p = Page(rec)
            val s: String = p.getString(0)
            val npos: Int = Page.maxLength(s.length)
            val int: Int = p.getInt(npos)
            println("[$s, $int]")
        }
        println()
    }

    private fun createRecords(lm: LogManager, start: Int, end: Int) {
        print("Creating records: ")
        for (i in start..end) {
            val rec = createLogRecord("record$i", i + 100)
            val lsn: Int = lm.append(rec)
            print("$lsn ")
        }
        println()
    }

    // Create a log record having two values: a string and an integer.
    private fun createLogRecord(s: String, n: Int): ByteArray {
        val spos = 0
        val npos: Int = spos + Page.maxLength(s.length)
        val b = ByteArray(npos + Integer.BYTES)
        val p = Page(b)
        p.setString(spos, s)
        p.setInt(npos, n)
        return b
    }
}

package com.hypo.driven.simpledb.file

import java.io.File
import java.io.IOException
import java.io.RandomAccessFile
import kotlin.test.Test

class FileTest {

    @Test
    fun `test_file_manager`() {
        val fileManager = FileManager(File("filetest"), 400)
        val block = BlockId("testfile", 2)
        val page1 = Page(fileManager.blockSize)
        val position1 = 88
        page1.setString(position1, "abcdefghijklm")
        val size = Page.maxLength("abcdefghijklm".length)
        val position2 = position1 + size
        page1.setInt(position2, 345)
        fileManager.write(block, page1)
        val p2 = Page(fileManager.blockSize)
        fileManager.read(block, p2);
        println("offset " + position2 +  " contains " + p2.getInt(position2))
        println("offset " + position1 +  " contains " + p2.getInt(position1))
    }

    @Test
    fun testRandomAccessFile() {
        val file = File("testfile")
        try {
            // initialize the file
            val f1 = RandomAccessFile(file, "rws")
            f1.seek(123)
            f1.writeInt(999)
            f1.close()

            // increment the file
            val f2 = RandomAccessFile(file, "rws")
            f2.seek(123)
            val n = f2.readInt()
            f2.seek(123)
            f2.writeInt(n + 1)
            f2.close()

            // re-read the file
            val f3 = RandomAccessFile(file, "rws")
            f3.seek(123)
            println("The new value is " + f3.readInt())
            f3.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
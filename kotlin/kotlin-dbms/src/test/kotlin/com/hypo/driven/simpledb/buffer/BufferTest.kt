package com.hypo.driven.simpledb.buffer

import com.hypo.driven.simpledb.file.BlockId
import com.hypo.driven.simpledb.file.FileManager
import com.hypo.driven.simpledb.log.LogManager
import java.io.File
import kotlin.test.Test
class BufferTest {
    @Test
    fun testBuffer() {
        val fm = FileManager(File("filetest"), 400)
        val lm = LogManager(fm, "logtest")
        val bufferManager = BufferManager(fm, lm, 8)

        val buffer1 = bufferManager.pin(BlockId("testfile", 1))
        val page = buffer1.contents()
        val n = page.getInt(80)
        page.setInt(80, n+1)
        buffer1.setModified(1, 0) // place holder values
        println("The new value is ${n+1}")
        bufferManager.unpin(buffer1)

        // One of these pins will flush buffer1 to disk
        var buffer2 = bufferManager.pin(BlockId("testfile", 2))
        val buffer3 = bufferManager.pin(BlockId("testfile", 3))
        val buffer4 = bufferManager.pin(BlockId("testfile", 4))

        bufferManager.unpin(buffer2)
        buffer2 = bufferManager.pin(BlockId("testfile", 1))
        val page2 = buffer2.contents()
        page2.setInt(80, 9999) // This modification won't get written to disk
        buffer2.setModified(1, 0)
    }
}
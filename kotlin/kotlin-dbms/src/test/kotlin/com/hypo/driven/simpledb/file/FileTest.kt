package com.hypo.driven.simpledb.file

import java.io.File
import kotlin.test.Test

class FileTest {

    @Test
    fun test() {
        val fileManager = FileManager(File("filetest"), 400)
        val blk: BlockId  = BlockId("testfile", 2)
        val p1: Page = Page(fileManager.blockSize)
        val pos1 = 88
        p1.setString(pos1, "abcdefghijklm")
        val size = Page.maxLength("abcdefghijklm".length)
        val pos2 = pos1 + size
        p1.setInt(pos2, 345)
        fileManager.write(blk, p1);
        val p2 = Page(fileManager.blockSize)
        fileManager.read(blk, p2);
        println("offset " + pos2 +  " contains " + p2.getInt(pos2))
        println("offset " + pos1 +  " contains " + p2.getInt(pos1))
    }

}
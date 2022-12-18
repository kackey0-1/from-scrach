package com.hypo.driven.simpledb.tx

import com.hypo.driven.simpledb.buffer.BufferManager
import com.hypo.driven.simpledb.file.FileManager
import com.hypo.driven.simpledb.log.LogManager
import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

class TransactionTest {

//    @Test
//    fun testTransaction() {
//        val fileManager: FileManager = db.fileMgr()
//        val lm: LogManager = db.logMgr()
//        val bm: BufferManager = db.bufferMgr()
//        val tx1 = Transaction(fm, lm, bm)
//        val blk = BlockId("testfile", 1)
//        tx1.pin(blk)
//        // The block initially contains unknown bytes,
//        // so don't log those values here.
//        tx1.setInt(blk, 80, 1, false)
//        tx1.setString(blk, 40, "one", false)
//        tx1.commit()
//        val tx2 = Transaction(fm, lm, bm)
//        tx2.pin(blk)
//        val ival = tx2.getInt(blk, 80)!!
//        val sval = tx2.getString(blk, 40)
//        println("initial value at location 80 = $ival")
//        println("initial value at location 40 = $sval")
//        val newival = ival + 1
//        val newsval = "$sval!"
//        tx2.setInt(blk, 80, newival, true)
//        tx2.setString(blk, 40, newsval, true)
//        tx2.commit()
//        val tx3 = Transaction(fm, lm, bm)
//        tx3.pin(blk)
//        System.out.println("new value at location 80 = " + tx3.getInt(blk, 80))
//        System.out.println("new value at location 40 = " + tx3.getString(blk, 40))
//        tx3.setInt(blk, 80, 9999, true)
//        System.out.println("pre-rollback value at location 80 = " + tx3.getInt(blk, 80))
//        tx3.rollback()
//        val tx4 = Transaction(fm, lm, bm)
//        tx4.pin(blk)
//        System.out.println("post-rollback at location 80 = " + tx4.getInt(blk, 80))
//        tx4.commit()
//    }

}
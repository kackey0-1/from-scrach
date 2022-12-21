package com.hypo.driven.simpledb.tx.recovery

import com.hypo.driven.simpledb.file.Page
import com.hypo.driven.simpledb.log.LogManager
import com.hypo.driven.simpledb.tx.Transaction


/**
 * Check Point Log Record
 */
class CheckpointRecord: LogRecord {
    override fun op(): Int {
        return Operator.CHECKPOINT.id
    }

    /**
     * Checkpoint recordsは関連付けられたトランザクションは持たないためダミーの値として-1を返す
     */
    override fun txNumber(): Int {
        return -1 // dummy value
    }

    /**
     * Checkpoint recordsはやり直しを行う情報は持っていない
     */
    override fun undo(transaction: Transaction) {}

    override fun toString(): String {
        return "<CHECKPOINT>"
    }

    /**
     * ログにCheckpoint(チェックポイント)の行を書くメソッド
     * このログレコードはCHECKPOINT Operatorだけを含む
     * @return 末尾のログの識別子
     */
    companion object {
        fun writeToLog(logManager: LogManager): Int {
            val record = ByteArray(Integer.BYTES)
            val page = Page(record)
            page.setInt(0, Operator.CHECKPOINT.id)
            return logManager.append(record)
        }
    }
}
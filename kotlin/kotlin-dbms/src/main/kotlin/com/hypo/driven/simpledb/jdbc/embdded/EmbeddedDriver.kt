package com.hypo.driven.simpledb.jdbc.embdded

import com.hypo.driven.simpledb.jdbc.DriverAdapter
//import com.hypo.driven.simpledb.server.SimpleDB
import java.sql.Connection
import java.sql.SQLException
import java.util.Properties

class EmbeddedDriver: DriverAdapter() {
//    override fun connect(url: String, info: Properties): Connection {
//        val db = SimpleDB(url)
//        return EmbeddedConnection(db)
//    }
}

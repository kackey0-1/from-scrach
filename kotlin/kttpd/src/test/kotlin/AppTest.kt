/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */

import java.io.File
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable
import java.time.Instant
import kotlin.test.assertEquals


data class Person(
    val name: String = "John Doe",
    val age: Int = 42,
) : Serializable

fun main() {
    val person = Person()
    val start = Instant.now()
    val end = Instant.now()
    val range: ClosedRange<Instant> = start..end

    val fileName = "sample.txt"
    val file = File(fileName)
    ObjectOutputStream(file.outputStream()).use { out ->
        // out.writeObject(person)
        out.writeObject(range)
    }

    ObjectInputStream(file.inputStream()).use { input ->
//        val p = input.readObject() as Person
//        println("p = $p")
//        assertEquals(p.age, person.age)
//        assertEquals(p.name, person.name)
//        val range = input.readObject() as
    }
}


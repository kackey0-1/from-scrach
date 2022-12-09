package com.hypo.driven.simpledb

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.runBlocking
import java.util.concurrent.atomic.AtomicInteger

/**
 * https://proandroiddev.com/synchronization-and-thread-safety-techniques-in-java-and-kotlin-f63506370e6d
 * - Synchronized
 * - Atomic Primitive
 * - Lock
 * - Semaphore
 * - Volatile
 */


/**
 * synchronization violate example method
 */
fun synchronizationViolateMethod() = runBlocking {
    var sharedCounter = 0
    // We want our code to run on 4 threads
    val scope = CoroutineScope(newFixedThreadPoolContext(4, "synchronizationPool"))
    scope.launch {
        //create 1000 coroutines (light-weight threads).
        val coroutines = 1.rangeTo(1000).map {
            launch {
                // and in each of them, increment the sharedCounter 1000 times.
                for (i in 1..1000) {
                    sharedCounter++
                }
            }
        }
        coroutines.forEach {
            // wait for all coroutines to finish their jobs.
                corotuine ->
            corotuine.join()
        }
    }.join()
    println("The number of shared counter should be 1000000, but actually is $sharedCounter")
}

var sharedCounter = 0

@Synchronized
fun updateCounter() {
    sharedCounter++
}

/**
 * synchronization annotation example method
 */
fun synchronizationAnnotationMethod() = runBlocking {
    // We want our code to run on 4 threads
    val scope = CoroutineScope(newFixedThreadPoolContext(4, "synchronizationPool"))
    scope.launch {
        //create 1000 coroutines (light-weight threads).
        val coroutines = 1.rangeTo(1000).map {
            launch {
                // and in each of them, increment the sharedCounter 1000 times.
                for (i in 1..1000) {
                    // call the newly created function that is now synchronized
                    updateCounter()
                }
            }
        }
        coroutines.forEach { corotuine ->
            corotuine.join() // wait for all coroutines to finish their jobs.
        }
    }.join()
    println("The number of shared counter is $sharedCounter")
}

/**
 * To use synchronization where it is needed, synchronized statements can be used.
 */
class Incrementor() {
    var sharedCounter: Int = 0
        private set

    fun updateCounterIfNecessary(shouldIActuallyIncrement: Boolean) {
        if (shouldIActuallyIncrement) {
            synchronized(this) {
                //only locks when needed, using the Incrementor`s instance as the lock.
                sharedCounter++
            }
        }
    }
}
fun synchronizedBlock() = runBlocking {
    val incrementor = Incrementor()
    // We want our code to run on 4 threads
    val scope = CoroutineScope(newFixedThreadPoolContext(4, "synchronizationPool"))
    scope.launch {
        val coroutines = 1.rangeTo(1000).map {
            //create 1000 coroutines (light-weight threads).
            launch {
                for (i in 1..1000) {
                    // and in each of them, increment the sharedCounter 1000 times.
                    incrementor.updateCounterIfNecessary(it % 2 == 0)
                }
            }
        }
        coroutines.forEach {
            // wait for all coroutines to finish their jobs.
            corotuine -> corotuine.join()
        }
    }.join()
    println("The number of shared counter is ${incrementor.sharedCounter}")
}

/**
 * Atomic primitives provide mutating functions for their underlying primitive value which are all atomic thread-safe
 */
class IncrementorV2() {
    val sharedCounter: AtomicInteger = AtomicInteger(0)

    fun updateCounterIfNecessary(shouldIActuallyIncrement: Boolean) {
        if (shouldIActuallyIncrement) {
            sharedCounter.incrementAndGet() // the increment (++) operation is done atomically, so all threads wait for its completion
        }
    }

    fun getSharedCounter():Int {
        return sharedCounter.get()
    }
}

fun atomicPrimitive() = runBlocking {
    val incrementor = IncrementorV2()
    val scope = CoroutineScope(newFixedThreadPoolContext(4, "synchronizationPool")) // We want our code to run on 4 threads
    scope.launch {
        val coroutines = 1.rangeTo(1000).map {
            //create 1000 coroutines (light-weight threads).
            launch {
                for (i in 1..1000) { // and in each of them, increment the sharedCounter 1000 times.
                    incrementor.updateCounterIfNecessary(it % 2 == 0)
                }
            }
        }

        coroutines.forEach { corotuine ->
            corotuine.join() // wait for all coroutines to finish their jobs.
        }
    }.join()

    println("The number of shared counter is ${incrementor.getSharedCounter()}")
}

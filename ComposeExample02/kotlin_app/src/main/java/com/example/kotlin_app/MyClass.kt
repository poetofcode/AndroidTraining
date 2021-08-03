package com.example.kotlin_app

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.collect

@DelicateCoroutinesApi
@InternalCoroutinesApi
fun main(args: Array<String>) {
    runBlocking {
        testFlow()
    }
}

@InternalCoroutinesApi
suspend fun testFlow() {
    val input = 1..10
    input.asFlow()
        .groupToList { it % 2 }
        .collect { println(it) }
}

@InternalCoroutinesApi
fun <T, K> Flow<T>.groupToList(getKey: (T) -> K): Flow<Pair<K, List<T>>> = flow {
    val storage = mutableMapOf<K, MutableList<T>>()
    collect { t ->
        storage.getOrPut(getKey(t)) { mutableListOf() } += t
    }
    storage.forEach { (k, ts) -> emit(k to ts) }
}
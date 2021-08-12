package com.example.kotlin_app

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

@DelicateCoroutinesApi
@InternalCoroutinesApi
fun main(args: Array<String>) {
    runBlocking {
        testFlow()
    }
}

@InternalCoroutinesApi
suspend fun testFlow() {
    val pagingArrOfProductItems = listOf(
        PagingData(ProductItem("Product 1")),
        PagingData(ProductItem("Product 2")),
        PagingData(ProductItem("Product 3")),
        PagingData(ProductItem("Product 4")),
        PagingData(ProductItem("Product 5"))

    )
    pagingArrOfProductItems.asFlow()
        .groupByPair()
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


@InternalCoroutinesApi
fun Flow<PagingData<ProductItem>>.groupByPair(): Flow<PagingData<ProductRow>> = flow {
    val storage = mutableListOf<PagingData<ProductRow>>()
    val tempPair = mutableListOf<ProductItem>()
    val addToStorage: (MutableList<ProductItem>) -> Unit = {
        storage += PagingData(ProductRow(it.toList()))
        it.clear()
    }
    collect { item ->
        tempPair += item.value
        if (tempPair.size < 2) return@collect
        addToStorage(tempPair)
    }
    if (tempPair.isNotEmpty()) addToStorage(tempPair)
    storage.forEach { emit(it) }
}


//
// Data classes
//

data class PagingData<T>(val value: T)

data class ProductItem(val productName: String)

data class ProductRow(val items: List<ProductItem>)
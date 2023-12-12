package com.example.selfupdatingstateapp

interface SelfReducer<T> {

    fun reduceSelf(cb: T.() -> T)

}
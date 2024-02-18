package com.poetofcode.site2api_sample.data.service

import java.security.MessageDigest

fun String.toSha1(): String {
    return MessageDigest
        .getInstance("SHA-1")
        .digest(this.toByteArray())
        .joinToString(separator = "", transform = { "%02x".format(it) })
}
package com.poetofcode.site2api_sample.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

fun Context.openLinkInBrowser(url: String) {
    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    startActivity(browserIntent)
}

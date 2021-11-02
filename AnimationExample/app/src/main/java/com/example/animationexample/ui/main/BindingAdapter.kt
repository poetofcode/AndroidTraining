package com.example.animationexample.ui.main

import android.view.View
import android.view.animation.Animation
import androidx.databinding.BindingAdapter

@BindingAdapter("app:anim")
fun applyAnimation(view: View, anim: Animation) {
    // val anim: Animation = AnimationUtils.loadAnimation(App.instance, animRes)
    view.startAnimation(anim)
}
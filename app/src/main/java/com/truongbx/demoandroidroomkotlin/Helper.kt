package com.truongbx.demoandroidroomkotlin

import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("loadImage")
fun bindingLoadImage(imageView: ImageView, imageUri: Int) {
    Glide.with(imageView.context).load(imageUri).into(imageView)
}

fun Context.toast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
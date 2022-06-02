package com.backbase.assignment.common

import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.backbase.assignment.R
import com.bumptech.glide.Glide

@BindingAdapter("ratingDrawable")
fun TextView.setRatingDrawable(rating: Int) {

    val resId = if (rating >= 50) R.drawable.bg_high_rating else R.drawable.bg_low_rating

    val drawable = ContextCompat.getDrawable(this.context, resId)

    setIntrinsicBounds(drawable)

    val drawables = compoundDrawables

    setCompoundDrawables(drawable, drawables[1], drawables[2], drawables[3])
}

fun setIntrinsicBounds(drawable: Drawable?) {
    drawable?.setBounds(
        0,
        0,
        drawable.intrinsicWidth,
        drawable.intrinsicHeight
    )
}

@BindingAdapter("setMovieImage")
fun ImageView.setMovieImage(url: String?) {
    if (url == null) {
        return
    }

   Glide.with(this.context)
       .load(url)
       .into(this)
}

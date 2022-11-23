package edu.temple.bookrecommendationsystem

import androidx.annotation.Nullable

data class Book(val title: String, val author: String, val coverURL: Int, @Nullable val userRating: Int)

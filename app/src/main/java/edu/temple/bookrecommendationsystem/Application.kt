package edu.temple.bookrecommendationsystem

import android.app.Application

/*
    class to store global variables to use throughout the application

    access pattern:
    Application().[wantToRead/previouslyRead/dummyBooks]
 */
class Application: Application() {
    var wantToRead = ArrayList<Book>()
    var previouslyRead = ArrayList<Book>()
    val dummyBooks = arrayOf(
        Book("Red Book", "Red Author", R.color.red),
        Book("Blue Book", "Blue Author", R.color.blue),
        Book("Green Book", "Green Author", R.color.green),
        Book("Yellow Book", "Yellow Author", R.color.yellow),
        Book("Cyan Book", "Cyan Author", R.color.cyan),
        Book("Magenta Book", "Magenta Author", R.color.magenta)
    )
}
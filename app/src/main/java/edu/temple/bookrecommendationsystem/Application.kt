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
        Book("Red Book", "Red Author", R.color.red, 0),
        Book("Blue Book", "Blue Author", R.color.blue, 0),
        Book("Green Book", "Green Author", R.color.green, 0),
        Book("Yellow Book", "Yellow Author", R.color.yellow, 0),
        Book("Cyan Book", "Cyan Author", R.color.cyan, 0),
        Book("Magenta Book", "Magenta Author", R.color.magenta, 0)
    )
}
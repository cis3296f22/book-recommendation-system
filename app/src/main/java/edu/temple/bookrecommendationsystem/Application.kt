package edu.temple.bookrecommendationsystem

import android.util.Log

/*
    class to store global variables to use throughout the application

    access pattern:
    Application.Singleton.[wantToRead/previouslyRead/dummyBooks]
 */
class Application {
    object Singleton {
        var wantToRead = ArrayList<Book>()
        var previouslyRead = ArrayList<Book>()
        var searchResults = ArrayList<Book>()
        var recommendations = ArrayList<Book>()
        var recIndex = 0
        val dummyBooks = arrayListOf(
            Book("Red Book", R.color.red),
            Book("Blue Book", R.color.blue),
            Book("Green Book", R.color.green),
            Book("Yellow Book", R.color.yellow),
            Book("Cyan Book", R.color.cyan),
            Book("Magenta Book", R.color.magenta)
        )

        val testCSV = "title1,1\ntitle2,2\ntitle3,3"
        fun csvToBookArray(csv: String) : ArrayList<Book> {
            val books = ArrayList<Book>()
            val elements = csv.split(",","\n")
            for(i in elements.indices step 2) {
                books.add(Book(elements[i], elements[i+1].toInt()))
            }
            return books
        }
    }
}
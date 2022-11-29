package edu.temple.bookrecommendationsystem

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
    }
}
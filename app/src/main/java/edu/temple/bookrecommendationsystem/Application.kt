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
            Book("Red Book", "Red Author", R.color.red),
            Book("Blue Book", "Blue Author", R.color.blue),
            Book("Green Book", "Green Author", R.color.green),
            Book("Yellow Book", "Yellow Author", R.color.yellow),
            Book("Cyan Book", "Cyan Author", R.color.cyan),
            Book("Magenta Book", "Magenta Author", R.color.magenta)
        )
    }
}
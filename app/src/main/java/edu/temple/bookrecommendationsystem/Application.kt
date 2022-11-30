package edu.temple.bookrecommendationsystem

import android.widget.ImageView
import com.squareup.picasso.Picasso

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

        fun csvToBookArray(csv: String) : ArrayList<Book> {
            val books = ArrayList<Book>()
            val elements = csv.replace("[0-9]+,".toRegex(),"").split(",","\n")
            for(i in 3 until elements.size step 2) {
                books.add(Book(elements[i], elements[i+1]))
            }
            return books
        }

        fun loadImage(url: String, imageView: ImageView) {
            Picasso.get().load(url).into(imageView)
        }
    }
}
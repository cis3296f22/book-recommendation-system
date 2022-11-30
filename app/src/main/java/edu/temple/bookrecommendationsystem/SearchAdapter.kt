package edu.temple.bookrecommendationsystem

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/*
Adapter class that maps a list of search results to a RecyclerView to display to user.
 */

class SearchAdapter(_results : ArrayList<Book>, _onClickFunc : (Book) -> Unit) :
    RecyclerView.Adapter<SearchAdapter.ImageViewHolder>() {

    private val results = _results
    private val onClickFunction = _onClickFunc

    inner class ImageViewHolder(_view : View) : RecyclerView.ViewHolder(_view){
        val bookCover = _view.findViewById<ImageView>(R.id.search_result_book_cover)!!
        val bookTitle = _view.findViewById<TextView>(R.id.search_result_title)!!
        lateinit var book: Book
        init {
            _view.setOnClickListener{onClickFunction(book)}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_result_book, parent, false)
        return ImageViewHolder(layout)
    }

    override fun onBindViewHolder(holder: SearchAdapter.ImageViewHolder, position: Int) {
        Application.Singleton.loadImage(results[position].coverURL, holder.bookCover)
        holder.bookTitle.text = results[position].title
        holder.book = results[position]
    }

    override fun getItemCount(): Int {
        return results.size
    }

}
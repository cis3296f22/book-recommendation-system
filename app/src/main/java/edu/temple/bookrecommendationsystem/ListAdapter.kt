package edu.temple.bookrecommendationsystem

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

/*
Adapter class that maps a list of books to a horizontal RecyclerView to display to user.
 */

class ListAdapter(_results: ArrayList<Book>, _onClickFunc: (Book) -> Unit) :
    RecyclerView.Adapter<ListAdapter.ImageViewHolder>() {

    private val results = _results
    private val onClickFunction = _onClickFunc

    inner class ImageViewHolder(_view : View) : RecyclerView.ViewHolder(_view){
        val bookCover = _view.findViewById<ImageView>(R.id.list_fragment_book_cover)!!
        lateinit var book: Book
        init {
            _view.setOnClickListener{onClickFunction(book)}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_fragment_book, parent, false)
        return ImageViewHolder(layout)
    }

    override fun onBindViewHolder(holder: ListAdapter.ImageViewHolder, position: Int) {
        Application.Singleton.loadImage(results[position].coverURL, holder.bookCover)
        holder.book = results[position]
    }

    override fun getItemCount(): Int {
        return results.size
    }

}
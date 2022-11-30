package edu.temple.bookrecommendationsystem

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


/**
 * Search adapter
 * Adapter class that maps a list of search results to a RecyclerView to display to user.
 *
 * @constructor
 *
 * @param _results
 * @param _onClickFunc
 */
class SearchAdapter(_results : ArrayList<Book>, _onClickFunc : (Book) -> Unit) :
    RecyclerView.Adapter<SearchAdapter.ImageViewHolder>() {

    private val results = _results
    private val onClickFunction = _onClickFunc

    /**
     * Image view holder
     *
     * @param _view
     */
    inner class ImageViewHolder(_view : View) : RecyclerView.ViewHolder(_view){
        val bookCover = _view.findViewById<ImageView>(R.id.search_result_book_cover)!!
        val bookTitle = _view.findViewById<TextView>(R.id.search_result_title)!!
        lateinit var book: Book
        init {
            _view.setOnClickListener{onClickFunction(book)}
        }
    }

    /**
     * On create view holder
     *
     * @param parent
     * @param viewType
     * @return
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_result_book, parent, false)
        return ImageViewHolder(layout)
    }

    /**
     * On bind view holder
     *
     * @param holder
     * @param position
     */
    override fun onBindViewHolder(holder: SearchAdapter.ImageViewHolder, position: Int) {
        Application.Singleton.loadImage(results[position].coverURL, holder.bookCover)
        holder.bookTitle.text = results[position].title
        holder.book = results[position]
    }

    /**
     * Get item count
     *
     * @return the size of the private val results: ArrayList
     */
    override fun getItemCount(): Int {
        return results.size
    }

}
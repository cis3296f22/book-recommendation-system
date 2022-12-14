package edu.temple.bookrecommendationsystem

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView


/**
 * List adapter
 * Adapter class that maps a list of books to a horizontal RecyclerView to display to user.
 *
 * @param _results - an ArrayList of Book objects
 * @param _onClickFunc - Book object
 */
class ListAdapter(_results: ArrayList<Book>, _onClickFunc: (Book) -> Unit) :
    RecyclerView.Adapter<ListAdapter.ImageViewHolder>() {

    private val results = _results
    private val onClickFunction = _onClickFunc

    /**
     * Image view holder
     *
     * @param _view
     */
    inner class ImageViewHolder(_view : View) : RecyclerView.ViewHolder(_view){
        val bookCover = _view.findViewById<ImageView>(R.id.list_fragment_book_cover)!!
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
     * @return an ImageViewHolder containing the layout
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_fragment_book, parent, false)
        return ImageViewHolder(layout)
    }

    /**
     * On bind view holder
     *
     * @param holder
     * @param position
     */
    override fun onBindViewHolder(holder: ListAdapter.ImageViewHolder, position: Int) {
        Application.Singleton.loadImage(results[position].coverURL, holder.bookCover)
        holder.book = results[position]
    }

    /**
     * Get item count
     *
     * @return the size of the private ArrayList results
     */
    override fun getItemCount(): Int {
        return results.size
    }

}
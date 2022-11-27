package edu.temple.bookrecommendationsystem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

/*
A fragment class to display a book cover, title, author, and user rating (if given).
Instance will be created when user clicks on a book from one of their lists or search results.
 */

class BookDetailsFragment : Fragment() {

    lateinit var title: String
    lateinit var author: String
    var cover: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = arguments?.get("title") as String
        author = arguments?.get("author") as String
        cover = arguments?.get("cover") as Int
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_book_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<ImageView>(R.id.details_cover).setImageResource(cover)
        view.findViewById<TextView>(R.id.details_title).text = title
        view.findViewById<TextView>(R.id.details_author).text = author
        view.findViewById<Button>(R.id.details_close_button).setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

}
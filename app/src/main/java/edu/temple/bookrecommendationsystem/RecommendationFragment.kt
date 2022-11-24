package edu.temple.bookrecommendationsystem

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

/*
A fragment class to display recommended books to the user that they can "swipe" left or right on.
 */

class RecommendationFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recommendation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var count = 0
        val cover = requireView().findViewById<ImageView>(R.id.recommendationCover)
        val title = requireView().findViewById<TextView>(R.id.recommendationTitle)
        val author = requireView().findViewById<TextView>(R.id.recommendationAuthor)
        val dummyBooks = Application().dummyBooks
        cover.setImageResource(dummyBooks[count].coverURL)
        title.text = dummyBooks[count].title
        author.text = dummyBooks[count].author

        requireView().findViewById<Button>(R.id.dislike_button).setOnClickListener {
            count++
            val index = count % dummyBooks.size
            cover.setImageResource(dummyBooks[index].coverURL)
            title.text = dummyBooks[index].title
            author.text = dummyBooks[index].author
        }

        requireView().findViewById<Button>(R.id.like_button).setOnClickListener {
            count++
            val index = count % dummyBooks.size
            cover.setImageResource(dummyBooks[index].coverURL)
            title.text = dummyBooks[index].title
            author.text = dummyBooks[index].author
        }

    }
}
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recommendation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var count = 0
        var index = 0
        val cover = requireView().findViewById<ImageView>(R.id.recommendation_cover)
        val title = requireView().findViewById<TextView>(R.id.recommendation_title)
        val app = Application.Singleton
        val dummyBooks = app.dummyBooks
        val want = app.wantToRead
        val prev = app.previouslyRead
        cover.setImageResource(dummyBooks[count].coverURL)
        title.text = dummyBooks[count].title

        requireView().findViewById<Button>(R.id.dislike_button).setOnClickListener {
            count++
            index = count % dummyBooks.size
            cover.setImageResource(dummyBooks[index].coverURL)
            title.text = dummyBooks[index].title
        }

        requireView().findViewById<Button>(R.id.like_button).setOnClickListener {
            want.add(dummyBooks[index])
            count++
            index = count % dummyBooks.size
            cover.setImageResource(dummyBooks[index].coverURL)
            title.text = dummyBooks[index].title
        }

        requireView().findViewById<Button>(R.id.prev_read_button).setOnClickListener {
            prev.add(dummyBooks[index])
            count++
            index = count % dummyBooks.size
            cover.setImageResource(dummyBooks[index].coverURL)
            title.text = dummyBooks[index].title
        }

    }
}
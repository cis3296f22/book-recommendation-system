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
        val cover = requireView().findViewById<ImageView>(R.id.recommendation_cover)
        val title = requireView().findViewById<TextView>(R.id.recommendation_title)
        val author = requireView().findViewById<TextView>(R.id.recommendation_author)
        val dummyBooks = Application().dummyBooks
        cover.setImageResource(dummyBooks[count].coverURL)
        title.text = dummyBooks[count].title
        author.text = dummyBooks[count].author

        requireView().findViewById<Button>(R.id.dislike_button).setOnClickListener {
            count++
            var index = count % dummyBooks.size
            cover.setImageResource(dummyBooks[index].coverURL)
            title.text = dummyBooks[index].title
            author.text = dummyBooks[index].author
        }

        requireView().findViewById<Button>(R.id.like_button).setOnClickListener {
            count++
            var index = count % dummyBooks.size
            cover.setImageResource(dummyBooks[index].coverURL)
            title.text = dummyBooks[index].title
            author.text = dummyBooks[index].author
            Application().wantToRead.add(dummyBooks[index])
        }

        requireView().findViewById<ImageView>(R.id.recommendation_cover).setOnClickListener {
            val fragment = BookDetailsFragment()
            val bundle = Bundle()
            bundle.putString("title", dummyBooks[count].title)
            bundle.putString("author", dummyBooks[count].author)
            bundle.putInt("cover", dummyBooks[count].coverURL)
            fragment.arguments = bundle
            parentFragmentManager.beginTransaction()
                .replace(R.id.container1, fragment)
                .addToBackStack(null)
                .commit()
        }

    }
}
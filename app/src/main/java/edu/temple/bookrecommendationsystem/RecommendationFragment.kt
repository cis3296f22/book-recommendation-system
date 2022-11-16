package edu.temple.bookrecommendationsystem

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import org.w3c.dom.Text

/**
 * A simple [Fragment] subclass.
 * Use the [RecommendationFragment.newInstance] factory method to
 * create an instance of this fragment.
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
        val dummyBooks = arrayOf(Book("Red Book", "Red Author", R.color.red),
            Book("Blue Book", "Blue Author", R.color.blue),
            Book("Green Book", "Green Author", R.color.green),
            Book("Yellow Book", "Yellow Author", R.color.yellow),
            Book("Cyan Book", "Cyan Author", R.color.cyan),
            Book("Magenta Book", "Magenta Author", R.color.magenta))
        val cover = requireView().findViewById<ImageView>(R.id.recommendationCover)
        val title = requireView().findViewById<TextView>(R.id.recommendationTitle)
        val author = requireView().findViewById<TextView>(R.id.recommendationAuthor)
        cover.setImageResource(dummyBooks[0].coverURL)
        title.text = dummyBooks[0].title
        author.text = dummyBooks[0].author

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
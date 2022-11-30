package edu.temple.bookrecommendationsystem

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.chaquo.python.Python

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
        val py = Python.getInstance()
        val pyMod = py.getModule("ratings_refactored")
        val pyRecs = Application.Singleton.csvToBookArray(pyMod.callAttr("generate_recs").toString())
        Application.Singleton.recommendations = pyRecs
        val cover = requireView().findViewById<ImageView>(R.id.recommendation_cover)
        val title = requireView().findViewById<TextView>(R.id.recommendation_title)
        val app = Application.Singleton
        val want = app.wantToRead
        val prev = app.previouslyRead
        Application.Singleton.loadImage(Application.Singleton.recommendations[Application.Singleton.recIndex].coverURL, cover)
        title.text = Application.Singleton.recommendations[Application.Singleton.recIndex].title

        requireView().findViewById<Button>(R.id.dislike_button).setOnClickListener {
            Application.Singleton.recIndex++
            Application.Singleton.loadImage(Application.Singleton.recommendations[Application.Singleton.recIndex].coverURL, cover)
            title.text = Application.Singleton.recommendations[Application.Singleton.recIndex].title
        }

        requireView().findViewById<Button>(R.id.like_button).setOnClickListener {
            want.add(pyRecs[Application.Singleton.recIndex])
            Application.Singleton.recIndex++
            Application.Singleton.loadImage(Application.Singleton.recommendations[Application.Singleton.recIndex].coverURL, cover)
            title.text = Application.Singleton.recommendations[Application.Singleton.recIndex].title
        }

        requireView().findViewById<Button>(R.id.prev_read_button).setOnClickListener {
            prev.add(pyRecs[Application.Singleton.recIndex])
            Application.Singleton.recIndex++
            Application.Singleton.loadImage(Application.Singleton.recommendations[Application.Singleton.recIndex].coverURL, cover)
            title.text = Application.Singleton.recommendations[Application.Singleton.recIndex].title
        }

    }
}
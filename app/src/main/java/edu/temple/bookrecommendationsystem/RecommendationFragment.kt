package edu.temple.bookrecommendationsystem

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.chaquo.python.Python
import kotlinx.coroutines.*


/**
 * Recommendation fragment
 * A fragment class to display recommended books to the user that they can "swipe" left or right on.
 *
 * @constructor Create empty Recommendation fragment
 */
class RecommendationFragment : Fragment() {

    /**
     * On create view
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recommendation, container, false)
    }

    /**
     * On view created
     * Creates buttons for user interaction, dislike, like, or have read
     * Checks the Application Singleton recommendations ArrayList
     * if empty it displays a load screen and calls the python file to generate recommendations
     * else displays the recommendation
     *
     * @param view
     * @param savedInstanceState
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cover = requireView().findViewById<ImageView>(R.id.recommendation_cover)
        val title = requireView().findViewById<TextView>(R.id.recommendation_title)
        val dislikeButton = requireView().findViewById<Button>(R.id.dislike_button)
        val likeButton =requireView().findViewById<Button>(R.id.like_button)
        val readButton = requireView().findViewById<Button>(R.id.prev_read_button)
        val app = Application.Singleton
        val want = app.wantToRead
        val prev = app.previouslyRead
        var deferred : Deferred<Unit>

        var pyRecs = Application.Singleton.recommendations
        if (pyRecs.isEmpty()) {
            title.text = "Generating recommendations..."
            likeButton.visibility = GONE
            dislikeButton.visibility = GONE
            readButton.visibility = GONE
            GlobalScope.launch(Dispatchers.IO) {
                deferred = async {
                    pyRecs = fetchRecs()
                    Application.Singleton.recommendations = pyRecs}
                deferred.await()
                GlobalScope.launch(Dispatchers.Main) {
                    likeButton.visibility = VISIBLE
                    dislikeButton.visibility = VISIBLE
                    readButton.visibility = VISIBLE
                    Application.Singleton.loadImage(Application.Singleton.recommendations[Application.Singleton.recIndex].coverURL, cover)
                    title.text = Application.Singleton.recommendations[Application.Singleton.recIndex].title
                }
            }
        } else {
            Application.Singleton.loadImage(Application.Singleton.recommendations[Application.Singleton.recIndex].coverURL, cover)
            title.text = Application.Singleton.recommendations[Application.Singleton.recIndex].title
        }

        dislikeButton.setOnClickListener {
            Application.Singleton.recIndex++
            Application.Singleton.loadImage(Application.Singleton.recommendations[Application.Singleton.recIndex].coverURL, cover)
            title.text = Application.Singleton.recommendations[Application.Singleton.recIndex].title
        }

        likeButton.setOnClickListener {
            want.add(pyRecs[Application.Singleton.recIndex])
            Application.Singleton.recIndex++
            Application.Singleton.loadImage(Application.Singleton.recommendations[Application.Singleton.recIndex].coverURL, cover)
            title.text = Application.Singleton.recommendations[Application.Singleton.recIndex].title
        }

        readButton.setOnClickListener {
            prev.add(pyRecs[Application.Singleton.recIndex])
            Application.Singleton.recIndex++
            Application.Singleton.loadImage(Application.Singleton.recommendations[Application.Singleton.recIndex].coverURL, cover)
            title.text = Application.Singleton.recommendations[Application.Singleton.recIndex].title
        }
    }

    /**
     * Fetch recs
     *
     * @return an ArrayList of Book objects using Application.Singleton.csvToBookArray
     */
    private fun fetchRecs() : ArrayList<Book> {
        val py = Python.getInstance()
        val pyMod = py.getModule("ratings_refactored")
        return Application.Singleton.csvToBookArray(pyMod.callAttr("generate_recs").toString())
    }

}
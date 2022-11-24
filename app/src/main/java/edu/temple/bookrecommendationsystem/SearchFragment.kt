package edu.temple.bookrecommendationsystem

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast

/*
A fragment class containing a search bar that will send a query to our database and return the top
results for the user to view.
 */

class SearchFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireView().findViewById<ImageButton>(R.id.search_button).setOnClickListener {
            val query = requireView().findViewById<EditText>(R.id.search_edit_text).text
            //call search w query
            //populate recyclerview with book results
            Toast.makeText(this.context, query, Toast.LENGTH_LONG).show()
        }
    }

}
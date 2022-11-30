package edu.temple.bookrecommendationsystem

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chaquo.python.Python

/*
A fragment class containing a search bar that will send a query to our database and return the top
results for the user to view.
 */

class SearchFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!Python.isStarted()) {
            Python.start(Python.getPlatform())
        }
    }


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = requireView().findViewById<RecyclerView>(R.id.search_results_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.adapter = SearchAdapter(Application.Singleton.searchResults) {
            val fragment = BookDetailsFragment(0)
            val bundle = Bundle()
            bundle.putString("title", it.title)
            bundle.putInt("cover", it.coverURL)
            fragment.arguments = bundle
            parentFragmentManager.beginTransaction()
                .replace(R.id.container1, fragment)
                .addToBackStack(null)
                .commit()
        }

        requireView().findViewById<ImageButton>(R.id.search_button).setOnClickListener {
            val query = requireView().findViewById<EditText>(R.id.search_edit_text).text.toString()

//            val py = Python.getInstance()
//            val pyMod = py.getModule("book_Search_refactored")
//            val books = Application.Singleton.csvToBookArray(pyMod.callAttr("main", query).toString())
            // remove next line during integ
            Application.Singleton.searchResults = Application.Singleton.dummyBooks
            //TODO: call search w query. parse results and assign to Application.Singleton.searchResults
            recyclerView.adapter = SearchAdapter(Application.Singleton.searchResults) {

                val fragment = BookDetailsFragment(0)
                val bundle = Bundle()
                bundle.putString("title", it.title)
                bundle.putInt("cover", it.coverURL)
                fragment.arguments = bundle
                parentFragmentManager.beginTransaction()
                    .replace(R.id.container1, fragment)
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

}

package edu.temple.bookrecommendationsystem

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/*
A fragment class that composes the Lists tab in the application and contains two BookListFragments -
"Want to Read" and "Previously Read"
 */

class BookListsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_lists, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val wantRecyclerView = requireView().findViewById<RecyclerView>(R.id.want_recycler_view)
        wantRecyclerView.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.HORIZONTAL, false)
        wantRecyclerView.adapter = ListAdapter(Application.Singleton.wantToRead) {
            val fragment = BookDetailsFragment(1)
            val bundle = Bundle()
            bundle.putString("title", it.title)
            bundle.putString("author", it.author)
            bundle.putInt("cover", it.coverURL)
            fragment.arguments = bundle
            parentFragmentManager.beginTransaction()
                .replace(R.id.container1, fragment)
                .addToBackStack(null)
                .commit()
        }

        val prevRecyclerView = requireView().findViewById<RecyclerView>(R.id.prev_recycler_view)
        prevRecyclerView.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.HORIZONTAL, false)
        prevRecyclerView.adapter = ListAdapter(Application.Singleton.previouslyRead) {
            val fragment = BookDetailsFragment(2)
            val bundle = Bundle()
            bundle.putString("title", it.title)
            bundle.putString("author", it.author)
            bundle.putInt("cover", it.coverURL)
            fragment.arguments = bundle
            parentFragmentManager.beginTransaction()
                .replace(R.id.container1, fragment)
                .addToBackStack(null)
                .commit()
        }

    }

}
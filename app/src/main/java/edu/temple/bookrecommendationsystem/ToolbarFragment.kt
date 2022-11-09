package edu.temple.bookrecommendationsystem

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

/**
 * A simple [Fragment] subclass.
 * Use the [ToolbarFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ToolbarFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_toolbar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.recsButton).setOnClickListener {
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.container1, RecommendationFragment())
                .commit()
        }
        view.findViewById<Button>(R.id.settingsButton).setOnClickListener {
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.container1, SettingsFragment())
                .commit()
        }
        view.findViewById<Button>(R.id.searchButton).setOnClickListener {
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.container1, SearchFragment())
                .commit()
        }
        view.findViewById<Button>(R.id.listsButton).setOnClickListener {
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.container1, BookListsFragment())
                .commit()
        }
    }
}
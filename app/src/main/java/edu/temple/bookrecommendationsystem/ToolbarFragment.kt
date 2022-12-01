package edu.temple.bookrecommendationsystem

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button


/**
 * Toolbar fragment
 * A fragment class that is always present when the application is running. Allows user to switch
 * between the Search, Recommendations, Lists, and Settings fragments to interact with the application.
 *
 * @constructor Create Toolbar fragment
 */
class ToolbarFragment : Fragment() {
    /**
     * On create
     *
     * @param savedInstanceState
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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
        return inflater.inflate(R.layout.fragment_toolbar, container, false)
    }

    /**
     * On view created
     * Populates the fragment with buttons for the Search fragment,
     * the Recommendations fragment, the Lists fragment, and the Settings fragment
     *
     * @param view
     * @param savedInstanceState
     */
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
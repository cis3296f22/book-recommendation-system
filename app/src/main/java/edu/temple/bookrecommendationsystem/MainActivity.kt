package edu.temple.bookrecommendationsystem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

/**
 * Main activity
 * Sets the layout for the app - layout is in the form of fragments
 * Creates and populates 2 fragments
 *
 * @constructor Create Main activity
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.container1, SearchFragment())
            .add(R.id.container2, ToolbarFragment())
            .commit()
    }
}
package edu.temple.bookrecommendationsystem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.container1, RecommendationFragment())
            .add(R.id.container2, ToolbarFragment())
            .commit()
        //populate fragment containers in recyclerview
    }
}
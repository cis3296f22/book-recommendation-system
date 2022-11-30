package edu.temple.bookrecommendationsystem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import android.graphics.Bitmap
import com.bumptech.glide.Glide


/*
A fragment class to display a book cover, title, author, and user rating (if given).
Instance will be created when user clicks on a book from one of their lists or search results.
 */

class BookDetailsFragment(_type: Int) : Fragment() {

    lateinit var title: String
    lateinit var cover: String
    lateinit var book: Book
    var type = _type
    // 0 = search; 1 = want; 2 = prev
    // search - button1 = read, button2 = want
    // want - button1 = remove, button2 = prev
    // prev - button1 = remove, button2 = want

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = arguments?.get("title") as String
        cover = arguments?.get("cover") as String
        book = Book(title, cover)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_book_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val button1 = view.findViewById<Button>(R.id.details_button_1)
        val button2 = view.findViewById<Button>(R.id.details_button_2)
        val prev = "MARK AS READ"
        val want = "WANT TO READ"
        val remove = "REMOVE FROM LIST"
        when (type) {
            0 -> {
                button1.text = prev
                button1.setOnClickListener {
                    Application.Singleton.previouslyRead.add(book)
                    Toast.makeText(requireContext(),
                        "Successfully added to previously read list!",Toast.LENGTH_SHORT).show()
                }
                button2.text = want
                button2.setOnClickListener {
                    Application.Singleton.wantToRead.add(book)
                    Toast.makeText(requireContext(),
                        "Successfully added to Want to Read!",Toast.LENGTH_SHORT).show()
                }
            }
            1 -> {
                button1.text = remove
                button1.setOnClickListener {
                    Application.Singleton.wantToRead.remove(book)
                    Toast.makeText(requireContext(),
                        "Successfully removed from Want To Read!",Toast.LENGTH_SHORT).show()
                }
                button2.text = prev
                button2.setOnClickListener {
                    Application.Singleton.wantToRead.remove(book)
                    Application.Singleton.previouslyRead.add(book)
                    Toast.makeText(requireContext(),
                        "Successfully added to previously read list!",Toast.LENGTH_SHORT).show()
                }
            }
            2 -> {
                button1.text = remove
                button1.setOnClickListener {
                    Application.Singleton.previouslyRead.remove(book)
                    Toast.makeText(requireContext(),
                        "Successfully removed from previously read list!",Toast.LENGTH_SHORT).show()
                }
                button2.text = want
                button2.setOnClickListener {
                    Application.Singleton.previouslyRead.remove(book)
                    Application.Singleton.wantToRead.add(book)
                    Toast.makeText(requireContext(),
                        "Successfully moved to Want to Read!",Toast.LENGTH_SHORT).show()
                }
            }
        }

        Application.Singleton.loadImage(cover,view.findViewById(R.id.details_cover))

        view.findViewById<TextView>(R.id.details_title).text = title
        view.findViewById<Button>(R.id.details_close_button).setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        val myImage: ImageView = view.findViewById<ImageView>(R.id.details_cover)
        val x:Bitmap? = Application.Singleton.imagePull("https://images.gr-assets.com/books/1333576379m/820816.jpg")
        view.findViewById<ImageView>(R.id.details_cover).setImageBitmap(x)
        Application.Singleton.imagePullAlpha("https://images.gr-assets.com/books/1333576379m/820816.jpg", view.findViewById<ImageView>(R.id.details_cover))
        Glide.with(this).load("https://images.gr-assets.com/books/1333576379m/820816.jpg").into(myImage)
    }

}
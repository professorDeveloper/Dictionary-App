package com.azamovhudstc.dictioronaryapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.azamovhudstc.dictioronaryapp.adapter.BookmarkAdapter
import com.azamovhudstc.dictioronaryapp.adapter.RvAdapter
import com.azamovhudstc.dictioronaryapp.db.DbHelper
import kotlinx.android.synthetic.main.activity_book_mark.*

class BookMarkActivity : AppCompatActivity() {
    lateinit var database:DbHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_mark)
         database=DbHelper.getDatabase()
        var adapter=BookmarkAdapter(database.getAllBookmarksEng(),this)
        rv_bookmark.adapter=adapter
        if (database.getAllBookmarksEng().isEmpty()){
            animationView.visibility=View.VISIBLE
            placeHolderTxt.visibility=View.VISIBLE
            rv_bookmark.visibility=View.INVISIBLE
        }
        else{
            animationView.visibility=View.INVISIBLE
            placeHolderTxt.visibility=View.INVISIBLE
            rv_bookmark.visibility=View.VISIBLE

        }
        backbookmarks.setOnClickListener {

            finish()
                    }

    }

    override fun onResume() {
        super.onResume()
        if (database.getAllBookmarksEng().isEmpty()){
            animationView.visibility=View.VISIBLE
            placeHolderTxt.visibility=View.VISIBLE
            rv_bookmark.visibility=View.INVISIBLE
        }
        else{
            animationView.visibility=View.INVISIBLE
            placeHolderTxt.visibility=View.INVISIBLE
            rv_bookmark.visibility=View.VISIBLE

        }

    }
}
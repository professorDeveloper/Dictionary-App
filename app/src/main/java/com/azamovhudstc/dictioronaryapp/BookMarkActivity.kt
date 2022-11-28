package com.azamovhudstc.dictioronaryapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.azamovhudstc.dictioronaryapp.adapter.BookmarkAdapter
import com.azamovhudstc.dictioronaryapp.adapter.RvAdapter
import com.azamovhudstc.dictioronaryapp.db.DbHelper
import com.azamovhudstc.dictioronaryapp.model.Dictionary
import kotlinx.android.synthetic.main.activity_book_mark.*
import kotlinx.android.synthetic.main.bottom_sheet.view.*
import kotlinx.android.synthetic.main.item_rv.view.*

class BookMarkActivity : AppCompatActivity() {
    lateinit var database:DbHelper
    lateinit var adapter: BookmarkAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_mark)
         database=DbHelper.getDatabase()
         adapter=BookmarkAdapter(database.getAllBookmarksEng(),this ,object:BookmarkAdapter.OnBookMarkStudent{
            override fun onClick(course: Dictionary,itemView:View) {
                if (course.isFavourite == 0) {
                    itemView.bookmark.setImageResource(R.drawable.ic_baseline_bookmark_24)
                    course.isFavourite=1
                    database.updateWord(course)
                    adapter.submitItems(database.getAllBookmarksEng())
                }
                else {
                    itemView.bookmark.setImageResource(R.drawable.ic_baseline_bookmark_border_24)
                    course.isFavourite=0
                    database.updateWord(course)
                    adapter.submitItems(database.getAllBookmarksEng())
                }
                Log.d("!@#", "onCreate: ${database.getAllBookmarksEng().size}")

                if (database.getAllBookmarksEng().size ==0){

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




         })
        Log.d("!@#", "onCreate: ${database.getAllBookmarksEng().size}")

        rv_bookmark.adapter=adapter

        if (database.getAllBookmarksEng().size==0){
            animationView.visibility=View.VISIBLE
            placeHolderTxt.visibility=View.VISIBLE
            rv_bookmark.visibility=View.INVISIBLE
        } else{
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
        if (database.getAllBookmarksEng().size ==0){
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
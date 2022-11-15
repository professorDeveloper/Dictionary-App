package com.azamovhudstc.dictioronaryapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.widget.doOnTextChanged
import com.azamovhudstc.dictioronaryapp.adapter.RvAdapter
import com.azamovhudstc.dictioronaryapp.db.DbHelper
import com.azamovhudstc.dictioronaryapp.model.Dictionary
import kotlinx.android.synthetic.main.activity_english_uzbek.*
import kotlin.math.log

class EnglishUzbekActivity : AppCompatActivity() {
    lateinit var handle: Handler
    var query = ""
    lateinit var database: DbHelper
    lateinit var adapter: RvAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_english_uzbek)
        iv_bookmarks.setOnClickListener {
            var inntent = Intent(this, BookMarkActivity::class.java)
            startActivity(inntent)
        }
        database = DbHelper.getDatabase()
        adapter = RvAdapter(database.getAllWords(),this)
        rv_eng.adapter = adapter

        handle = Handler(Looper.getMainLooper())

        search_edit_text.doOnTextChanged { text, _, _, _ ->
            val query = text.toString().lowercase()
            //                handle.removeCallbacksAndMessages(null)

            adapter.query = text.toString()
            handle.postDelayed({
                query?.let {
                    this@EnglishUzbekActivity.query = it.trim()
                    adapter.query = this@EnglishUzbekActivity.query

                    adapter.submitItems(database.getWordByQuery(it.trim()))
                }
            }, 1000)
            toggleImageView(query)
        }
        clear_search_query.setOnClickListener {
            search_edit_text.setText("")

        }

        adapter.setChangeRememberStatusListener { id, newAmount ->
            database.updateWord(newAmount, id)
            Log.d("!@#", "onCreate: ${query.toString()}")
            adapter.query = query

            adapter.submitItems(database.getWordByQuery(query))
            rv_eng.adapter = adapter

        }

    }

    private fun toggleImageView(query: String) {
        if (query.isNotEmpty()) {
            clear_search_query.visibility = View.VISIBLE
        } else if (query.isEmpty()) {
            clear_search_query.visibility = View.INVISIBLE
        }
    }

    override fun onResume() {
        super.onResume()
        adapter.submitItems(database.getAllWords())

        rv_eng.adapter = adapter

    }
}

//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//
//                handle.removeCallbacksAndMessages(null)
//                query?.let {
//                    this@EnglishUzbekActivity.query =it.trim()
//                    adapter.query=this@EnglishUzbekActivity.query
//                    adapter.submitItems(database.getWordByQuery(it.trim()))
//                    adapter.notifyDataSetChanged()
//                }
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                handle.removeCallbacksAndMessages(null)
//                handle.postDelayed({
//                    newText?.let {
//                        this@EnglishUzbekActivity.query =it.trim()
//                        adapter.query=this@EnglishUzbekActivity.query
//
//                        adapter.submitItems(database.getWordByQuery(it.trim()))
//                    }
//                }, 1000)
//                return true
//            }
//
//        })
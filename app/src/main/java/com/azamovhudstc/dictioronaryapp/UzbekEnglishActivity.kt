package com.azamovhudstc.dictioronaryapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.RecognizerIntent
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.azamovhudstc.dictioronaryapp.adapter.RvAdapter
import com.azamovhudstc.dictioronaryapp.adapter.UzbAdapter
import com.azamovhudstc.dictioronaryapp.db.DbHelper
import kotlinx.android.synthetic.main.activity_english_uzbek.*
import kotlinx.android.synthetic.main.activity_english_uzbek.clear_search_query
import kotlinx.android.synthetic.main.activity_uzbek_english.*
import java.util.*


class UzbekEnglishActivity : AppCompatActivity() {
    lateinit var handle: Handler
    var query = ""
    lateinit var database: DbHelper
    lateinit var adapter: UzbAdapter
    var offset: Int=0;

    // on below line we are creating a constant value
    private val REQUEST_CODE_SPEECH_INPUT = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_uzbek_english)
        iv_bookmarks_uzb.setOnClickListener {
            var inntent = Intent(this, BookMarkActivity::class.java)
            startActivity(inntent)
        }
        backuzb.setOnClickListener {
            finish()
        }
        database = DbHelper.getDatabase()

        adapter = UzbAdapter(query,database.getAllWords(offset),this)
        rv_uzb.adapter = adapter
        rv_uzb.setOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                offset=dx
            }
        })

        handle = Handler(Looper.getMainLooper())

        search_edit_text_uzb.doOnTextChanged { text, _, _, _ ->
            val query = text.toString().lowercase()
            adapter.query = text.toString()
            handle.postDelayed({
                query?.let {
                    this@UzbekEnglishActivity.query = it.trim()
                    adapter.query = this@UzbekEnglishActivity.query
                    adapter.submitItems(database.getWordByQueryUZb(it.trim()))

                }
            }, 1000)
            toggleImageView(query)
        }
        clear_search_query_uzb.setOnClickListener {
            search_edit_text_uzb.setText("")

        }

        voice_uzb.setOnClickListener {
            // on below line we are calling speech recognizer intent.
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)

            // on below line we are passing language model
            // and model free form in our intent
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )

            // on below line we are passing our
            // language as a default language.
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault()
            )

            // on below line we are specifying a prompt
            // message as speak to text on below line.
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text")

            // on below line we are specifying a try catch block.
            // in this block we are calling a start activity
            // for result method and passing our result code.
            try {
                startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT)
            } catch (e: Exception) {
                // on below line we are displaying error message in toast

            }
        }
        adapter.setChangeRememberStatusListener { id, newAmount ->
            database.updateWord(newAmount, id)
            Log.d("!@#", "onCreate: ${query.toString()}")
            adapter.submitItems(database.getWordByQueryUZb(query))

        }

    }

    private fun toggleImageView(query: String) {
        if (query.trim().isNotEmpty()) {
            voice_uzb.visibility =View.INVISIBLE
            clear_search_query_uzb.visibility = View.VISIBLE
        } else if (query.trim().isEmpty()) {
            clear_search_query_uzb.visibility = View.INVISIBLE
            voice_uzb.visibility =View.VISIBLE

        }
    }

    override fun onResume() {
        adapter.submitItems(database.getWordByQueryUZb(query))
        super.onResume()

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // in this method we are checking request
        // code with our result code.
        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            // on below line we are checking if result code is ok
            if (resultCode == RESULT_OK && data != null) {

                // in that case we are extracting the
                // data from our array list
                val res: ArrayList<String> =
                    data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) as ArrayList<String>

                // on below line we are setting data
                // to our output text view.
                search_edit_text_uzb.setText(
                    Objects.requireNonNull(res)[0]
                )
            }
        }
    }
}



//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//
//                handle.removeCallbacksAndMessages(null)
//                query?.let {
//                    this@UzbekEnglishActivity.query =it.trim()
//                    adapter.query=this@UzbekEnglishActivity.query
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
//                        this@UzbekEnglishActivity.query =it.trim()
//                        adapter.query=this@UzbekEnglishActivity.query
//
//                        adapter.submitItems(database.getWordByQuery(it.trim()))
//                    }
//                }, 1000)
//                return true
//            }
//
//        })
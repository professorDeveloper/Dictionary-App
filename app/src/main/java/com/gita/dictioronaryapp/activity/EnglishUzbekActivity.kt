package com.gita.dictioronaryapp.activity

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.RecognizerIntent
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.gita.dictioronaryapp.R
import com.gita.dictioronaryapp.adapter.RvAdapter
import com.gita.dictioronaryapp.db.DbHelper
import com.permissionx.guolindev.PermissionX
import kotlinx.android.synthetic.main.activity_english_uzbek.*
import kotlinx.android.synthetic.main.activity_uzbek_english.*
import java.util.*


class EnglishUzbekActivity : AppCompatActivity() {
    lateinit var handle: Handler
    lateinit var searchView: EditText
    private var _query = ""
    lateinit var database: DbHelper
    lateinit var adapter: RvAdapter
    var offset: Int = 0;
    private val REQUEST_CODE_SPEECH_INPUT = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_english_uzbek)
        backs.setOnClickListener {
            finish()
        }

        iv_bookmarks.setOnClickListener {
            var inntent = Intent(this, BookMarkActivity::class.java)
            startActivity(inntent)
        }
        database = DbHelper.getDatabase()

        adapter = RvAdapter(_query, database.getAllWords(offset), this)
        rv_eng.adapter = adapter
        rv_eng.setOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                offset = dx
            }
        })

        voice.setOnClickListener {
            PermissionX.init(this)
                .permissions(
                    Manifest.permission.RECORD_AUDIO
                )
                .request { allGranted, grantedList, deniedList ->
                    if (allGranted) {
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
                    } else {
                        Toast.makeText(
                            this,
                            "These permissions are denied: $deniedList",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            // on below line we are calling speech recognizer intent.

        }
        handle = Handler(Looper.getMainLooper())
        adapter.setChangeRememberStatusListener { id, newAmount ->
            database.updateWord(newAmount, id)
            adapter.arrayList = database.getWordByQuery(_query)
            adapter.notifyDataSetChanged()
        }

        handle = Handler(Looper.getMainLooper())
        searchView = findViewById<EditText>(R.id.search_edit_text)
        searchView.addTextChangedListener {
            handle.removeCallbacksAndMessages(null)
            handle.postDelayed({
                it?.let {
                    adapter.arrayList = database.getWordByQuery(it.toString().trim())
                    adapter.query = it.toString().trim()
                    _query = it.toString().trim()
                    adapter.notifyDataSetChanged()
                }
            }, 1000)
            toggleImageView(it.toString())
        }
        clear_search_query.setOnClickListener {
            searchView.setText("")
        }


    }

    private fun toggleImageView(query: String) {
        if (query.trim().isNotEmpty()) {
            clear_search_query.visibility = View.VISIBLE
            voice.visibility = View.INVISIBLE
        } else if (query.trim().isEmpty()) {
            clear_search_query.visibility = View.INVISIBLE
            voice.visibility = View.VISIBLE

        }
    }

    override fun onResume() {
        adapter.submitItems(database.getWordByQuery(_query))
        super.onResume()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && data != null) {
                val res: ArrayList<String> =
                    data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) as ArrayList<String>
                searchView.setText(
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
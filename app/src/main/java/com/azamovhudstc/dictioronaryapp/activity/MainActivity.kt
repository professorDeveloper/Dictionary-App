package com.azamovhudstc.dictioronaryapp.activity

import android.app.Application
import android.content.DialogInterface
import android.content.Intent
import android.media.Image
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.v4.os.IResultReceiver
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.VIEW_MODEL_STORE_OWNER_KEY
import com.azamovhudstc.dictioronaryapp.BuildConfig
import com.azamovhudstc.dictioronaryapp.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*


class MainActivity : AppCompatActivity() {
    lateinit var drawerLayout: DrawerLayout
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawerLayout = findViewById(R.id.my_drawer_layout)

        setting.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        var inflater =navigation.getHeaderView(0)

        var view =inflater.findViewById<ImageView>(R.id.mode_app)
        val sharedPreferences = getSharedPreferences(
            "sharedPrefs", MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        val isDarkModeOn = sharedPreferences
            .getBoolean(
                "isDarkModeOn", false
            )
        if (isDarkModeOn) {
            AppCompatDelegate
                .setDefaultNightMode(
                    AppCompatDelegate
                        .MODE_NIGHT_YES);
            view.setImageResource(R.drawable.ic_baseline_wb_sunny_24)
        }
        else {
            AppCompatDelegate
                .setDefaultNightMode(
                    AppCompatDelegate
                        .MODE_NIGHT_NO);
            view.setImageResource(R.drawable.ic_baseline_dark_mode_24)
        }
        // after applying dark/light mode

        view.setOnClickListener {
            if (isDarkModeOn) {

                // if dark mode is on it
                // will turn it off
                AppCompatDelegate
                    .setDefaultNightMode(
                        AppCompatDelegate
                            .MODE_NIGHT_NO
                    );
                // it will set isDarkModeOn
                // boolean to false
                editor.putBoolean(
                    "isDarkModeOn", false
                );
                editor.apply();

                // change text of Button
                view.setImageResource(R.drawable.ic_baseline_dark_mode_24)
            } else {

                // if dark mode is off
                // it will turn it on
                AppCompatDelegate
                    .setDefaultNightMode(
                        AppCompatDelegate
                            .MODE_NIGHT_YES
                    );

                // it will set isDarkModeOn
                // boolean to true
                editor.putBoolean(
                    "isDarkModeOn", true
                );
                editor.apply();

                // change text of Button
                view.setImageResource(R.drawable.ic_baseline_wb_sunny_24)
            }
        }

        navigation.setNavigationItemSelectedListener { item ->
            when (item?.itemId) {
                R.id.quit -> {
                    var dialog= AlertDialog.Builder(this)
                    dialog.setTitle("Chiqish ")
                    dialog.setMessage("Ilovadan chiqishni hohlaysizmi ?")
                    dialog.setPositiveButton("Chiqish",object : DialogInterface.OnClickListener{
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            finish()
                            System.out.close()

                        }
                    })
                    dialog.setNegativeButton("Yo`q",object :DialogInterface.OnClickListener{
                        override fun onClick(dialog: DialogInterface?, which: Int) {

                        }
                    })
                    dialog.show()
                                    drawerLayout.closeDrawer(GravityCompat.START)

                }

                R.id.rating -> {
                    val url = "https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID.toString()}"
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(url)
                    startActivity(i)
                    drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.share -> {
                    val sharingIntent = Intent(Intent.ACTION_SEND)

                    // type of the content to be shared

                    // type of the content to be shared
                    sharingIntent.type = "text/plain"

                    // Body of the content

                    // Body of the content
                    val shareBody = "Dictionary App Sharing With"

                    // subject of the content. you can share anything

                    // subject of the content. you can share anything
                    val shareSubject = "https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID.toString()}"

                    // passing body of the content

                    // passing body of the content
                    sharingIntent.putExtra(Intent.EXTRA_TEXT, shareSubject)

                    // passing subject of the content

                    // passing subject of the content
                    sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubject)
                    startActivity(Intent.createChooser(sharingIntent, "Share using"))
                    drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.info -> {
                    var intent = Intent(this@MainActivity, AboutActivity::class.java)
                    startActivity(intent)
                    drawerLayout.closeDrawer(GravityCompat.START)
                }

            }
            true
        };

        btn_uzb_eng.setOnClickListener {
            var intent = Intent(this, UzbekEnglishActivity::class.java)
            startActivity(intent)
        }
        btn_eng_uzb.setOnClickListener {
            var intent = Intent(this, EnglishUzbekActivity::class.java)
            startActivity(intent)
        }

  }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.pop_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }
//    private fun showPopup(view: View) {
//        val popup = PopupMenu(this, view)
//        popup.inflate(R.menu.pop_menu)
//
//        popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item: MenuItem? ->
//            when (item!!.itemId) {
//                R.id.header1 -> {
//                    Toast.makeText(this@MainActivity, item.title, Toast.LENGTH_SHORT).show()
//                }
//                R.id.header2 -> {
//                    var intent=Intent(this,AboutActivity::class.java)
//                    startActivity(intent)
//
//                }
//                R.id.header3 -> {
//                    var dialog=AlertDialog.Builder(this)
//                    dialog.setTitle("Chiqish ")
//                    dialog.setMessage("Ilovadan chiqishni hohlaysizmi ?")
//                    dialog.setPositiveButton("Chiqish",object :DialogInterface.OnClickListener{
//                        override fun onClick(dialog: DialogInterface?, which: Int) {
//                            finish()
//                            System.out.close()
//
//                        }
//                    })
//                    dialog.setNegativeButton("Yo`q",object :DialogInterface.OnClickListener{
//                        override fun onClick(dialog: DialogInterface?, which: Int) {
//
//                        }
//                    })
//                    dialog.show()
//                }
//            }
//
//            true
//        })
//
//        popup.show()
//    }
}
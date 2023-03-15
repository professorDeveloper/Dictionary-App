package com.gita.dictioronaryapp.activity

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.gita.dictioronaryapp.BuildConfig
import com.gita.dictioronaryapp.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*


class MainActivity : AppCompatActivity() {
    lateinit var drawerLayout: DrawerLayout
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
//        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawerLayout = findViewById(R.id.my_drawer_layout)

        setting.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        var inflater = navigation.getHeaderView(0)

//        var view = inflater.findViewById<ImageView>(R.id.mode_app)
//        val sharedPreferences = getSharedPreferences(
//            "sharedPrefs", MODE_PRIVATE
//        )
//        val editor = sharedPreferences.edit()



        navigation.setNavigationItemSelectedListener { item ->
            when (item?.itemId) {
                R.id.quit -> {

                    val dialog = Dialog(this)
                    val view: View = LayoutInflater.from(this)
                        .inflate(R.layout.dialog_exit, my_drawer_layout, false)
                    dialog.setContentView(view)

                    view.findViewById<View>(R.id.button_exit_no).setOnClickListener { view1: View? ->
                        dialog.cancel()
                        dialog.dismiss()
                    }
                    view.findViewById<View>(R.id.button_exit_yes)
                        .setOnClickListener { view2: View? ->
                            dialog.cancel()
                            dialog.dismiss()
                            val a = Intent(Intent.ACTION_MAIN)
                            a.addCategory(Intent.CATEGORY_HOME)
                            a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(a)
                        }
                    dialog.show()

                    dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                    drawerLayout.closeDrawer(GravityCompat.START)

                }

                R.id.rating -> {
                    val url =
                        "https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID.toString()}"
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
                    val shareSubject =
                        "https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID.toString()}"

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
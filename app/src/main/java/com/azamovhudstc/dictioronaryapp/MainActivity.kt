package com.azamovhudstc.dictioronaryapp

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setting.setOnClickListener {
            showPopup(setting)
        }
        btn_eng_uzb.setOnClickListener {
            var intent=Intent(this,EnglishUzbekActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showPopup(view: View) {
        val popup = PopupMenu(this, view)
        popup.inflate(R.menu.pop_menu)

        popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item: MenuItem? ->
            when (item!!.itemId) {
                R.id.header1 -> {
                    Toast.makeText(this@MainActivity, item.title, Toast.LENGTH_SHORT).show()
                }
                R.id.header2 -> {
                    Toast.makeText(this@MainActivity, item.title, Toast.LENGTH_SHORT).show()
                }
                R.id.header3 -> {
                    var dialog=AlertDialog.Builder(this)
                    dialog.setTitle("Chiqish ")
                    dialog.setMessage("Ilovadan chiqishni hohlaysizmi ?")
                    dialog.setPositiveButton("Chiqish",object :DialogInterface.OnClickListener{
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
                }
            }

            true
        })

        popup.show()
    }
}
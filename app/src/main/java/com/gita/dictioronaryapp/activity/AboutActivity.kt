package com.gita.dictioronaryapp.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gita.dictioronaryapp.R
import kotlinx.android.synthetic.main.activity_about.*


class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_about)


        toolbar2.setNavigationOnClickListener { onBackPressed() }
        youtubeIcon.setOnClickListener {
            val url = "https://www.youtube.com/c/GITADasturchilarAkademiyasi"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }
        facebookIcon.setOnClickListener {
            val url = "https://www.facebook.com/profile.php?id=100077062961253"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }
        instagramIcon.setOnClickListener {
            val url = "https://www.instagram.com/gita.uzofficial/"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }
        telegramIcon.setOnClickListener {
            val url = "https://t.me/stc_android"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }

    }

}
package com.gita.dictioronaryapp.utils

import android.app.Activity
import android.speech.tts.TextToSpeech
import android.widget.Toast
import java.util.*

class TTS(private val activity: Activity,
          private val message: String,
          private var br: Boolean) : TextToSpeech.OnInitListener {

    private val tts: TextToSpeech = TextToSpeech(activity, this)

    override fun onInit(i: Int) {
        if (i == TextToSpeech.SUCCESS) {

            val localeBR = Locale("en", "US")
            val localeUS = Locale.US



            br=true
            val result: Int =  tts.setLanguage(localeBR)
            tts.setLanguage(localeUS)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(activity, "This Language is not supported", Toast.LENGTH_SHORT).show()
            } else {
                speakOut(message)
            }

        } else {
            br=false }
    }

    private fun speakOut(message: String) {
        tts.speak(message, TextToSpeech.QUEUE_FLUSH, null, null)
    }
}
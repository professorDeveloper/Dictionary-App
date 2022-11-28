package com.azamovhudstc.dictioronaryapp.adapter

import android.app.Activity
import android.content.Intent
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.azamovhudstc.dictioronaryapp.R
import com.azamovhudstc.dictioronaryapp.db.DbHelper
import com.azamovhudstc.dictioronaryapp.model.Dictionary
import com.azamovhudstc.dictioronaryapp.utils.TTS
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.bottom_sheet.view.*
import kotlinx.android.synthetic.main.item_rv.view.*


class BookmarkAdapter(var arrayList: ArrayList<Dictionary>, var context:Activity,var onClick:OnBookMarkStudent):RecyclerView.Adapter<BookmarkAdapter.Wh>(){
    private var changeRememberStatusListener: ((Int, Int) -> Unit)? = null
    lateinit var bottomSheetDialog:BottomSheetDialog;

    inner class Wh(var view:View):RecyclerView.ViewHolder(view) {
       fun onBind(dictionary: Dictionary,position:Int){
           itemView.word.text=dictionary.english
           var database=DbHelper.getDatabase()
           itemView.textView.text=dictionary.type
           itemView.setOnClickListener {
               var view = LayoutInflater.from(context).inflate(R.layout.bottom_sheet, null, false)
               if (this@BookmarkAdapter::bottomSheetDialog.isInitialized){
                   bottomSheetDialog.dismiss();
               }
               bottomSheetDialog = BottomSheetDialog(context)
               bottomSheetDialog.setContentView(view)
               view.uzb_word.text = dictionary.uzbek
               view.eng_word.text = dictionary.english
               view.iv_back.setOnClickListener {
                   bottomSheetDialog?.dismiss()
               }
               view.iv_volume.setOnClickListener {
                   textToSpeech(dictionary.english)
               }
               view.iv_share.setOnClickListener {
                   val sharingIntent = Intent(Intent.ACTION_SEND)

                   // type of the content to be shared

                   // type of the content to be shared
                   sharingIntent.type = "text/plain"

                   // Body of the content

                   // Body of the content
                   val shareBody = dictionary.toString()

                   // subject of the content. you can share anything

                   // subject of the content. you can share anything
                   val shareSubject = dictionary.toString()

                   // passing body of the content

                   // passing body of the content
                   sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)

                   // passing subject of the content

                   // passing subject of the content
                   sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubject)
                   context.startActivity(Intent.createChooser(sharingIntent, "Share using"))
               }

               view.iv_bookmark.setOnClickListener {
                   if (dictionary.isFavourite == 0) {
                       view.iv_bookmark.setImageResource(R.drawable.ic_baseline_bookmark_24)
                       dictionary.isFavourite = 1
                       database.updateWord(dictionary)
                       submitItems(database.getAllBookmarksEng())
                       changeRememberStatusListener?.invoke(dictionary.id, 1)

                   } else {
                       view.iv_bookmark.setImageResource(R.drawable.ic_baseline_bookmark_border_24)
                       dictionary.isFavourite = 0
                       changeRememberStatusListener?.invoke(dictionary.id, 0)

                       database.updateWord(dictionary)
                       submitItems(database.getAllBookmarksEng())
                   }
               }
               if (dictionary.isFavourite == 1) {
                   view.iv_bookmark.setImageResource(R.drawable.ic_baseline_bookmark_24)
               } else {
                   view.iv_bookmark.setImageResource(R.drawable.ic_baseline_bookmark_border_24)
               }
               view.type.text = dictionary.type
               view.transcription.text = dictionary.transcript
               view.countable.text = dictionary.countable
               bottomSheetDialog.show()
           }

           itemView.bookmark.setOnClickListener {
               onClick.onClick(dictionary,itemView)

           }


           if (dictionary.isFavourite == 1) {
               itemView.bookmark.setImageResource(R.drawable.ic_baseline_bookmark_24)
           }
           else {
               itemView.bookmark.setImageResource(R.drawable.ic_baseline_bookmark_border_24)
           }
       }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Wh {
        return Wh(LayoutInflater.from(parent.context).inflate(R.layout.item_rv,parent,false))
    }

    override fun onBindViewHolder(holder: Wh, position: Int) {
        holder.onBind(arrayList[position],position)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }
    fun submitItems(submitList:ArrayList<Dictionary>){
        arrayList.clear()
        arrayList.addAll(submitList)
        notifyDataSetChanged()
    }
    private lateinit var t: TextToSpeech
    fun textToSpeech(text:String){
        var tts= TTS(context,text,false)

    }
    interface OnBookMarkStudent{
        fun onClick(course: Dictionary,itemView: View)
    }
}

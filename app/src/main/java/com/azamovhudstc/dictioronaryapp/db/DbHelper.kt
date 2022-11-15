package com.azamovhudstc.dictioronaryapp.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.azamovhudstc.dictioronaryapp.model.Dictionary

class DbHelper private constructor(context: Context) : CopyDb(context, "dictionary.db") {
    companion object {
        @SuppressLint("StaticFieldLeak")
        private var database: DbHelper? = null
        fun init(context: Context) {
            database = DbHelper(context)
        }

        fun getDatabase(): DbHelper = database!!
    }
    fun updateWord(dictionary:Dictionary){
        val database =this.writableDatabase
        var contentValues=ContentValues()
        contentValues.put("id",dictionary .id)
        contentValues.put("english",dictionary.english)
        contentValues.put("type",dictionary.type)
        contentValues.put("transcript",dictionary.transcript)
        contentValues.put("uzbek",dictionary.uzbek)
        contentValues.put("countable",dictionary.countable)
        contentValues.put("is_favourite",dictionary.isFavourite)
        contentValues.put("is_favourite_uzb",dictionary.isFavouriteUzbekistan)
         database.update(
            "dictionary",
            contentValues,
            " id = ?",
            arrayOf("${dictionary.id}")
        )
    }

    fun getWordByQuery(str:String):ArrayList<Dictionary>{
        var wordsList=ArrayList<Dictionary>()

        var cursor= getDatabase().rawQuery("select * from dictionary where dictionary   .english like '%$str%'", null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(0)
                val english = cursor.getString(1)
                val type = cursor.getString(2)
                val transcript = cursor.getString(3)
                val uzbek = cursor.getString(4)
                val countable = cursor.getString(5)
                val isFavourite = cursor.getInt(6)
                val favouriteUzb = cursor.getInt(7)
                val contact = Dictionary(id,english,type,transcript,uzbek,countable,isFavourite,favouriteUzb)
                wordsList.add(contact)
            } while (cursor.moveToNext())
        }
        return wordsList

    }
    fun updateWord(remember: Int, id: Int) {
        val cv = ContentValues()
        cv.put("is_favourite", remember)
        getDatabase().update("dictionary", cv, "dictionary.id == $id", null)
    }
    fun getAllWords(): ArrayList<Dictionary> {
        var wordsList=ArrayList<Dictionary>()
        val cursor = getDatabase().rawQuery("select * from dictionary", null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(0)
                val english = cursor.getString(1)
                val type = cursor.getString(2)
                val transcript = cursor.getString(3)
                val uzbek = cursor.getString(4)
                val countable = cursor.getString(5)
                val isFavourite = cursor.getInt(6)
                val favouriteUzb = cursor.getInt(7)
                val contact = Dictionary(id,english,type,transcript,uzbek,countable,isFavourite,favouriteUzb)
                wordsList.add(contact)
            } while (cursor.moveToNext())
        }
        return wordsList
    }
    fun getAllBookmarksEng():ArrayList<Dictionary>{
        var wordsList=ArrayList<Dictionary>()
        val cursor = getDatabase().rawQuery("select * from dictionary where is_favourite = 1 ", null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(0)
                val english = cursor.getString(1)
                val type = cursor.getString(2)
                val transcript = cursor.getString(3)
                val uzbek = cursor.getString(4)
                val countable = cursor.getString(5)
                val isFavourite = cursor.getInt(6)
                val favouriteUzb = cursor.getInt(7)
                val contact = Dictionary(id,english,type,transcript,uzbek,countable,isFavourite,favouriteUzb)
                wordsList.add(contact)
            } while (cursor.moveToNext())
        }
        return wordsList
    }

}

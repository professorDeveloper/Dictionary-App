package com.gita.dictioronaryapp.model

class Dictionary {
    var id = 0
    var english = ""
    var type = ""
    var transcript = ""
    var uzbek = ""
    var countable = ""
    var isFavourite = 0
    var isFavouriteUzbekistan: Int? = null

    constructor(
        id: Int,
        english: String,
        type: String,
        transcript: String,
        uzbek: String,
        countable: String,
        isFavourite: Int,
        isFavouriteUzbekistan: Int?
    ) {
        this.id = id
        this.english = english
        this.type = type
        this.transcript = transcript
        this.uzbek = uzbek
        this.countable = countable
        this.isFavourite = isFavourite
        this.isFavouriteUzbekistan = isFavouriteUzbekistan
    }

    override fun toString(): String {
        return "Uzb text=${uzbek}\nEng text=${english}\nTranscript text${transcript}"
    }


}
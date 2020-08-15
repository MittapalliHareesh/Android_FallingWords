package com.game.fallingwords.model

data class WordItems(val text_eng: String, val text_spa: String)

class BaseResponse {
    var correct: Int = 0
    var incorrect: Int = 0
    var notAttempt: Int = 0
}
package com.game.fallingwords.network

import com.game.fallingwords.model.WordItems
import io.reactivex.Observable
import retrofit2.http.*

interface APIinterface {

    @GET
    fun getWordsList(@Url methodUrl: String): Observable<List<WordItems>>
}
package com.game.fallingwords.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.game.fallingwords.model.WordItems
import com.game.fallingwords.network.APIinterface
import com.game.fallingwords.utils.Constants.ApiName.Companion.wordsListApiURL
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FallingWordsRepository @Inject constructor(val apiInterface: APIinterface) {

    private var disposable: Disposable? = null

    fun getListFromServer(wordList: MutableLiveData<List<WordItems>>): LiveData<List<WordItems>> {

        apiInterface.getWordsList(wordsListApiURL).subscribeOn(Schedulers.newThread())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : Observer<List<WordItems>> {
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                    disposable = d
                }

                override fun onNext(reponse: List<WordItems>) {
                    wordList.value = reponse
                    Log.i("text...", reponse[0].text_eng)
                }

                override fun onError(e: Throwable) {
                    Log.i("error", "${e.message}")
                }
            })
        return wordList
    }

    fun flush() {
        disposable?.dispose()
    }
}

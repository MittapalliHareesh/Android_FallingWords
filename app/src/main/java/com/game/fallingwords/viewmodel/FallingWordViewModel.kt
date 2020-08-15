package com.game.fallingwords.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.game.fallingwords.model.WordItems
import com.game.fallingwords.repo.FallingWordsRepository
import javax.inject.Inject

class FallingWordViewModel @Inject constructor(private val repository: FallingWordsRepository) :
    ViewModel() {

    private val wordItemLiveData = MutableLiveData<List<WordItems>>()

    private val wordListLiveData by lazy {
        MutableLiveData<List<WordItems>>()
    }

    fun getListOfWords() {
        repository.getListFromServer(wordItemLiveData).observeForever { wordList ->
            wordListLiveData.value = wordList
        }
    }

    fun getWordList(): MutableLiveData<List<WordItems>> {
        return wordListLiveData
    }

    fun flush() {
       repository.flush()
    }
}
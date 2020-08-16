package com.game.fallingwords

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.*
import com.game.fallingwords.baseclasses.BaseActivity
import com.game.fallingwords.databinding.ActivityFallingWordsLayoutBinding
import com.game.fallingwords.model.BaseResponse
import com.game.fallingwords.model.WordItems
import com.game.fallingwords.utils.Constants.Companion.DURATION
import com.game.fallingwords.viewmodel.FallingWordViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_falling_words_layout.*
import javax.inject.Inject
import kotlin.random.Random

class FallingWordsActivity : BaseActivity() {

    private lateinit var mBinding: ActivityFallingWordsLayoutBinding
    private lateinit var objectAnimator: ObjectAnimator
    private val baseResponse = BaseResponse()
    private lateinit var wordItemList: List<WordItems>
    private var index = 0
    private var isAttempted = false
    private lateinit var tempWordItem: WordItems


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val fallingWordViewModel by lazy {
        // ViewModelProviders.of(this,viewModelFactory).get(FallingWordViewModel::class.java)
        ViewModelProvider(ViewModelStore(), viewModelFactory).get(FallingWordViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_falling_words_layout)
        init()
    }

    private fun init() {
        val yValueStart = 0F
        val yValueEnd = windowManager?.defaultDisplay?.height!!.toFloat()
        objectAnimator =
            ObjectAnimator.ofFloat(fallingText, View.TRANSLATION_Y, yValueStart, yValueEnd)
                .setDuration(DURATION)
        fallingWordViewModel.getListOfWords()
        fallingWordViewModel.getWordList().observeForever { response ->
            objectAnimator.start()
            wordItemList = response
            tempWordItem = wordItemList.first()
            mBinding.wordItem = tempWordItem
            initializeHeadingTxt(wordItemList.size, index)
        }

        objectAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {
            }

            override fun onAnimationEnd(p0: Animator?) {
                if (!isAttempted) {
                    isAttempted = false
                    baseResponse.notAttempt++
                    index++
                    if (index == wordItemList.size) {
                        showPlayAgainDialog()
                    } else {
                        tempWordItem = wordItemList[index]
                        mBinding.wordItem = tempWordItem
                        mBinding.baseResponse = baseResponse
                        initializeHeadingTxt(wordItemList.size, index)
                        objectAnimator.start()
                    }
                }
            }

            override fun onAnimationCancel(p0: Animator?) {}

            override fun onAnimationStart(p0: Animator?) {
                isAttempted = false
            }
        })
        correct_iv.setOnClickListener { correctInCorrectButtonsLogic(true) }
        incorrect_iv.setOnClickListener { correctInCorrectButtonsLogic(false) }
    }

    private fun correctInCorrectButtonsLogic(isCorrect: Boolean) {
        isAttempted = true
        if (index == wordItemList.size - 1) {
            showPlayAgainDialog()
            objectAnimator.end()
        } else {
            if (tempWordItem.text_spa == wordItemList[index].text_spa) {
                if (isCorrect) baseResponse.correct++ else baseResponse.incorrect++
            } else {
                if (isCorrect) baseResponse.incorrect++ else baseResponse.correct++
            }
            var randomIndex = Random.nextInt(wordItemList.size - 1)
            index++
            tempWordItem =
                WordItems(wordItemList[index].text_eng, wordItemList[randomIndex].text_spa)
            mBinding.wordItem = tempWordItem
            mBinding.baseResponse = baseResponse
            objectAnimator.start()
            isAttempted = false
            initializeHeadingTxt(wordItemList.size, index)
        }
    }

    private fun showPlayAgainDialog() {
        MaterialAlertDialogBuilder(this).setTitle(resources.getString(R.string.endTitle))
            .setMessage(resources.getString(R.string.endDesc))
            .setPositiveButton(resources.getString(R.string.playAgainTxt)) { _, _ ->
                startActivity(Intent(applicationContext, FallingWordsActivity::class.java))
            }
            .setNegativeButton(resources.getString(R.string.exit)) { _, _ -> finishAffinity() }
            .setCancelable(false)
            .show()
    }

    override fun onBackPressed() {
        objectAnimator.pause()
        MaterialAlertDialogBuilder(this).setTitle(resources.getString(R.string.dialog_message))
            .setPositiveButton(resources.getString(R.string.yes)) { _, _ -> finishAffinity() }
            .setNegativeButton(resources.getString(R.string.no)) { dialog, _ ->
                objectAnimator.resume()
                dialog.dismiss()
            }
            .setCancelable(false)
            .show()
    }

    private fun initializeHeadingTxt(size: Int, index: Int) {
        headingTxt.text = String.format(resources.getString(R.string.totalTxt), index, size - 1)
    }

    override fun onDestroy() {
        super.onDestroy()
        fallingWordViewModel.flush()
    }
}
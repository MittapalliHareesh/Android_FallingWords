package com.game.fallingwords

import android.animation.Animator
import android.animation.ObjectAnimator
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

class FallingWordsActivity : BaseActivity() {

    private lateinit var mBinding: ActivityFallingWordsLayoutBinding
    private lateinit var objectAnimator: ObjectAnimator
    private val baseResponse = BaseResponse()
    private lateinit var wordItemList: List<WordItems>
    private var index = 0
    private var isAttempted = false


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
            mBinding.wordItem = wordItemList.first()
            initializeHeadingTxt(wordItemList.size,index)
        }

        objectAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {
            }

            override fun onAnimationEnd(p0: Animator?) {
                if (!isAttempted) {
                    isAttempted = false
                    baseResponse.notAttempt++
                    index++
                    mBinding.baseResponse = baseResponse
                    mBinding.wordItem = wordItemList[index]
                    initializeHeadingTxt(wordItemList.size,index)
                    objectAnimator.start()
                }
            }

            override fun onAnimationCancel(p0: Animator?) {
            }

            override fun onAnimationStart(p0: Animator?) {
                isAttempted = false
                fallingText.visibility = View.VISIBLE
            }
        })

        correct_iv.setOnClickListener {
            isAttempted = true
            index++
            baseResponse.correct++
            mBinding.wordItem = wordItemList[index]
            mBinding.baseResponse = baseResponse
            objectAnimator.start()
            isAttempted = false
            initializeHeadingTxt(wordItemList.size,index)
        }

        incorrect_iv.setOnClickListener {
            isAttempted = true
            index++
            baseResponse.incorrect++
            mBinding.wordItem = wordItemList[index]
            mBinding.baseResponse = baseResponse
            objectAnimator.start()
            isAttempted = false
            initializeHeadingTxt(wordItemList.size,index)
        }
    }

    override fun onBackPressed() {
        objectAnimator.pause()
        MaterialAlertDialogBuilder(this).setTitle(resources.getString(R.string.dialog_message))
            .setPositiveButton("Yes") { _, _ -> finish() }
            .setNegativeButton("No") { dialog, _ ->
                objectAnimator.resume()
                dialog.dismiss()
            }
            .setCancelable(false)
            .show()
    }

    private fun initializeHeadingTxt(size: Int, index: Int) {
        headingTxt.text = String.format(resources.getString(R.string.totalTxt), index, size)
    }

    override fun onDestroy() {
        super.onDestroy()
        fallingWordViewModel.flush()
    }
}
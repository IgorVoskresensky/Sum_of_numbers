package ru.ivos.sum_of_numbers.presentation

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.ivos.sum_of_numbers.R
import ru.ivos.sum_of_numbers.data.GameRepositoryImpl
import ru.ivos.sum_of_numbers.domain.entities.GameResult
import ru.ivos.sum_of_numbers.domain.entities.GameSettings
import ru.ivos.sum_of_numbers.domain.entities.Level
import ru.ivos.sum_of_numbers.domain.entities.Question
import ru.ivos.sum_of_numbers.domain.usecases.GenerateQuestionUseCase
import ru.ivos.sum_of_numbers.domain.usecases.GetGameSettingsUseCase

class GameViewModel(
    private val application: Application,
    private val level: Level
) : ViewModel() {



    private val repository = GameRepositoryImpl

    private val getGameSettingsUseCase = GetGameSettingsUseCase(repository)
    private val generateQuestionUseCase = GenerateQuestionUseCase(repository)


    private lateinit var gameSettings: GameSettings


    private var timer: CountDownTimer? = null
    private var countOfCorrectAnswers = 0
    private var countOfQuestions = 0

    private var _formattedTime = MutableLiveData<String>()
    val formattedTime: LiveData<String> get() = _formattedTime

    private var _question = MutableLiveData<Question>()
    val question: LiveData<Question> get() = _question

    private var _percentOfCorrectAnswers = MutableLiveData<Int>()
    val percentOfCorrectAnswers: LiveData<Int> get() = _percentOfCorrectAnswers

    private var _progressAnswers = MutableLiveData<String>()
    val progressAnswers: LiveData<String> get() = _progressAnswers

    private var _enoughCountOfCorrectAnswers = MutableLiveData<Boolean>()
    val enoughCountOfCorrectAnswers: LiveData<Boolean> get() = _enoughCountOfCorrectAnswers

    private var _enoughPercentOfCorrectAnswers = MutableLiveData<Boolean>()
    val enoughPercentOfCorrectAnswers: LiveData<Boolean> get() = _enoughPercentOfCorrectAnswers

    private var _minPercent = MutableLiveData<Int>()
    val minPercent: LiveData<Int> get() = _minPercent

    private var _gameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult> get() = _gameResult


    init {
        startGame()
    }

    private fun startGame() {
        this.gameSettings = getGameSettingsUseCase(level)
        _minPercent.value = gameSettings.minPercentOfCorrectAnswers
        startTimer()
        generateQuestion()
        updateProgress()
    }

    fun chooseAnswer(selectedOption: Int) {
        val correctAnswer = _question.value?.correctAnswer
        if(selectedOption == correctAnswer){
            countOfCorrectAnswers++
        }
        countOfQuestions++
        updateProgress()
        generateQuestion()
    }

    private fun updateProgress() {
        val percent = if(countOfQuestions != 0){
            ((countOfCorrectAnswers / countOfQuestions.toDouble()) * 100)
                .toInt()
        } else {
            0
        }
        _percentOfCorrectAnswers.value = percent
        _progressAnswers.value = String.format(
            application.resources.getString(R.string.progress_answers),
            countOfCorrectAnswers,
            gameSettings.minCountOfCorrectAnswers
        )
        _enoughCountOfCorrectAnswers.value = countOfCorrectAnswers >= gameSettings.minCountOfCorrectAnswers
        _enoughPercentOfCorrectAnswers.value = percent >= gameSettings.minPercentOfCorrectAnswers
    }

    private fun generateQuestion() {
        _question.value = generateQuestionUseCase(gameSettings.maxSumValue)
    }

    private fun startTimer() {

        timer = object  : CountDownTimer(
            gameSettings.gameTimer * 1000L,
        1000L
        ) {
            override fun onTick(p0: Long) {
                _formattedTime.value = formatTime(p0)
            }

            override fun onFinish() {
                finishGame()
            }
        }
        timer?.start()
    }

    private fun formatTime(millySec: Long): String {
        val sec = millySec /  1000
        val min = sec / 60
        val leftSec = sec - (min * 60)
        return String.format("%02d:%02d", min, leftSec)
    }

    private fun finishGame() {
        _gameResult.value = GameResult(
            winner = _enoughPercentOfCorrectAnswers.value == true && _enoughCountOfCorrectAnswers.value == true,
            countOfCorrectAnswers = countOfCorrectAnswers,
            countIfQuestions = countOfQuestions,
            gameSettings = gameSettings
        )
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }
}
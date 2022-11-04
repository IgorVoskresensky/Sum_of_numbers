package ru.ivos.sum_of_numbers.data

import ru.ivos.sum_of_numbers.domain.entities.GameSettings
import ru.ivos.sum_of_numbers.domain.entities.Level
import ru.ivos.sum_of_numbers.domain.entities.Question
import ru.ivos.sum_of_numbers.domain.repositories.GameRepository
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

object GameRepositoryImpl : GameRepository {

    private const val MIN_SUM_VALUE = 10
    private const val MIN_VISIBLE_NUMBER = 1

    override fun generateQuestion(maxSumValue: Int, countOfOptions: Int): Question {
        val sum = Random.nextInt(MIN_SUM_VALUE, maxSumValue + 1)
        val visibleNumber = Random.nextInt(MIN_VISIBLE_NUMBER, sum)
        val correctAnswer = sum - visibleNumber

        val options = HashSet<Int>()
        options.add(correctAnswer)

        val from = max(correctAnswer - countOfOptions, MIN_VISIBLE_NUMBER)
        val to = min(maxSumValue, correctAnswer + countOfOptions)

        while (options.size < countOfOptions) {
            options.add(Random.nextInt(from, to))
        }

        return Question(sum, visibleNumber, options.toList())
    }

    override fun getGameSettings(level: Level): GameSettings {
        return when(level) {
            Level.TEST -> {
                GameSettings(30, 3, 50, 10)
            }
            Level.EASY -> {
                GameSettings(20, 10, 70, 60)
            }
            Level.NORMAL -> {
                GameSettings(200, 15, 80, 60)
            }
            Level.HARD -> {
                GameSettings(2000, 20, 90, 60)
            }
        }
    }
}
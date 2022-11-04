package ru.ivos.sum_of_numbers.domain.usecases

import ru.ivos.sum_of_numbers.domain.entities.Question
import ru.ivos.sum_of_numbers.domain.repositories.GameRepository

class GenerateQuestionUseCase (
    private val repository: GameRepository
        ) {

    operator fun invoke(maxSumValue: Int): Question {
        return repository.generateQuestion(maxSumValue, COUNT_OF_OPTIONS)
    }

    private companion object {
        const val COUNT_OF_OPTIONS = 6
    }
}
package ru.ivos.sum_of_numbers.domain.usecases

import ru.ivos.sum_of_numbers.domain.entities.GameSettings
import ru.ivos.sum_of_numbers.domain.entities.Level
import ru.ivos.sum_of_numbers.domain.repositories.GameRepository

class GetGameSettingsUseCase(
    private val repository: GameRepository
) {

    operator fun invoke(level: Level): GameSettings {
        return repository.getGameSettings(level)
    }
}
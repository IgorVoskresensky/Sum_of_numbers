package ru.ivos.sum_of_numbers.domain.entities

data class Question (
    val sum: Int,
    val visibleNumber: Int,
    val options: List<Int>
        ) {

    val correctAnswer: Int
    get() = sum - visibleNumber
}
package ru.otus.otuskotlin.coroutines.homework.easy

fun generateNumbers() = (0..10000).map {
    (0..100).random()
}
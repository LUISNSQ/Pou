package com.example.pouxml

data class PouEstado(
    val fome: Int = 67,
    val energia: Int = 100,
    val felicidade: Int = 100,
    val higiene: Int = 100,
    val moedas: Int = 100,
    // Stock de comida: uma lista com os IDs dos recursos drawable
    val stockComida: List<Int> = listOf(R.drawable.item_comida, R.drawable.item_comida)
)

package com.example.pouxml

data class PouEstado(
    val fome: Int = 67,
    val energia: Int = 100,
    val felicidade: Int = 100,
    val higiene: Int = 100, // Ajustei para higiene para bater com o ViewModel
    val moedas: Int = 50
)

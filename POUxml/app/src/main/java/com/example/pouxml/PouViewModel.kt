package com.example.pouxml

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class PouViewModel : ViewModel() {

    var estado = mutableStateOf(PouEstado())
        private set

    fun alimentar() {
        estado.value = estado.value.copy(
            fome = (estado.value.fome + 10).coerceAtMost(100)
        )
    }

    fun dormir() {
        estado.value = estado.value.copy(
            energia = (estado.value.energia + 20).coerceAtMost(100)
        )
    }

    fun tomarBanho() {
        estado.value = estado.value.copy(
            higiene = (estado.value.higiene + 15).coerceAtMost(100)
        )
    }

    fun spriteAtual(): Int {
        return when {
            estado.value.energia < 20 -> R.drawable.pou_dormir
            estado.value.fome < 30 -> R.drawable.pou_fome
            estado.value.higiene < 30 -> R.drawable.pou_sujo
            else -> R.drawable.pou_idle
        }
    }
}

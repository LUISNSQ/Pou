package com.example.pouxml

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PouViewModel : ViewModel() {

    var estado = mutableStateOf(PouEstado())
        private set
    private var contadorCiclos = 0

    // Adicionei um temporizador para o jogo
    private fun gameTimer(){
        viewModelScope.launch {
            while(true) {
                delay(1000) // Isto espera 1 segundo
                // Dormir
                if (estado.value.sleeping == true) {
                    estado.value =
                        estado.value.copy(energia = (estado.value.energia + 5).coerceAtMost(100))
                }
                // Fome e gasto de energia
                else if(estado.value.sleeping == false) {
                    contadorCiclos++
                    if (contadorCiclos >= 10) {
                        estado.value = estado.value.copy(fome = (estado.value.fome - 1).coerceAtLeast(0), energia = (estado.value.energia - 1).coerceAtLeast(0))
                        contadorCiclos = 0
                    }
                }
            }
        }
    }

    init {
        gameTimer()
    }
    fun alimentar() {
        estado.value = estado.value.copy(
            fome = (estado.value.fome + 10).coerceAtMost(100)
        )
    }

    fun comprarItem(preco: Int) {
        if (estado.value.moedas >= preco) {
            estado.value = estado.value.copy(
                moedas = estado.value.moedas - preco
            )
            // Aqui podes disparar um som de moedas ou uma mensagem de "Sucesso!"
        }
    }


    fun dormir() {
        val isSleeping = !estado.value.sleeping
        estado.value = estado.value.copy(sleeping = isSleeping)
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

    fun pouFeliz(){
        estado.value = estado.value.copy(felicidade = (estado.value.felicidade + 10).coerceAtMost(100))
    }


}

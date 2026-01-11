package com.example.pouxml

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class PouViewModel : ViewModel() {

    var estado = mutableStateOf(PouEstado())
        private set

    fun alimentar() {
        if (estado.value.stockComida.isNotEmpty() && !estado.value.estaDormindo) {
            estado.value = estado.value.copy(
                fome = (estado.value.fome + 15).coerceAtMost(100),
                stockComida = estado.value.stockComida.drop(1) // Remove o primeiro item do stock
            )
        }
    }

    fun alternarSono() {
        estado.value = estado.value.copy(estaDormindo = !estado.value.estaDormindo)
    }

    fun dormir() {
        if (estado.value.estaDormindo) {
            estado.value = estado.value.copy(
                energia = (estado.value.energia + 5).coerceAtMost(100)
            )
        }
    }

    fun tomarBanho() {
        if (!estado.value.estaDormindo) {
            estado.value = estado.value.copy(
                higiene = (estado.value.higiene + 20).coerceAtMost(100)
            )
        }
    }

    fun pouFeliz() {
        if (!estado.value.estaDormindo) {
            estado.value = estado.value.copy(
                felicidade = (estado.value.felicidade + 20).coerceAtMost(100)
            )
        }
    }

    fun comprarItem(item: ItemShop) {
        if (estado.value.moedas >= item.preco) {
            val novoInventario = if (item.tipo == TipoItem.ROUPA || item.tipo == TipoItem.ACESSORIO) {
                if (estado.value.inventario.any { it.id == item.id }) estado.value.inventario 
                else estado.value.inventario + item
            } else {
                estado.value.inventario
            }
            
            var novoStockComida = estado.value.stockComida
            if (item.tipo == TipoItem.COMIDA) {
                novoStockComida = novoStockComida + item
            }

            estado.value = estado.value.copy(
                moedas = estado.value.moedas - item.preco,
                inventario = novoInventario,
                stockComida = novoStockComida
            )
        }
    }

    fun vestirRoupa(item: ItemShop?) {
        estado.value = estado.value.copy(roupaEquipada = item)
    }

    fun equiparAcessorio(item: ItemShop?) {
        estado.value = estado.value.copy(acessorioEquipado = item)
    }

    fun spriteAtual(): Int {
        if (estado.value.estaDormindo) return R.drawable.pou_dormir
        
        return when {
            estado.value.fome < 30 -> R.drawable.pou_fome
            estado.value.higiene < 30 -> R.drawable.pou_sujo
            estado.value.energia < 30 -> R.drawable.pou_dormir 
            else -> R.drawable.pou_idle
        }
    }
}

package com.example.pouxml

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PouViewModel : ViewModel() {
    var estado = mutableStateOf(PouEstado())
        private set

    private var contadorCiclos = 0
    private var contadorHigiene = 0

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    // Timer do jogo
    private fun gameTimer() {
        viewModelScope.launch {
            while (true) {
                delay(1000) // Ciclo de 1 segundo

                //Higiene
                contadorHigiene++
                if (contadorHigiene >= 3) {
                    estado.value = estado.value.copy(
                        higiene = (estado.value.higiene - 1).coerceAtLeast(0)
                    )
                    contadorHigiene = 0
                }
                // Dormir
                if (estado.value.sleeping == true) {
                    estado.value =
                        estado.value.copy(energia = (estado.value.energia + 5).coerceAtMost(100))
                }
                // fome e energia
                else if (estado.value.sleeping == false) {
                    contadorCiclos++
                    // A cada 10 segundos, perde 1 de fome e 1 de energia
                    if (contadorCiclos >= 10) {
                        estado.value = estado.value.copy(
                            fome = (estado.value.fome - 1).coerceAtLeast(0),
                            energia = (estado.value.energia - 1).coerceAtLeast(0),
                            felicidade = (estado.value.felicidade - 2).coerceAtLeast(0)

                        )
                        contadorCiclos = 0
                    }
                }
            }
        }
    }

    init {
        gameTimer()
    }

    // Remove um item de comida do stock e aumenta a saciedade do Xamuel
    fun comer(itemRes: Int) {
        val listaAtual = estado.value.stockComida.toMutableList()
        val index = listaAtual.indexOf(itemRes)
        if (index != -1) {
            listaAtual.removeAt(index)
            estado.value = estado.value.copy(
                fome = (estado.value.fome + 15).coerceAtMost(100),
                stockComida = listaAtual
            )
        }
    }

    // Alterna entre o estado de acordado e a dormir
    fun dormir() {
        val isSleeping = !estado.value.sleeping
        estado.value = estado.value.copy(sleeping = isSleeping)
    }

    // Aumenta a higiene
    fun tomarBanho() {
        estado.value = estado.value.copy(
            higiene = (estado.value.higiene + 1).coerceAtMost(100)
        )
    }

    // Sprite do Xamuel
    fun spriteAtual(): Int {
        return when {
            // Se estiver a dormir
            estado.value.sleeping -> R.drawable.pou_dormir
            // Se estiver com fome ou sujo
            estado.value.fome < 30 -> R.drawable.pou_fome
            estado.value.higiene < 30 -> R.drawable.pou_sujo
            // Estado padrão feliz/normal
            else -> R.drawable.pou_idle
        }
    }

    // Aumenta a felicidade (usado em impactos fortes da bola ou cliques)
    fun pouFeliz() {
        estado.value =
            estado.value.copy(felicidade = (estado.value.felicidade + 5).coerceAtMost(100))
    }

    // Aumenta a felicidade lentamente enquanto o utilizador brinca com a bola
    fun pouFelizGradual() {
        // Tem uma probabilidade de 20% de aumentar para não subir demasiado rápido
        if ((0..10).random() > 8) {
            estado.value = estado.value.copy(
                felicidade = (estado.value.felicidade + 1).coerceAtMost(100)
            )
        }
    }

    // Compra e armazenamento de itens
    fun adInventario(item: ItemShop) {
        if (estado.value.moedas >= item.preco) {
            if (item.tipo == TipoItem.COMIDA) {
                // Comida vai para o stock acumulável
                estado.value = estado.value.copy(
                    moedas = estado.value.moedas - item.preco,
                    stockComida = estado.value.stockComida + item.imagemRes
                )
            }
            else {
                // Roupas e acessórios vão para o inventário único
                if (!estado.value.inventario.contains(item)) {
                    estado.value = estado.value.copy(
                        moedas = estado.value.moedas - item.preco,
                        inventario = estado.value.inventario + item
                    )
                }
            }
        }
    }
    
    // Equipa uma peça de roupa no Xamuel
    fun vestirRoupa(item: ItemShop?) {
        estado.value = estado.value.copy(roupaEquipada = item)
        guardarDados()
    }

    // Equipa um acessório no Xamuel
    fun equiparAcessorio(item: ItemShop?) {
        estado.value = estado.value.copy(acessorioEquipado = item)
        guardarDados()
    }

    // Carrega os dados da Firebase
    fun carregarDadosFb() {
        val uid = auth.currentUser?.uid ?: return
        db.collection("jogadores").document(uid).get()
            .addOnSuccessListener { documento ->
                if (documento.exists()) {
                    val dados = documento.toObject(PouEstado::class.java)
                    if (dados != null) {
                        estado.value = dados
                    }
                }
            }
    }

    // Guarda os dados do estado do Xamuel na Firebase
    fun guardarDados() {
        val uid = auth.currentUser?.uid ?: return
        db.collection("jogadores").document(uid).set(estado.value)
    }

}

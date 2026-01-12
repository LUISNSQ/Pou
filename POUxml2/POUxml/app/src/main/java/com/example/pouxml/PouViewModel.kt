package com.example.pouxml

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.io.path.exists

class PouViewModel : ViewModel() {

    var estado = mutableStateOf(PouEstado())
        private set
    private var contadorCiclos = 0
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    // Adicionei um temporizador para o jogo
    private fun gameTimer() {
        viewModelScope.launch {
            while (true) {
                delay(1000) // Isto espera 1 segundo
                // Dormir
                if (estado.value.sleeping == true) {
                    estado.value =
                        estado.value.copy(energia = (estado.value.energia + 5).coerceAtMost(100))
                }
                // Fome e gasto de energia
                else if (estado.value.sleeping == false) {
                    contadorCiclos++
                    if (contadorCiclos >= 10) {
                        estado.value = estado.value.copy(
                            fome = (estado.value.fome - 1).coerceAtLeast(0),
                            energia = (estado.value.energia - 1).coerceAtLeast(0)
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

    // Comer + fome
    fun alimentar() {
        estado.value = estado.value.copy(
            fome = (estado.value.fome + 10).coerceAtMost(100)
        )
    }

    // Para ele adormecer
    fun dormir() {
        val isSleeping = !estado.value.sleeping
        estado.value = estado.value.copy(sleeping = isSleeping)
    }

    // Toma banho +higiene
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

    // Felicidade do pou
    fun pouFeliz() {
        estado.value =
            estado.value.copy(felicidade = (estado.value.felicidade + 10).coerceAtMost(100))
    }

    //Comprrar itens e adiciona los ao inventário
    fun adInventario(item: ItemShop) {
        if (estado.value.moedas >= item.preco) {
            if (item.tipo == TipoItem.COMIDA) {
                estado.value = estado.value.copy(
                    moedas = estado.value.moedas - item.preco,
                    stockComida = estado.value.stockComida + item.imagemRes
                )
            }
            // Roupa
            else {
                if (!estado.value.inventario.contains(item)) {
                    estado.value = estado.value.copy(
                        moedas = estado.value.moedas - item.preco,
                        inventario = estado.value.inventario + item
                    )
                }
            }
        }
    }
    // Vestir
    fun vestirRoupa(item: ItemShop?) {
        estado.value = estado.value.copy(roupaEquipada = item)
       //  guardarDados()
    }

    // Vestir acessorio
    fun equiparAcessorio(item: ItemShop?) {
        estado.value = estado.value.copy(acessorioEquipado = item)
        //guardarDados()
    }

    // Carregar dados da Firebase
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

    // Guardar na firebase
    fun guardarDados() {
        val uid = auth.currentUser?.uid ?: return
        db.collection("jogadores").document(uid).set(estado.value)
    }

}



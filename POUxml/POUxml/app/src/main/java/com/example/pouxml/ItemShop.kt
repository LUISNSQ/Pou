package com.example.pouclone.data.model

enum class TipoItem {
    COMIDA, ROUPA
}

data class ItemShop(
    val id: Int,
    val nome: String,
    val preco: Int,
    val tipo: TipoItem
)

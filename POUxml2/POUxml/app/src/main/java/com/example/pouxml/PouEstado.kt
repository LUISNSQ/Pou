package com.example.pouxml

data class PouEstado(
    val fome: Int = 100,
    val energia: Int = 100,
    val felicidade: Int = 100,
    val higiene: Int = 100,
    val moedas: Int = 1000,
    val estaDormindo: Boolean = false,
    val stockComida: List<ItemShop> = listOf(
        ItemShop(1, "Maçã", 10, TipoItem.COMIDA, R.drawable.item_comida),
        ItemShop(1, "Maçã", 10, TipoItem.COMIDA, R.drawable.item_comida)
    ),
    val inventario: List<ItemShop> = emptyList(),
    val roupaEquipada: ItemShop? = null,
    val acessorioEquipado: ItemShop? = null
)

data class ItemShop(
    val id: Int,
    val nome: String,
    val preco: Int,
    val tipo: TipoItem,
    val imagemRes: Int
)

enum class TipoItem {
    COMIDA, ROUPA, ACESSORIO
}

package com.example.pouxml

data class PouEstado(
    val fome: Int = 67,
    val energia: Int = 50,
    val felicidade: Int = 100,
    val higiene: Int = 50,
    val moedas: Int = 900,
    val nomeUser: String = "",
    val sleeping: Boolean = false,
    val inventario: List<ItemShop> = emptyList(), // Lista de itens
    val roupaEquipada: ItemShop? = null,
    val acessorioEquipado: ItemShop? = null,
    // Stock de comida: uma lista com os IDs dos recursos drawable
    val stockComida: List<Int> = emptyList()
)
enum class TipoItem {
    COMIDA, ROUPA, ACESSORIO
}
data class ItemShop(
    val id: Int =0,
    val nome: String = "",
    val preco: Int= 0,
    val tipo: TipoItem = TipoItem.ROUPA,
    val imagemRes: Int =0,
)

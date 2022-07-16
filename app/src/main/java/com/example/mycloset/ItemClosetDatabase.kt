package com.example.mycloset

import java.io.Serializable

open class ItemCloset(
    var nome : String = "",
    var preco_compra : String = "",
    var preco_venda : String = "",
    var foto : String = "",
    var categoria : String = "",
    var descricao : String = ""
)

class ItemClosetId(var id : String = "", var obj : ItemCloset = ItemCloset())

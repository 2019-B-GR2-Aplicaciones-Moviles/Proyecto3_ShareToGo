package com.example.sharetogo.models

import java.util.*
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import com.google.type.Date
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.LinkedHashMap

@IgnoreExtraProperties
data class Rutas(
    var id: String? = "",
    var calles: ArrayList<String> = ArrayList(),
    var capacidad: Int = 0,
    var color: String = "",
    var disponible: Boolean = true,
    var hora: String = "",
    var modelo_carro: String = "",
    var pasajeros: Int = 0,
    var placa_carro: String = "",
    var sector_fin: String = "",
    var sector_ini: String = "",
    var sentido: String = ""
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "calles" to calles,
            "capacidad" to capacidad,
            "color" to color,
            "disponible" to disponible,
            "hora" to hora,
            "modelo_carro" to modelo_carro,
            "pasajeros" to pasajeros,
            "placa_carro" to placa_carro,
            "sector_fin" to sector_fin,
            "sector_ini" to sector_ini,
            "sentido" to sentido
        )
    }
}
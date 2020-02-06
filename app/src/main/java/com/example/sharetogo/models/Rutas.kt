package com.example.sharetogo.models

import java.util.*

class Rutas(
    var id: String,
    var calles: Map<Int, String>,
    var capacidad: Int,
    var color: String,
    var disponible: Boolean,
    var hora: Date,
    var modelo_carro: String,
    var pasajeros: Int,
    var placa_carro: String,
    var sector_fin: String,
    var sector_ini: String,
    var sentido: String
)
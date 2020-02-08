package com.example.sharetogo.models

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import kotlin.collections.ArrayList

@IgnoreExtraProperties
data class Usuarios(
    var userId: String = "",
    var nombre: String? = "",
    var contraseña: String? = "",
    var correo: String = "",
    var rol: String = "",
    var telefono: String? = "",
    var activo: Boolean = true
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to userId,
            "nombre" to nombre,
            "contraseña" to contraseña,
            "correo" to correo,
            "rol" to rol,
            "telefono" to telefono,
            "activo" to activo
        )
    }
}
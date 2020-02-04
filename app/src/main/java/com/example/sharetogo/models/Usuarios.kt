package com.example.sharetogo.models

class Usuarios(
    var userId: String,
    var nombre: String? = "",
    var contraseña: String? = "",
    var correo: String,
    var rol: String,
    var telefono: String? = "",
    var rutas: String? = "",
    var activo: Boolean
)
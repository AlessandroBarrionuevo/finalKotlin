package com.istea.finalintegrador.dto

import java.io.Serializable

open class AbstracAlimentacion(
    var tipoDeComida: String, var comidaPrincipal: String,
    var comidaSecundaria: String, var bebida: String,
    var postre: String, var tentacion: String, var lleno:Boolean
) :Serializable {
}
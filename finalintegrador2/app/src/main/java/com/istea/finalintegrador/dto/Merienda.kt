package com.istea.finalintegrador.dto

import java.time.LocalDate

class Merienda(
    tipoDeComida: String,
    comidaPrincipal: String,
    comidaSecundaria: String,
    bebida: String,
    postre: String,
    tentacion: String, lleno: Boolean,
) : AbstracAlimentacion(tipoDeComida,
    comidaPrincipal,
    comidaSecundaria,
    bebida,
    postre,
    tentacion, lleno) {
}
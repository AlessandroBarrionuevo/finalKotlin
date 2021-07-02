package com.istea.finalintegrador.dto

import java.io.Serializable
import java.time.LocalDate
import kotlin.collections.ArrayList

class Pacientes(var nombre:String,var apellido:String,
                var sexo:String, var localidad:String,
                var usuario: Usuario,
                var fechaDeNacimiento: LocalDate, var dni: Number,
                var tipoTratamiento: String): Serializable {
                var listaDesayuno= arrayListOf<Desayuno>(Desayuno("","","","","","",true))
                var listaAlmuerzo = arrayListOf<Almuerzo>(Almuerzo("","","","","","",true))
                var listaCena= arrayListOf<Cena>(Cena("","","","","","",true))
                var listaMerienda= arrayListOf<Merienda>(Merienda("","","","","","",true))

    fun agregarAlmuerzo(almuerzo: Almuerzo){
        listaAlmuerzo.add(almuerzo)
    }

    fun agregarDesayuno(desayuno: Desayuno){
        listaDesayuno.add(desayuno)
    }

    fun agregarCena(cena: Cena){
        listaCena.add(cena)
    }

    fun agregarMerienda(merienda: Merienda){
        listaMerienda.add(merienda)
    }
}
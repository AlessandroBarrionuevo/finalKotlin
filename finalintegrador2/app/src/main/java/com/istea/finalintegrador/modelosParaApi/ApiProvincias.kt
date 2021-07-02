package com.istea.api.implementation

import Json4Kotlin_Base
import com.istea.finalintegrador.APISERVICE.ProvinciasAPI
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiProvincias {
    // generar la vinculacion entre retrofit y el endpoint
    // endpoint = url API
    private fun getRetrofit() : Retrofit {

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://apis.datos.gob.ar/georef/")
            .build()
    }

    // invocamos al interfaz que tiene la invocacion al endpoint
    fun getProvincias(): Call<Json4Kotlin_Base> {
        return getRetrofit().create(ProvinciasAPI::class.java).getProvincias()
    }

    fun getProvincia(): Call<Json4Kotlin_Base> {
        return getRetrofit().create(ProvinciasAPI::class.java).getProvincias()
    }
}
package com.istea.finalintegrador.APISERVICE

import Json4Kotlin_Base
import com.istea.api.implementation.ObjGeneral
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ProvinciasAPI {

    @GET("api/provincias")
    fun getProvincias(): retrofit2.Call<Json4Kotlin_Base>

    companion object{

        fun create() : ProvinciasAPI{
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://apis.datos.gob.ar/georef/")
                .build()

            return retrofit.create(ProvinciasAPI::class.java)
        }

    }
}
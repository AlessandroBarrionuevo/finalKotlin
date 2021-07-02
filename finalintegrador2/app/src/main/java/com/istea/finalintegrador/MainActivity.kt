package com.istea.finalintegrador

import Json4Kotlin_Base
import Provincias
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import com.istea.api.implementation.ApiProvincias
import com.istea.finalintegrador.dao.DBHelperUsers
import com.istea.finalintegrador.dto.Pacientes
import com.istea.finalintegrador.dto.Usuario
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate

class MainActivity : AppCompatActivity() {

    lateinit var lblMain: TextView
    lateinit var eUsuario: EditText
    lateinit var eContrasena: EditText
    lateinit var btnLogin: Button
    lateinit var btnRegister: Button
    lateinit var registerLayout: LinearLayout

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //varibles globales
        var listaUsuarios = arrayListOf<Usuario>()
        var listaPacientes = arrayListOf<Pacientes>()
        var listaProvincias= arrayListOf<Provincias>()
        var listaProvinciasNombres = arrayListOf<String>("caba","chaco","bsas")
        var eleccionProv = ""
        val adapterSpinnerProv = ArrayAdapter(this, android.R.layout.simple_spinner_item, listaProvinciasNombres)

        //viewRegister
        var view = LayoutInflater.from(this).inflate(R.layout.register_layout,null)
        val lblMainRegister:TextView = view.findViewById(R.id.lblMainRegistrer)
        val eRegisterUsuario:EditText = view.findViewById(R.id.eMainRegistrerUsuario)
        val eRegisterContrasena: EditText =view.findViewById(R.id.eMainRegistrerContrasena)
        val eRegisterBtn:Button = view.findViewById(R.id.btnRegistrar)
        val eRegisterNombre: TextView = view.findViewById(R.id.eMainRegisterNombre)
        val eRegisterApellido: TextView = view.findViewById(R.id.eMainRegisterApellido)
        val eRegisterDni: TextView = view.findViewById(R.id.eMainRegisterDni)
        val eRegisterSexo: TextView = view.findViewById(R.id.eMainRegisterGender)
        val eRegisterLocalidad: TextView = view.findViewById(R.id.eMainRegisterLocalidad)
        val eRegisterCumpleanios: TextView = view.findViewById(R.id.eMainRegisterFechaDeNacimiento)
        val eRegisterPatologia: TextView = view.findViewById(R.id.eMainRegisterPatologia)
        val spinner: Spinner = view.findViewById(R.id.spinner2)

        listaUsuarios.add(Usuario("admin","admin"))
        //inicio
        inicializador()
        val apiProvincias = ApiProvincias()
        //logica

        //comienzo obteniendo la lista de provincias

        //dejo el codigo de la toma de lista desde la api///////////////

        /*apiProvincias.getProvincias().enqueue(object: Callback<Json4Kotlin_Base>{
            override fun onResponse(
                call: Call<Json4Kotlin_Base>,
                response: Response<Json4Kotlin_Base>
            ) {
                if(response.body() != null){
                    val data = response.body()
                    listaProvincias = data?.provincias as ArrayList<Provincias>

                    listaProvincias.forEach {
                        listaProvinciasNombres.add(it.nombre)
                    }
                    Toast.makeText(applicationContext,"entro",Toast.LENGTH_LONG).show()
                    spinner.adapter = adapterSpinnerProv

                }else{
                    Toast.makeText(applicationContext,"body vacio",Toast.LENGTH_LONG).show()

                }
            }

            override fun onFailure(call: Call<Json4Kotlin_Base>, t: Throwable) {
                generadorToastCorto("errror")
            }

        })
*/

        //al logearse
        val db: DBHelperUsers = DBHelperUsers(this,null)

        //objeto de prueba para poder utilizar la logica de que si es un usuario pero no un paciente complete los datos
        db.ingresarUsuario(Usuario("ale","aa"))


        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                eleccionProv = listaProvinciasNombres[position]
                generadorToastCorto(eleccionProv)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }


        btnLogin.setOnClickListener {
            var usuario= Usuario(eUsuario.text.toString(),eContrasena.text.toString())
            if (db.validateUser(usuario)){
                listaUsuarios = db.obtenerUsuarios()
                if(verificarSiEsPaciente(listaPacientes, usuario)){
                    if (obtenerUsuarioDeSesion(listaUsuarios)) {
                        var dniUsuarioDeSesion: Int = verificarCredenciales(listaPacientes)
                        var intent: Intent = Intent(this, formularioActivity::class.java)
                        intent.putExtra("listaPacientes", listaPacientes)
                        intent.putExtra("dniDeUsuario", dniUsuarioDeSesion)
                        startActivity(intent)
                    }
                }else{
                    generadorToastCorto("Usted no es paciente, por favor complete sus datos")
                    eRegisterUsuario.setText(eUsuario.text.toString())
                    eRegisterContrasena.setText(eContrasena.text.toString())
                    registerLayout.removeAllViews()
                    registerLayout.addView(view)
                }
            }
        }

        btnRegister.setOnClickListener {

            registerLayout.removeAllViews()
            registerLayout.addView(view)
        }

        eRegisterBtn.setOnClickListener {
            var miUser: Usuario = Usuario(eRegisterUsuario.text.toString(),eRegisterContrasena.text.toString())
            if(!db.validateUser(miUser)){
                db.ingresarUsuario(miUser)
                listaPacientes.add(Pacientes(
                    eRegisterNombre.text.toString(),eRegisterApellido.text.toString(),
                    eRegisterSexo.text.toString(),
                    eleccionProv, miUser
                    , LocalDate.parse("2021-12-31"),eRegisterDni.text.toString().toInt(),eRegisterPatologia.text.toString()))
                generadorToastCorto("Registrado Con exito")
                registerLayout.removeAllViews()
            }
            if (db.validateUser(miUser)){
                if (!verificarSiEsPaciente(listaPacientes, miUser)){
                    listaPacientes.add(Pacientes(
                        eRegisterNombre.text.toString(),eRegisterApellido.text.toString(),
                        eRegisterSexo.text.toString(),
                        eleccionProv, miUser
                        , LocalDate.parse("2021-12-31"),eRegisterDni.text.toString().toInt(),eRegisterPatologia.text.toString()))
                    generadorToastCorto("Registrado Con exito")
                    registerLayout.removeAllViews()
                }
            }
        }

    }

    @SuppressLint("ResourceType")
    private fun inicializador(){
        lblMain = this.findViewById(R.id.lblMain)
        eUsuario = findViewById(R.id.eMainUsuario)
        eContrasena = findViewById(R.id.eMainContrasena)
        btnLogin = findViewById(R.id.btnLogin)
        btnRegister= findViewById(R.id.btnRegister)
        registerLayout= findViewById(R.id.registerLayout)
    }

    fun verificarCredenciales(listaPacientes: ArrayList<Pacientes>): Int {
        var aprobado: Int = 0
        //si la lista no esta vacia
        if (listaPacientes!= null){
            //verifico datos en la lista
            listaPacientes.forEach {
                //si el usuario existe verifico la contrasena
                if (it.usuario.nickName.equals(eUsuario.text.toString())){
                    //si la credenciales coinciden
                    if (it.usuario.password.equals(eContrasena.text.toString())){
                        aprobado = it.dni as Int
                       return aprobado
                    }
                }
            }
        }
         return aprobado
    }

    fun verificarSiEsPaciente(listaPacientes: ArrayList<Pacientes>, user:Usuario): Boolean{
        if (listaPacientes!= null){
            //verifico datos en la lista
            listaPacientes.forEach {
                //si el usuario existe verifico la contrasena
                if (it.usuario.nickName.equals(eUsuario.text.toString())){
                    //si la credenciales coinciden
                    if (it.usuario.password.equals(eContrasena.text.toString())){
                        if(it.dni.toInt() >= 0 ){
                            return true
                        }
                    }
                }
            }
        }
        return false
    }

    fun obtenerUsuarioDeSesion(listaUsuarios: ArrayList<Usuario>): Boolean {
        var aprobado: Boolean = false
        //si la lista no esta vacia
        if (listaUsuarios!= null){
            //verifico datos en la lista
            listaUsuarios.forEach {
                //si el usuario existe verifico la contrasena
                if (it.nickName.equals(eUsuario.text.toString())){
                    //si la credenciales coinciden
                    if (it.password.equals(eContrasena.text.toString())){
                        aprobado = true
                        return aprobado
                    }
                }
            }
        }
        return aprobado
    }

    fun generadorToastCorto(msj:String){
        Toast.makeText(this,msj,Toast.LENGTH_SHORT).show()
    }

}
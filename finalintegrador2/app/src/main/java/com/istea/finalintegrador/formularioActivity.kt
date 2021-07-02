package com.istea.finalintegrador

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.istea.finalintegrador.dto.*
import org.w3c.dom.Text
import java.time.LocalDate

class formularioActivity : AppCompatActivity() {
    lateinit var formLblMain: TextView
    lateinit var formLblTipoComida: TextView
    lateinit var formSpinnerTC : Spinner
    lateinit var eFormComidaPrincipal : EditText
    lateinit var eFormComidaSecundaria: EditText
    lateinit var eFormBebida: EditText
    lateinit var formLblIngirioPOstre: TextView
    lateinit var eFormIngirioPostre: EditText
    lateinit var formCBTentacion: CheckBox
    lateinit var eFormAlimento: EditText
    lateinit var formCBHambre: CheckBox
    lateinit var formBtnGuardar: Button
    lateinit var pruebas:TextView
    lateinit var verForm: Button

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario)

        inicializador()
        //variables globales
        var eleccionComida= ""
        var listaDePacientes = arrayListOf<Pacientes>()
        var usuarioDni: Int
        var alimentoQueQueriaIngerir=""

        listaDePacientes = intent.getSerializableExtra("listaPacientes") as ArrayList<Pacientes>
        usuarioDni = intent.getSerializableExtra("dniDeUsuario") as Int

        //listas para spinners
        var listaComidas = arrayListOf<String>("Desayuno","Almuerzo","Merienda","Cena")

        //adapters
        val adapterSpinnerForm = ArrayAdapter(this, android.R.layout.simple_spinner_item, listaComidas)
        formSpinnerTC.adapter = adapterSpinnerForm

        //inicializador

        //logica
        //voy a ver que tipo de comida se eligio y se va a guardar esa lista
        var usuarioCualTrabajar: Pacientes = listaDePacientes[main(listaDePacientes, usuarioDni)]
        var posicion: Int = main(listaDePacientes,usuarioDni)

        formSpinnerTC.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ){
                eleccionComida = listaComidas[position]
                generadorToastCorto(eleccionComida)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {generadorToastCorto("Nada seleccionado")}
        }


        formCBTentacion.setOnClickListener {
            eFormAlimento.isEnabled = true
        }

        formBtnGuardar.setOnClickListener {
            if(formCBTentacion.isChecked){
                alimentoQueQueriaIngerir = eFormAlimento.text.toString()
            }
            if (eleccionComida.contains("Desayuno")){
                var desayuno: Desayuno = Desayuno(
                eleccionComida,eFormComidaPrincipal.text.toString(),
                eFormComidaSecundaria.text.toString(),eFormBebida.text.toString(),
                formLblIngirioPOstre.text.toString(), alimentoQueQueriaIngerir, formCBHambre.isChecked)
                listaDePacientes[posicion].agregarDesayuno(desayuno)
                pruebas.setText(listaDePacientes[posicion].listaDesayuno.size.toString())
                listaDePacientes[posicion].listaDesayuno.forEach {
                    pruebas.setText(" ${it.comidaPrincipal} ${it.comidaSecundaria}  $alimentoQueQueriaIngerir \n")
                }
            }
            if (eleccionComida == "Almuerzo"){
                var almuerzo: Almuerzo = Almuerzo(eleccionComida,eFormComidaPrincipal.text.toString(),
                eFormComidaSecundaria.text.toString(),eFormBebida.text.toString(),
                formLblIngirioPOstre.text.toString(),alimentoQueQueriaIngerir,formCBHambre.isChecked)
                listaDePacientes[posicion].agregarAlmuerzo(almuerzo)
                listaDePacientes[posicion].listaAlmuerzo.forEach {
                    pruebas.setText(" ${it.comidaPrincipal} ${it.comidaSecundaria}  $alimentoQueQueriaIngerir\n")
                }
            }
            if (eleccionComida.contains("Cena")){
                var cena: Cena = Cena( eleccionComida,eFormComidaPrincipal.text.toString(),
                eFormComidaSecundaria.text.toString(),eFormBebida.text.toString(),
                formLblIngirioPOstre.text.toString(), alimentoQueQueriaIngerir,formCBHambre.isChecked)
                listaDePacientes[posicion].agregarCena(cena)
                pruebas.setText(listaDePacientes[posicion].listaCena.size.toString())
                listaDePacientes[posicion].listaCena.forEach {
                    pruebas.setText(" ${it.comidaPrincipal} ${it.comidaSecundaria}  $alimentoQueQueriaIngerir \n")
                }
            }
            generadorToastCorto("error")
        }

    }
    //funciones
    fun inicializador(){
        formLblMain = findViewById(R.id.formLblMain)
        formLblTipoComida = findViewById(R.id.formLblTipoComida)
        formSpinnerTC = findViewById(R.id.formSpinnerTC)
        eFormComidaPrincipal = findViewById(R.id.eFormComidaPrincipal)
        eFormComidaSecundaria = findViewById(R.id.eFormComidaSecundaria)
        eFormBebida = findViewById(R.id.eFormBebida)
        formLblIngirioPOstre = findViewById(R.id.formLblIngirioPOstre)
        eFormIngirioPostre = findViewById(R.id.eFormIngirioPostre)
        formCBTentacion = findViewById(R.id.formCBTentacion)
        eFormAlimento = findViewById(R.id.eFormAlimento)
        formCBHambre = findViewById(R.id.formCBHambre)
        formBtnGuardar = findViewById(R.id.formBtnGuardar)
        pruebas = findViewById(R.id.pruebas)
        verForm = findViewById(R.id.btnVer)
    }



    fun main(listaDePacientes: ArrayList<Pacientes>, dniABuscar: Int):Int {
        var posicionPaciente: Int = 0
        for ((posicion, valor) in listaDePacientes.withIndex()) {
            if (valor.dni == dniABuscar){
                posicionPaciente = posicion
                return posicionPaciente
            }
        }
        return posicionPaciente
    }

    fun generadorToastCorto(msj:String){
        Toast.makeText(this,msj,Toast.LENGTH_SHORT).show()
    }

}
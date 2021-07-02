package com.istea.finalintegrador.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.istea.finalintegrador.dto.Usuario
import java.lang.Exception

class DBHelperUsers(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    companion object {
        private val DATABASE_NAME = "usuarios.db"
        private val DATABASE_VERSION = 1
        val TABLE_USUARIOS = "usuariostable"
        val COLUMN_NICKNAME = "nickname"
        val COLUMN_PASSWORD = "password"
        val COLUMN_ID = "id"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = ("CREATE TABLE ${TABLE_USUARIOS} ( ${COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT, ${COLUMN_NICKNAME} TEXT, $COLUMN_PASSWORD TEXT )")
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIOS)
        onCreate(db)
    }

    fun ingresarUsuario(nuevo: Usuario) {
        var db = this.writableDatabase

        val values = ContentValues()

        values.put(COLUMN_NICKNAME, nuevo.nickName)
        values.put(COLUMN_PASSWORD, nuevo.password)

        db?.insert(TABLE_USUARIOS, null, values)
    }

    fun validateUser(user: Usuario): Boolean {

        var db = this.readableDatabase

        var querySelect = "SELECT * FROM " + TABLE_USUARIOS

        val cursor = db.rawQuery(querySelect, null)

        if (cursor.moveToFirst()) {
            do {
                val nick = cursor.getString(cursor.getColumnIndex(COLUMN_NICKNAME))
                val password = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD))

                if (user.nickName.equals(nick) && user.password.equals(password)) {
                    return true
                }
            } while (cursor.moveToNext())
        }
        return false
    }

    fun obtenerUsuarios():ArrayList<Usuario>{
        val lista: ArrayList<Usuario> = ArrayList<Usuario>();
        val queryselect = "SELECT * FROM "+ TABLE_USUARIOS
        val db= this.readableDatabase
        val cursor = db.rawQuery(queryselect,null)

        if(cursor.moveToFirst()){
            do{
                val nickname = cursor.getString(cursor.getColumnIndex(COLUMN_NICKNAME))
                val password = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD))
                lista.add(Usuario(nickname,password))
            }while(cursor.moveToNext())
        }
        return lista
    }

}
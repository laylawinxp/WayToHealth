package com.example.waytohealth

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DBHelper(val context: Context, val factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, "way_to_health_db", factory, 1){

    override fun onCreate(db: SQLiteDatabase?) {
        val query1 = "CREATE TABLE info (name TEXT, goal INT, age INT)"
        val query = "CREATE TABLE trainings (day INT, month INT, year INT, training INT)"
        db!!.execSQL(query)
        db.execSQL(query1)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS users")
        db.execSQL("DROP TABLE IF EXISTS drinks")
        onCreate(db)
    }

//    fun displayAllData() {
//        val db = this.readableDatabase
//        val query = "SELECT * FROM info"
//
//        val cursor = db.rawQuery(query, null)
//
//        if (cursor.moveToFirst()) {
//            do {
//                val id = cursor.getInt(0)
//                val day = cursor.getString(1)
//                val month = cursor.getInt(2)
//                val w = cursor.getString(3)
//                Log.e("DEBUG","ID: $id, day: $day, month: $month  , w: $w")
//
//            } while (cursor.moveToNext())
//        }
//
//        cursor.close()
//    }

    fun addDataForDay(day: Int, month: Int, year: Int){                     //vika v123
        val values = ContentValues()
        values.put("day", day)
        values.put("month", month)
        values.put("year", year)
        values.put("training", 0)

        val db = this.writableDatabase
        db.insert("trainings", null, values)
        db.close()
    }

    fun checkDataForDay(day: Int, month: Int, year: Int): Boolean{
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM trainings WHERE day = '$day' AND month = '$month' AND year = '$year'", null)
        val result = cursor.moveToFirst()
        cursor.close()
        return result
    }

    fun getCurrentTrainingsOfDay(day: Int, month: Int, year: Int): Int{
        val db = this.readableDatabase
        val query = "SELECT water FROM trainings WHERE day = '$day' AND month = '$month' AND year = '$year'"
        val cursor = db.rawQuery(query, null)
        var count = 0
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0)
        }
        cursor.close()
        return count
    }

    fun changeWaterValue(day: Int, month: Int, year: Int){
        val db = this.writableDatabase
        val currentTrainings = getCurrentTrainingsOfDay(day, month, year)
        val contentValues = ContentValues()
        val updatedTrainings = currentTrainings + 1
        contentValues.put("training", updatedTrainings)
        db.update("drinks", contentValues, "day = '$day' AND month = '$month' AND year = '$year'", null)
    }

}
package com.example.waytohealth

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DBHelper(val context: Context, val factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, "way_to_health_db", factory, 2){

    override fun onCreate(db: SQLiteDatabase?) {
        val query1 = "CREATE TABLE IF NOT EXISTS info (name TEXT, goal INT, age INT)"
        val query = "CREATE TABLE IF NOT EXISTS trainings (day INT, month INT, year INT, training INT)"
        val query2 = "CREATE TABLE IF NOT EXISTS trainings_in_month (month INT, training INT)"
        db!!.execSQL(query)
        db.execSQL(query1)
        db.execSQL(query2)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS users")
        db.execSQL("DROP TABLE IF EXISTS drinks")
        onCreate(db)
    }

    fun getAllDates(): MutableList<Triple<Int, Int, Int>> {
        val db = this.readableDatabase
        val query = "SELECT * FROM trainings"
        val listOfTriples: MutableList<Triple<Int, Int, Int>> = mutableListOf()

        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val day = cursor.getInt(0)
                val month = cursor.getInt(1)
                val year = cursor.getInt(2)

                listOfTriples.add(Triple(day, month, year))

            } while (cursor.moveToNext())
        }

        cursor.close()
        return listOfTriples
    }

    fun displayAllData() {
        val db = this.readableDatabase
        val query = "SELECT * FROM trainings"

        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(0)
                val day = cursor.getString(1)
                val month = cursor.getInt(2)
                val w = cursor.getString(3)
            } while (cursor.moveToNext())
        }
        cursor.close()
    }

    fun displayAllDataForM() {
        val db = this.readableDatabase
        val query = "SELECT * FROM trainings_in_month"

        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getString(0)
                val day = cursor.getString(1)
                Log.e("DEBUG", "$id $day")
            } while (cursor.moveToNext())
        }
        cursor.close()
    }

    fun addDataForMonth(month: Int){
        val values = ContentValues()
        values.put("month", month)
        values.put("training", 0)
        val db = this.writableDatabase
        db.insert("trainings_in_month", null, values)
        db.close()
    }

    fun addDataForDay(day: Int, month: Int, year: Int){
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

    fun checkDataForMonth(month: Int): Boolean{
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM trainings_in_month WHERE month = '$month'", null)
        val result = cursor.moveToFirst()
        cursor.close()
        return result
    }

    fun getCurrentTrainingsOfDay(day: Int, month: Int, year: Int): Int{
        val db = this.readableDatabase
        val query = "SELECT training FROM trainings WHERE day = '$day' AND month = '$month' AND year = '$year'"
        val cursor = db.rawQuery(query, null)
        var count = 0
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0)
        }
        cursor.close()
        return count
    }

    fun getCurrentTrainingsOfMonth(month: Int): Int{
        val db = this.readableDatabase
        val query = "SELECT training FROM trainings_in_month WHERE month = '$month'"
        val cursor = db.rawQuery(query, null)
        var count = 0
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0)
        }
        cursor.close()
        return count
    }

    fun changeTrainingsValue(day: Int, month: Int, year: Int){
        val db = this.writableDatabase
        val currentTrainings = getCurrentTrainingsOfDay(day, month, year)
        val contentValues = ContentValues()
        val updatedTrainings = currentTrainings + 1
        contentValues.put("training", updatedTrainings)
        db.update("trainings", contentValues, "day = '$day' AND month = '$month' AND year = '$year'", null)
    }

    fun changeTrainingsForMonthValue(month: Int){
        val db = this.writableDatabase
        val currentTrainings = getCurrentTrainingsOfMonth(month)
        val contentValues = ContentValues()
        val updatedTrainings = currentTrainings + 1
        contentValues.put("training", updatedTrainings)
        db.update("trainings_in_month", contentValues, "month = '$month'", null)
    }
}
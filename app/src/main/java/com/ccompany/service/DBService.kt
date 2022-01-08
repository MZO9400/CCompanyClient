package com.ccompany.service

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DBService
    (context: Context?) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE " + TABLE_USER + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT,"
                + EMAIL_COL + " TEXT)")

        db.execSQL(query)
    }

    fun saveUserDetail(name: String?, email: String?) {
        val db = this.writableDatabase

        val values = ContentValues()

        values.put(NAME_COL, name)
        values.put(EMAIL_COL, email)

        db.insert(TABLE_USER, null, values)

        db.close()
    }

    fun removeUserDetail() {
        this.onUpgrade(this.writableDatabase, DB_VERSION, DB_VERSION)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USER")
        onCreate(db)
    }

    companion object {
        private const val DB_NAME = "ccompany"

        private const val DB_VERSION = 1

        // Common ID
        private const val ID_COL = "id"

        // Table for Users
        private const val TABLE_USER = "users"

        private const val NAME_COL = "name"

        private const val EMAIL_COL = "email"

        // Table for Companies
        private const val TABLE_COMPANIES = "companies"
    }
}


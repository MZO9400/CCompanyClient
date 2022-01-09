package com.ccompany.service

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.ccompany.interfaces.Company


class DBService
    (context: Context?) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    private val db: SQLiteDatabase = this.writableDatabase

    override fun onCreate(db: SQLiteDatabase) {
        createUserTable()
        createCompanyTable()
    }

    private fun createUserTable() {
        val query = ("CREATE TABLE " + TABLE_USER + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT,"
                + EMAIL_COL + " TEXT)")

        db.execSQL(query)
    }

    private fun createCompanyTable() {
        val query = ("CREATE TABLE " + TABLE_COMPANY + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT,"
                + ADDRESS_COL + " TEXT,"
                + PHONE_COL + " TEXT,"
                + LOGO_COL + " TEXT,"
                + DESCRIPTION_COL + " TEXT,"
                + LATITUDE_COL + " REAL,"
                + LONGITUDE_COL + " REAL)")

        db.execSQL(query)
    }

    fun saveUserDetail(name: String?, email: String?) {
        val values = ContentValues()

        values.put(NAME_COL, name)
        values.put(EMAIL_COL, email)

        db.insert(TABLE_USER, null, values)

        db.close()
    }

    fun removeUserDetail() {
        db.delete(TABLE_USER, null, null)
        db.close()
    }

    fun saveCompanyDetail(companies: List<Company>) {
        for (company in companies) {
            val values = ContentValues()

            values.put(NAME_COL, company.name)
            values.put(ADDRESS_COL, company.address)
            values.put(PHONE_COL, company.phone)
            values.put(LOGO_COL, company.logo)
            values.put(DESCRIPTION_COL, company.description)
            values.put(LATITUDE_COL, company.geolocation.latitude)
            values.put(LONGITUDE_COL, company.geolocation.longitude)

            db.insert(TABLE_COMPANY, null, values)
        }

        db.close()
    }

    fun removeCompanyDetail() {
        db.delete(TABLE_COMPANY, null, null)
        db.close()
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USER")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_COMPANY")
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
        private const val TABLE_COMPANY = "companies"

        private const val ADDRESS_COL = "address"

        private const val PHONE_COL = "phone"

        private const val LOGO_COL = "logo"

        private const val DESCRIPTION_COL = "description"

        private const val LATITUDE_COL = "latitude"

        private const val LONGITUDE_COL = "longitude"

    }
}


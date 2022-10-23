package com.example.database.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * If you want to access database
val dbHelper = DatingDbHelper(context)
 */

private const val SQL_CREATE_USER_TABLE =
    "CREATE TABLE IF NOT EXISTS ${FlowDBContract.UserTable.TABLE_NAME} (" +
            "${FlowDBContract.UserTable.USER_ID} INTEGER PRIMARY KEY AUTOINCREMENT, " + //"${BaseColumns._ID}"
            "${FlowDBContract.UserTable.EMAIL} TEXT, " +
            "${FlowDBContract.UserTable.PASSWORD} TEXT, " +
            "${FlowDBContract.UserTable.FIRST_NAME} TEXT, " +
            "${FlowDBContract.UserTable.LAST_NAME} TEXT" +
            ")"

private const val SQL_CREATE_PERIOD_TABLE =
    "CREATE TABLE IF NOT EXISTS ${FlowDBContract.PeriodTable.TABLE_NAME} (" +
            "${FlowDBContract.PeriodTable.USER_ID} INTEGER , " + //"${BaseColumns._ID}"
            "${FlowDBContract.PeriodTable.START_DATE} LONG, " +
            "${FlowDBContract.PeriodTable.END_DATE} LONG " +
            ")"

private const val DROP_USER_TABLE = "DROP TABLE IF EXISTS ${FlowDBContract.UserTable.TABLE_NAME}"
private const val DROP_PERIOD_TABLE = "DROP TABLE IF EXISTS ${FlowDBContract.PeriodTable.TABLE_NAME}"

class FlowDbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        const val DATABASE_NAME = "flow.db"
        const val DATABASE_VERSION = 4
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_USER_TABLE)
        db?.execSQL(SQL_CREATE_PERIOD_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(DROP_USER_TABLE)
        db?.execSQL(DROP_PERIOD_TABLE)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }
}



















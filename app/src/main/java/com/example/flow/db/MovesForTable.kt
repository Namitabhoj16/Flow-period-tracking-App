package com.example.database.db

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.flow.entities.Period
import com.reza.sqlliteapp.entities.User
import java.sql.Date

class MovesForTable(context: Context) {
    private val dbHelper = FlowDbHelper(context)

    fun insertData(email: String, password: String, firstName: String, lastName: String) {
        //Map of column name + row value
        val values = ContentValues().apply {
            put(FlowDBContract.UserTable.EMAIL, email)
            put(FlowDBContract.UserTable.PASSWORD, password)
            put(FlowDBContract.UserTable.FIRST_NAME, firstName)
            put(FlowDBContract.UserTable.LAST_NAME, lastName)
        }

        val writeToDb = dbHelper.writableDatabase //EXPENSIVE if DB is closed
        //Second argument: What to do when there is no value.
        // Because of null: If there is no value then we just do not insert.
        val newRowId = writeToDb.insert(FlowDBContract.UserTable.TABLE_NAME, null, values)
    }

    fun insertPeriodData(user_id: Int, start_date: Long, end_date: Long) {
        //Map of column name + row value
        val values = ContentValues().apply {
            put(FlowDBContract.PeriodTable.USER_ID, user_id)
            put(FlowDBContract.PeriodTable.START_DATE, start_date)
            put(FlowDBContract.PeriodTable.END_DATE, end_date)
        }

        val writeToDb = dbHelper.writableDatabase //EXPENSIVE if DB is closed
        //Second argument: What to do when there is no value.
        // Because of null: If there is no value then we just do not insert.
        val newRowId = writeToDb.insert(FlowDBContract.PeriodTable.TABLE_NAME, null, values)
    }


    fun getUser(userID: Int): MutableList<User> {
        val userList = mutableListOf<User>()
        for (user in getAll()) {
            if (user.id == userID) {
                userList.add(user)
            }
        }
        return userList
    }

    fun authenticate(email: String, password: String): User? {
        for (user in getAll()) {
            if (user.email == email && user.getPassword() == password) {
                return user
            }
        }
        return null
    }

    fun existingEmail(email: String): Boolean {
        for (user in getAll()) {
            if (user.email == email) {
                return true
            }
        }
        return false
    }

    private fun getAll(): List<User> {
        val readFromDb = dbHelper.readableDatabase //EXPENSIVE if DB is closed.

        //Select Columns you want
        val projection = arrayOf(
            FlowDBContract.UserTable.USER_ID,
            FlowDBContract.UserTable.EMAIL,
            FlowDBContract.UserTable.PASSWORD,
            FlowDBContract.UserTable.FIRST_NAME,
            FlowDBContract.UserTable.LAST_NAME,
        )

        //Sorting
        val orderBy = "${FlowDBContract.UserTable.USER_ID} DESC"

        val cursor = readFromDb.query(
            FlowDBContract.UserTable.TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            orderBy
        )

        val userList = mutableListOf<User>()

        with(cursor) {
            while (moveToNext()) {//Moves from -1 row to next one
                val user = User(
                    getInt(getColumnIndexOrThrow(FlowDBContract.UserTable.USER_ID)),
                    getString(getColumnIndexOrThrow(FlowDBContract.UserTable.EMAIL)),
                    getString(getColumnIndexOrThrow(FlowDBContract.UserTable.PASSWORD)),
                    getString(getColumnIndexOrThrow(FlowDBContract.UserTable.FIRST_NAME)),
                    getString(getColumnIndexOrThrow(FlowDBContract.UserTable.LAST_NAME)),
                )
                userList.add(user)
            }
        }
        cursor.close()
        return userList
    }

    fun getPeriod(userID: Int): MutableList<Period> {
        val dateList = mutableListOf<Period>()
        for (date in getAllDates()) {
            if (date.user_id == userID) {
                dateList.add(date)
            }
        }
        return dateList
    }

    fun getAllDates(): List<Period> {
        val readFromDb = dbHelper.readableDatabase //EXPENSIVE if DB is closed.

        //Select Columns you want
        val projection = arrayOf(
            FlowDBContract.PeriodTable.USER_ID,
            FlowDBContract.PeriodTable.START_DATE,
            FlowDBContract.PeriodTable.END_DATE
        )

        //Sorting
        val orderBy = "${FlowDBContract.PeriodTable.USER_ID} ASC"

        val cursor = readFromDb.query(
            FlowDBContract.PeriodTable.TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            orderBy
        )

        val userList = mutableListOf<Period>()

        with(cursor) {
            while (moveToNext()) {//Moves from -1 row to next one
                val dates = Period(
                    getInt(getColumnIndexOrThrow(FlowDBContract.PeriodTable.USER_ID)),
                    getLong(getColumnIndexOrThrow(FlowDBContract.PeriodTable.START_DATE)),
                    getLong(getColumnIndexOrThrow(FlowDBContract.PeriodTable.END_DATE))
                )
                userList.add(dates)
            }
        }
        cursor.close()
        return userList
    }

    fun getSingleUser(userID: Int): User? {
        val readFromDb = dbHelper.readableDatabase //EXPENSIVE if DB is closed.

        //Select Columns you want
        val projection = arrayOf(
            FlowDBContract.UserTable.USER_ID,
            FlowDBContract.UserTable.EMAIL,
            FlowDBContract.UserTable.PASSWORD,
            FlowDBContract.UserTable.FIRST_NAME,
            FlowDBContract.UserTable.LAST_NAME,
        )

        val cursor = readFromDb.rawQuery(
            "SELECT * from ${FlowDBContract.UserTable.TABLE_NAME} " +
                    "where ${FlowDBContract.UserTable.USER_ID} like '$userID%'", null
        )

        with(cursor) {
            if (moveToNext()) {
                val user = User(
                    getInt(getColumnIndexOrThrow(FlowDBContract.UserTable.USER_ID)),
                    getString(getColumnIndexOrThrow(FlowDBContract.UserTable.EMAIL)),
                    getString(getColumnIndexOrThrow(FlowDBContract.UserTable.PASSWORD)),
                    getString(getColumnIndexOrThrow(FlowDBContract.UserTable.FIRST_NAME)),
                    getString(getColumnIndexOrThrow(FlowDBContract.UserTable.LAST_NAME)),
                )
                return user
            } else return null
        }
    }

    fun update(user: User): Boolean {
        val dbWrite = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put(FlowDBContract.UserTable.EMAIL, user.email)
            put(FlowDBContract.UserTable.FIRST_NAME, user.firstName)
            put(FlowDBContract.UserTable.LAST_NAME, user.lastName)
        }

        val whereClaus = "${FlowDBContract.UserTable.USER_ID} = ?"
        val whereClausArgs = arrayOf(user.id.toString())

        val rowsUpdated = dbWrite.update(
            FlowDBContract.UserTable.TABLE_NAME,
            values,
            whereClaus,
            whereClausArgs
        )

        if (rowsUpdated == 1)
            return true
        return false
    }
}
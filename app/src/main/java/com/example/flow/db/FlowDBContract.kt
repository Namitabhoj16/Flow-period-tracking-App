package com.example.database.db

import android.provider.BaseColumns

object FlowDBContract {
    //Tables
    object UserTable : BaseColumns {
        const val TABLE_NAME = "user"
        const val USER_ID = "user_id"
        const val EMAIL = "email"
        const val PASSWORD = "password"
        const val FIRST_NAME = "first_name"
        const val LAST_NAME = "last_name"
    }

    object PeriodTable : BaseColumns {
        const val TABLE_NAME = "period"
        const val USER_ID = "user_id"
        const val START_DATE = "start_date"
        const val END_DATE = "end_date"
    }
}
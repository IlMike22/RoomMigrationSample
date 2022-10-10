package com.mind.market.roommigrationsample

import android.os.Bundle
import android.service.autofill.UserData
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = Room.databaseBuilder(
            applicationContext,
            UserDatabase::class.java,
            "users.db"
        ).addMigrations(UserDatabase.migration3To4).build()

        lifecycleScope.launch {
            database.dao.getSchools().forEach(::println)
        }

        (1..10).forEach {
            lifecycleScope.launch {
                database.dao.insertSchool(
                    School(
                        name = "test$it"
                    )
                )
            }
        }

    }
}
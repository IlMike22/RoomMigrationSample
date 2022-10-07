package com.mind.market.roommigrationsample

import android.os.Bundle
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
        ).build()

        lifecycleScope.launch {
            database.dao.getUsers().forEach(::println)
        }

       /* (1..10).forEach {
            lifecycleScope.launch {
                database.dao.insertUser(
                    User(
                        email = "test@test$it.com",
                        name = "test$it"
                    )
                )
            }
        }*/

    }
}
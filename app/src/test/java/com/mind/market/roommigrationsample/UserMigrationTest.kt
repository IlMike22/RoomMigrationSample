package com.mind.market.roommigrationsample

import android.system.Os.close
import androidx.room.Room
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

private const val DB_NAME = "test"

@RunWith(AndroidJUnit4::class)
class UserMigrationTest {
    @get:Rule
    val helper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        UserDatabase::class.java,
        listOf(UserDatabase.Migration2To3()),
        FrameworkSQLiteOpenHelperFactory()
    )

    @Test
    fun migration1to2ContainsCorrectData() {
        var db = helper.createDatabase(DB_NAME, 1).apply {
            execSQL("INSERT INTO user VALUES('test@test.com', 'Mike')")
            close()
        }

        db = helper.runMigrationsAndValidate(DB_NAME, 2, true)
        db.query("SELECT * FROM user").apply {
            assertThat(moveToFirst()).isTrue()
            assertThat(getLong(getColumnIndex("created"))).isEqualTo(0)
        }
    }

    @Test
    fun testAllMigrations() {
        helper.createDatabase(DB_NAME, 1).apply {
            close()
        }
        Room.databaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            UserDatabase::class.java,
            DB_NAME
        ).addMigrations(UserDatabase.migration3To4).build().apply {
            openHelper.writableDatabase.close()
        }
    }
}
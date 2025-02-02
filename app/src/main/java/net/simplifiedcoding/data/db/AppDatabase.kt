package net.simplifiedcoding.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import net.simplifiedcoding.data.db.daos.EmployeeDao
import net.simplifiedcoding.data.db.daos.UserDao
import net.simplifiedcoding.data.db.entities.Employee
import net.simplifiedcoding.data.db.entities.User


@Database(
    entities = [Employee::class, User::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getEmployeeDao(): EmployeeDao
    abstract fun getUserDao(): UserDao

    companion object {

        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(
            LOCK
        ) {
            instance
                ?: buildDatabase(context).also {
                    instance = it
                }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "TVSLocal.db"
            ).build()
    }
}
package com.infinty8.cliffexcart.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.infinty8.cliffexcart.model.ProductModelData


@Database(entities = [ProductModelData::class], version = 4, exportSchema = false)
abstract class DbProduct : RoomDatabase() {

    abstract fun productDao(): ProductDao

    companion object {
        @Volatile
        private var instance: DbProduct? = null

        fun getDatabase(context: Context): DbProduct =
                instance ?: synchronized(this) {
                    instance ?: buildDatabase(context).also {
                        instance = it
                    }
                }

        private fun buildDatabase(appContext: Context) =
                Room.databaseBuilder(appContext, DbProduct::class.java, "add_to_cart")
                        .fallbackToDestructiveMigration()
                        .build()
    }

}
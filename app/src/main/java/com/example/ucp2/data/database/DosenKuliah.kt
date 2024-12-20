package com.example.ucp2.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ucp2.data.dao.DosenDao
import com.example.ucp2.data.dao.MataKuliahDao
import com.example.ucp2.data.entity.Dosen
import com.example.ucp2.data.entity.MataKuliah

// Mendefinisikan database dengan tabel Mahasiswa
@Database(entities = [Dosen::class,MataKuliah::class], version = 1, exportSchema = false)
abstract class DosenKuliah : RoomDatabase(){

    abstract fun DosenDao(): DosenDao
    abstract fun MataKuliahDao(): MataKuliahDao

    companion object{
        // Memastikan bahwa nilai variabel Instance selalu sama di semua thread
        @Volatile
        private var Instance: DosenKuliah? = null

        fun getDatabase(context: Context): DosenKuliah{
            return (Instance ?: synchronized(this){
                Room.databaseBuilder(
                    context,
                    DosenKuliah::class.java, // Class database
                    "DosenKuliah" // Nama database
                )
                    .build().also { Instance = it }
            })
        }
    }
}
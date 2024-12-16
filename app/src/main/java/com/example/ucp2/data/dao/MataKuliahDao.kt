package com.example.ucp2.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.ucp2.data.entity.MataKuliah
import kotlinx.coroutines.flow.Flow

@Dao
interface MataKuliahDao {
    @Query("select * from matakuliah")
    fun getAllMataKuliah() : Flow<List<MataKuliah>>
    @Insert
    suspend fun insertMataKuliah(mataKuliah: MataKuliah)
    @Query("select * from matakuliah where kode = :kode")
    fun getMataKuliah(kode: String): Flow<MataKuliah>
    @Delete
    suspend fun deleteMataKuliah(mataKuliah: MataKuliah)
    @Update
    suspend fun updateMataKuliah(mataKuliah: MataKuliah)
}
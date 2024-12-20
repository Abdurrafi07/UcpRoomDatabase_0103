package com.example.ucp2.repository

import com.example.ucp2.data.dao.MataKuliahDao
import com.example.ucp2.data.entity.MataKuliah
import kotlinx.coroutines.flow.Flow

class LocalRepositoryMataKuliah (
    private val matakuliahDao: MataKuliahDao
) : RepositoryMataKuliah {
    override fun getAllMatakuliah(): Flow<List<MataKuliah>> {
        return matakuliahDao.getAllMataKuliah()
    }
    override fun getMatakuliah(kode: String): Flow<MataKuliah> {
        return matakuliahDao.getMataKuliah(kode)
    }
    override suspend fun insertMatakuliah(matakuliah: MataKuliah) {
        matakuliahDao.insertMataKuliah(matakuliah)
    }
    override suspend fun deleteMatakuliah(matakuliah: MataKuliah) {
        matakuliahDao.deleteMataKuliah(matakuliah)
    }
    override suspend fun updateMatakuliah(matakuliah: MataKuliah) {
        matakuliahDao.updateMataKuliah(matakuliah)
    }
}
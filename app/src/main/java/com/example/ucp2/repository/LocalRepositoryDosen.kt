package com.example.ucp2.repository

import com.example.ucp2.data.dao.DosenDao
import com.example.ucp2.data.entity.Dosen
import kotlinx.coroutines.flow.Flow

class LocalRepositoryDosen (
    private val dosenDao: DosenDao
) : RepositoryDosen {
    override fun getAllDosen(): Flow<List<Dosen>> {
        return dosenDao.getAllDosen()
    }
    override suspend fun insertDosen(dosen: Dosen) {
        dosenDao.insertDosen(dosen)
    }
}
package com.example.ucp2.data.dependenciesinjection

import android.content.Context
import com.example.ucp2.data.database.DosenKuliah
import com.example.ucp2.repository.LocalRepositoryDosen
import com.example.ucp2.repository.LocalRepositoryMataKuliah
import com.example.ucp2.repository.RepositoryDosen
import com.example.ucp2.repository.RepositoryMataKuliah

interface InterfaceContainerApps {
    val repositoryDosen: RepositoryDosen
    val repositoryMatakuliah: RepositoryMataKuliah
}

class ContainerApp (private val context: Context): InterfaceContainerApps {
    override val repositoryDosen: RepositoryDosen by lazy {
        LocalRepositoryDosen(DosenKuliah.getDatabase(context).DosenDao())
    }
    override val repositoryMatakuliah: RepositoryMataKuliah by lazy {
        LocalRepositoryMataKuliah(DosenKuliah.getDatabase(context).MataKuliahDao())
    }
}
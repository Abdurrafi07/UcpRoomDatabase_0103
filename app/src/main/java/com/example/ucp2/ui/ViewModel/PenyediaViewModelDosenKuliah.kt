package com.example.ucp2.ui.ViewModel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ucp2.DosenKuliahApp

object PenyediaViewModelDosenKuliah{
    val Factory = viewModelFactory {
        initializer {
            DosenViewModel(
                krsApp().containerApp.repositoryDosen
            )
        }

        initializer {
            HomeDosenViewModel(
                krsApp().containerApp.repositoryDosen
            )
        }

        initializer {
            MataKuliahViewModelIns(
                krsApp().containerApp.repositoryMatakuliah
            )
        }

        initializer {
            HomeMataKuliahViewModel(
                krsApp().containerApp.repositoryMatakuliah
            )
        }

        initializer {
            DetailMataKuliahViewModel(
                createSavedStateHandle(),
                krsApp().containerApp.repositoryMatakuliah
            )
        }

        initializer {
            UpdateMatkulViewModel(
                createSavedStateHandle(),
                krsApp().containerApp.repositoryMatakuliah
            )
        }
    }
}

fun CreationExtras.krsApp(): DosenKuliahApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as DosenKuliahApp)
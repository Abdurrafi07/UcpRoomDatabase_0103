package com.example.ucp2.ui.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.Dosen
import com.example.ucp2.repository.LocalRepositoryDosen
import com.example.ucp2.repository.RepositoryDosen
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class HomeDosenViewModel(
    private val repositoryDosen: RepositoryDosen
): ViewModel() {
    val homeUiState: StateFlow<HomeUiStateDosen> = repositoryDosen.getAllDosen()
        .filterNotNull()
        .map {
            HomeUiStateDosen(
                listDosen = it.toList(),
                isLoading = false,
            )
        }
        .onStart {
            emit(HomeUiStateDosen(isLoading = true))
            delay(900)
        }
        .catch {
            emit(
                HomeUiStateDosen(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message?: "Terjadi Kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUiStateDosen(
                isLoading = true
            )
        )
}

data class HomeUiStateDosen(
    val listDosen: List<Dosen> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String =""
)

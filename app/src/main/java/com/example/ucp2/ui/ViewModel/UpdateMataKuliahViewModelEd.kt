package com.example.ucp2.ui.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.MataKuliah
import com.example.ucp2.repository.RepositoryMataKuliah
import com.example.ucp2.ui.Navigation.DestinasiUpdateMataKuliah
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UpdateMatkulViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryMataKuliah: RepositoryMataKuliah
): ViewModel(){
    var updateUIState by mutableStateOf(MataKuliahUiState())
        private set

    private val _kode: String = checkNotNull(savedStateHandle[DestinasiUpdateMataKuliah.KODE])

    init {
        viewModelScope.launch {
            updateUIState = repositoryMataKuliah.getMatakuliah(_kode)
                .filterNotNull()
                .first()
                .toUIStateMatkul()
        }
    }

    fun updateState(mataKuliahEvent: MataKuliahEvent){
        updateUIState = updateUIState.copy(
            matkulEvent = mataKuliahEvent
        )
    }

    fun validateFields(): Boolean {
        val event = updateUIState.matkulEvent
        val errorState = MatkulErrorState(
            kode = if (event.kode.isNotEmpty()) null else "Kode tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama matkul tidak boleh kosong",
            sks = if (event.sks.isNotEmpty()) null else "SKS tidak boleh kosong",
            semester = if (event.semester.isNotEmpty())null else "Semester tidak boleh kosong",
            jenis = if (event.jenis.isNotEmpty()) null else "Jenis tidak boleh kosong",
            dosenPengampu = if (event.dosenPengampu.isNotEmpty()) null else "Dosen pengampu tidak boleh kosong"
        )
        updateUIState = updateUIState.copy(isEntryValid =  errorState)
        return errorState.isValid()
    }

    fun updateData(){
        val currentEvent = updateUIState.matkulEvent
        if(validateFields()){
            viewModelScope.launch {
                try {
                    repositoryMataKuliah.updateMatakuliah(currentEvent.toMataKuliahEntity())
                    updateUIState = updateUIState.copy(
                        snackbarMessage = "Data berhasil disimpan",
                        matkulEvent = MataKuliahEvent(),
                        isEntryValid = MatkulErrorState()
                    )
                    println("snackBarMessage diatur: ${updateUIState.snackbarMessage}")
                } catch (e: Exception){
                    updateUIState = updateUIState.copy(
                        snackbarMessage = "Data gagal diupdate"
                    )
                }
            }
        }else{
            updateUIState = updateUIState.copy(
                snackbarMessage = "Data gagal diupdate"
            )
        }
    }
    fun resetSnackBarMessage(){
        updateUIState = updateUIState.copy(snackbarMessage = null)
    }
}


fun MataKuliah.toUIStateMatkul(): MataKuliahUiState = MataKuliahUiState(
    matkulEvent = this.toDetailUiEvent()
)
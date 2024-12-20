package com.example.ucp2.ui.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.MataKuliah
import com.example.ucp2.repository.RepositoryMataKuliah
import kotlinx.coroutines.launch

class MataKuliahViewModelIns(private val  repositoryMataKuliah: RepositoryMataKuliah) : ViewModel(){
    var uiState by mutableStateOf(MataKuliahUiState())

    fun updateState(mataKuliahEvent: MataKuliahEvent){
        uiState = uiState.copy(
            matkulEvent = mataKuliahEvent
        )
    }

    private fun validateFields(): Boolean{
        val event = uiState.matkulEvent
        val errorState = MatkulErrorState(
            kode = if (event.kode.isNotEmpty()) null else "Kode tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama matkul tidak boleh kosong",
            sks = if (event.sks.isNotEmpty()) null else "SKS tidak boleh kosong",
            semester = if (event.semester.isNotEmpty()) null else "Semester tidak boleh kosong",
            jenis = if (event.jenis.isNotEmpty()) null else "Jenis tidak boleh kosong",
            dosenPengampu = if (event.dosenPengampu.isNotEmpty()) null else "Dosen pengampu tidak boleh kosong"
        )
        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }
    fun saveData(){
        val currentEvent = uiState.matkulEvent
        if (validateFields()){
            viewModelScope.launch {
                try {
                    repositoryMataKuliah.insertMatakuliah(currentEvent.toMataKuliahEntity())
                    uiState = uiState.copy(
                        snackbarMessage = "Data berhasil disimpan",
                        matkulEvent = MataKuliahEvent(),
                        isEntryValid = MatkulErrorState()
                    )
                }catch (e: Exception){
                    uiState = uiState.copy(snackbarMessage = "Data gagal disimpan")
                }
            }
        }else{
            uiState = uiState.copy(snackbarMessage = "Input tidak valid. Periksa kembali data")
        }
    }
    fun resetSnackBarMessage(){
        uiState = uiState.copy(snackbarMessage = null)
    }
}

data class MataKuliahUiState(
    val matkulEvent: MataKuliahEvent = MataKuliahEvent(),
    val isEntryValid: MatkulErrorState = MatkulErrorState(),
    val snackbarMessage : String? = null
)

data class MatkulErrorState(
    val kode : String? = null,
    val nama: String? = null,
    val sks: String? = null,
    val semester: String? = null,
    val jenis: String? = null,
    val dosenPengampu: String? = null
){
    fun isValid(): Boolean{
        return kode == null && nama == null && sks == null && semester == null && jenis == null && dosenPengampu == null
    }
}

fun MataKuliahEvent.toMataKuliahEntity(): MataKuliah = MataKuliah(
    kode = kode,
    nama = nama,
    sks = sks,
    semester = semester,
    jenis = jenis,
    dosenPengampu = dosenPengampu
)

data class MataKuliahEvent(
    val kode : String = "",
    val nama : String = "",
    val sks : String = "",
    val semester : String = "",
    val jenis : String = "",
    val dosenPengampu : String = ""
)
package com.example.ucp2.ui.Views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.data.entity.Dosen
import com.example.ucp2.ui.Navigation.NavigationAddress
import com.example.ucp2.ui.ViewModel.HomeDosenViewModel
import com.example.ucp2.ui.ViewModel.HomeUiStateDosen
import com.example.ucp2.ui.ViewModel.MataKuliahEvent
import com.example.ucp2.ui.ViewModel.MataKuliahUiState
import com.example.ucp2.ui.ViewModel.MataKuliahViewModelIns
import com.example.ucp2.ui.ViewModel.MatkulErrorState
import com.example.ucp2.ui.ViewModel.PenyediaViewModelDosenKuliah
import com.example.ucp2.ui.customWidget.DynamicSelectedTextField
import com.example.ucp2.ui.customWidget.TopAppBar
import kotlinx.coroutines.launch

object DestinasiMatakuliahInsert : NavigationAddress {
    override val route = "matakuliahinsert"
}

@Composable
fun InsertMataKuliahViews(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MataKuliahViewModelIns = viewModel(factory = PenyediaViewModelDosenKuliah.Factory),
    viewModelDsn: HomeDosenViewModel = viewModel(factory = PenyediaViewModelDosenKuliah.Factory),
) {
    val uiState = viewModel.uiState
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val dsnList by viewModelDsn.homeUiState.collectAsState()

    LaunchedEffect(uiState.snackbarMessage) {
        uiState.snackbarMessage?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message)
                viewModel.resetSnackBarMessage()
            }
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            TopAppBar(
                onBack = onBack,
                showBackButton = true,
                judul = "Tambah Matakuliah"
            )
            InsertBodyMatakuliah(
                uiState = uiState,
                listDosen = dsnList,
                onValueChange = { updatedEvent ->
                    viewModel.updateState(updatedEvent)
                },
                onClick = {
                    coroutineScope.launch {
                        viewModel.saveData()
                    }
                    onNavigate()
                }
            )
        }
    }
}

@Composable
fun InsertBodyMatakuliah(
    modifier: Modifier = Modifier,
    onValueChange: (MataKuliahEvent) -> Unit,
    onClick: () -> Unit,
    uiState: MataKuliahUiState,
    listDosen: HomeUiStateDosen
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FormMatakuliah(
            matakuliahEvent = uiState.matkulEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid,
            modifier = Modifier.fillMaxWidth(),
            listDosen = listDosen.listDosen
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4CAF50), // Hijau Muhammadiyah Green
                contentColor = Color.White // Text color
            )
        ) {
            Text("Simpan")
        }
    }
}

@Composable
fun FormMatakuliah(
    matakuliahEvent: MataKuliahEvent = MataKuliahEvent(),
    onValueChange: (MataKuliahEvent) -> Unit = {},
    errorState: MatkulErrorState = MatkulErrorState(),
    modifier: Modifier = Modifier,
    listDosen: List<Dosen>
) {
    val sks = listOf("1", "2", "3", "4", "5", "6")
    val jenis = listOf("Wajib", "Peminatan")
    val namaDosenList = listDosen.map { it.nama }
    val semesterOptions = listOf("Ganjil", "Genap") // Menambahkan pilihan semester

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = matakuliahEvent.kode,
            onValueChange = {
                onValueChange(matakuliahEvent.copy(kode = it))
            },
            label = { Text("Kode") },
            isError = errorState.kode != null,
            placeholder = { Text("Masukkan Kode Matakuliah") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            leadingIcon = {
                Icon(Icons.Filled.Lock, contentDescription = "Kode Icon", tint = Color.Black)
            },
            shape = RoundedCornerShape(8.dp) // Rounded corners for the TextField
        )
        if (errorState.kode != null) {
            Text(text = errorState.kode!!, color = Color.Red)
        }

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = matakuliahEvent.nama,
            onValueChange = {
                onValueChange(matakuliahEvent.copy(nama = it))
            },
            label = { Text("Nama") },
            isError = errorState.nama != null,
            placeholder = { Text("Masukkan Nama Matakuliah") },
            leadingIcon = {
                Icon(Icons.Filled.Search, contentDescription = "Nama Icon", tint = Color.Black)
            },
            shape = RoundedCornerShape(8.dp) // Rounded corners for the TextField
        )
        if (errorState.nama != null) {
            Text(text = errorState.nama!!, color = Color.Red)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "SKS")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            sks.forEach { sksOption ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    RadioButton(
                        selected = matakuliahEvent.sks == sksOption,
                        onClick = {
                            onValueChange(matakuliahEvent.copy(sks = sksOption))
                        },
                    )
                    Text(text = sksOption)
                }
            }
        }

        // Menghapus OutlinedTextField untuk Semester dan hanya menggunakan RadioButton
        Text(text = "Semester")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            semesterOptions.forEach { semesterOption ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    RadioButton(
                        selected = matakuliahEvent.semester == semesterOption,
                        onClick = {
                            onValueChange(matakuliahEvent.copy(semester = semesterOption))
                        },
                    )
                    Text(text = semesterOption)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Jenis Matakuliah")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            jenis.forEach { jenisOption ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    RadioButton(
                        selected = matakuliahEvent.jenis == jenisOption,
                        onClick = {
                            onValueChange(matakuliahEvent.copy(jenis = jenisOption))
                        },
                    )
                    Text(text = jenisOption)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Dosen Pengampu")
        DynamicSelectedTextField(
            selectedValue = matakuliahEvent.dosenPengampu,
            options = namaDosenList,
            label = "Pilih Dosen Pengampu",
            onValueChangedEvent = {
                onValueChange(matakuliahEvent.copy(dosenPengampu = it))
            },
            shape = RoundedCornerShape(8.dp) // Rounded corners for the TextField
        )
        if (errorState.dosenPengampu != null) {
            Text(text = errorState.dosenPengampu!!, color = Color.Red)
        }
    }
}

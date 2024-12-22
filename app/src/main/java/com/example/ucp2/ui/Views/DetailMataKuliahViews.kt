package com.example.ucp2.ui.Views

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.data.entity.MataKuliah
import com.example.ucp2.ui.ViewModel.DetailMataKuliahViewModel
import com.example.ucp2.ui.ViewModel.DetailUiState
import com.example.ucp2.ui.ViewModel.PenyediaViewModelDosenKuliah
import com.example.ucp2.ui.ViewModel.toMataKuliahEntity
import com.example.ucp2.ui.customWidget.TopAppBar

// Muhammadiyah Green Color
val MuhammadiyahGreen = Color(0xFF4CAF50)

@Composable
fun DetailMatakuliahViews(
    modifier: Modifier = Modifier,
    viewModel: DetailMataKuliahViewModel = viewModel(factory = PenyediaViewModelDosenKuliah.Factory),
    onBack: () -> Unit = {},
    onEditClick: (String) -> Unit = {},
    onDeleteClick: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                judul = "Detail Matakuliah",
                showBackButton = true,
                onBack = onBack,
                backgroundColor = MuhammadiyahGreen, // Muhammadiyah Green color
                contentColor = Color.White,
                modifier = modifier
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onEditClick(viewModel.detailUiState.value.detailUiEvent.kode)
                },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp),
                containerColor = MuhammadiyahGreen // Floating action button with Muhammadiyah Green
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Matakuliah",
                    tint = Color.White // Ensures the icon has good contrast
                )
            }
        }
    ) { innerPadding ->
        val detailUiState by viewModel.detailUiState.collectAsState()

        BodyDetailMatakuliah(
            modifier = Modifier.padding(innerPadding),
            detailUiState = detailUiState,
            onDeleteClick = {
                viewModel.deleteMK()
                onDeleteClick()
            }
        )
    }
}

@Composable
fun BodyDetailMatakuliah(
    modifier: Modifier = Modifier,
    detailUiState: DetailUiState = DetailUiState(),
    onDeleteClick: () -> Unit = {}
) {
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }

    when {
        detailUiState.isLoading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = MuhammadiyahGreen) // Circular progress with Muhammadiyah Green
            }
        }

        detailUiState.isUiEventNotEmpty -> {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ItemDetailMatakuliah(
                    matakuliah = detailUiState.detailUiEvent.toMataKuliahEntity(),
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Button(
                    onClick = { deleteConfirmationRequired = true },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = MuhammadiyahGreen)
                ) {
                    Text(text = "Delete", color = Color.White)
                }

                if (deleteConfirmationRequired) {
                    DeleteConfirmationDialog(
                        onDeleteConfirm = {
                            deleteConfirmationRequired = false
                            onDeleteClick()
                        },
                        onDeleteCancel = { deleteConfirmationRequired = false },
                        modifier = Modifier.padding(8.dp),
                    )
                }
            }
        }

        detailUiState.isUiEventEmpty -> {
            Box(
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Data tidak ditemukan",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun ItemDetailMatakuliah(
    modifier: Modifier = Modifier,
    matakuliah: MataKuliah
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .shadow(8.dp, shape = MaterialTheme.shapes.medium), // Adding shadow for elevation
        shape = MaterialTheme.shapes.medium, // Rounded corners
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFE8F5E9) // Light green background for the card
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            ComponentDetailMatakuliah(judul = "Kode Matakuliah", isinya = matakuliah.kode)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailMatakuliah(judul = "Nama Matakuliah", isinya = matakuliah.nama)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailMatakuliah(judul = "SKS", isinya = matakuliah.sks)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailMatakuliah(judul = "Semester", isinya = matakuliah.semester)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailMatakuliah(judul = "Jenis Matakuliah", isinya = matakuliah.jenis)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailMatakuliah(judul = "Dosen Pengampu", isinya = matakuliah.dosenPengampu)
        }
    }
}

@Composable
fun ComponentDetailMatakuliah(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$judul : ",
            fontSize = 18.sp, // Smaller font size for headers
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Text(
            text = isinya,
            fontSize = 18.sp, // Smaller font size for content
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = { /* Do nothing */ },
        title = { Text("Delete Data") },
        text = { Text("Apakah anda yakin ingin menghapus data?") },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            TextButton(
                onClick = onDeleteConfirm,
                colors = ButtonDefaults.textButtonColors(
                    containerColor = MuhammadiyahGreen, // Mengatur warna kotak tombol menjadi Muhammadiyah Green
                    contentColor = Color.White // Mengatur warna teks tombol menjadi putih untuk kontras
                    )
                ) {
                Text(text = "Yes")
            }
        }
    )
}

package com.example.ucp2.ui.Views


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.data.entity.MataKuliah
import com.example.ucp2.ui.ViewModel.HomeMataKuliahViewModel
import com.example.ucp2.ui.ViewModel.HomeUiStateDosen
import com.example.ucp2.ui.ViewModel.HomeUiStateMataKuliah
import com.example.ucp2.ui.ViewModel.PenyediaViewModelDosenKuliah
import com.example.ucp2.ui.customWidget.TopAppBar
import kotlinx.coroutines.launch

@Composable
fun HomeMatakuliahViews(
    viewModel: HomeMataKuliahViewModel = viewModel(factory = PenyediaViewModelDosenKuliah.Factory),
    onAddMatakuliah: () -> Unit = {},
    onBack:()->Unit,
    onDetailClick: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                judul = "Daftar Matakuliah",
                showBackButton = true,
                onBack = onBack,
                modifier = modifier
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddMatakuliah,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Tambah Matakuliah"
                )
            }
        }
    ) { innerPadding ->
        val homeUIStateMataKuliah by viewModel.HomeUiStateMataKuliah.collectAsState()

        BodyHomeMatakuliahView(
            homeUIStateMataKuliah = homeUIStateMataKuliah,
            onClick = {
                onDetailClick(it)
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun BodyHomeMatakuliahView(
    homeUIStateMataKuliah: HomeUiStateMataKuliah,
    onClick: (String) -> Unit = { },
    modifier: Modifier = Modifier
) {

    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    when {
        homeUIStateMataKuliah.isLoading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        homeUIStateMataKuliah.isError -> {
            LaunchedEffect(homeUIStateMataKuliah.errorMessage) {
                homeUIStateMataKuliah.errorMessage?.let { message ->
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(message)
                    }
                }
            }
        }

        homeUIStateMataKuliah.listKuliah.isEmpty() -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Tidak ada data matakuliah.",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        else -> {
            ListMatakuliah(
                listKuliah = homeUIStateMataKuliah.listKuliah,
                onClick = {
                    onClick(it)
                    println(it)
                }, modifier = modifier
            )
        }
    }
}

@Composable
fun ListMatakuliah(
    listKuliah: List<MataKuliah>,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit = { }
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(
            items = listKuliah,
            itemContent = { kuliah ->
                CardMatakuliah(
                    kuliah = kuliah,
                    onClick = { onClick(kuliah.kode) }
                )
            }
        )
    }
}

@Composable
fun CardMatakuliah(
    kuliah: MataKuliah,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { }
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .shadow(8.dp, shape = MaterialTheme.shapes.medium), // Adding shadow for elevation
        shape = MaterialTheme.shapes.medium, // Rounded corners
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFE8F5E9) // Light green background for the card
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp) // Increased padding for better spacing
        ) {
            // Row for MataKuliah code
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Lock,
                    contentDescription = "",
                    modifier = Modifier.size(28.dp), // Icon size
                    tint = Color.Black // Set icon color to black
                )
                Spacer(modifier = Modifier.width(8.dp)) // Space between icon and text
                Text(
                    text = kuliah.kode,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.Black // Set text color to black
                )
            }

            Spacer(modifier = Modifier.height(8.dp)) // Spacer between rows

            // Row for MataKuliah name
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "",
                    modifier = Modifier.size(28.dp), // Icon size
                    tint = Color.Black // Set icon color to black
                )
                Spacer(modifier = Modifier.width(8.dp)) // Space between icon and text
                Text(
                    text = kuliah.nama,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black // Set text color to black
                )
            }

            Spacer(modifier = Modifier.height(8.dp)) // Spacer between rows

            // Row for Dosen Pengampu
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "",
                    modifier = Modifier.size(28.dp), // Icon size
                    tint = Color.Black // Set icon color to black
                )
                Spacer(modifier = Modifier.width(8.dp)) // Space between icon and text
                Text(
                    text = kuliah.dosenPengampu,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black // Set text color to black
                )
            }
        }
    }
}

package com.example.ucp2.ui.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ucp2.ui.Views.* // Mengimpor semua file view
import com.example.ucp2.ui.views.SplashHomeViews

@Composable
fun ControllerPage(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiSplashHome.route // Halaman awal adalah SplashHomeView
    ) {
        // Splash Home Screen
        composable(route = DestinasiSplashHome.route) {
            SplashHomeViews(
                onDosenClick = {
                    navController.navigate(DestinasiHomeDosen.route)
                },
                onMataKuliahClick = {
                    navController.navigate(DestinasiHomeMataKuliah.route)
                }
            )
        }

        // Home Dosen Screen
        composable(route = DestinasiHomeDosen.route) {
            HomeDosenViews(
                onBack = { navController.popBackStack() },
                onAddDosen = { navController.navigate(DestinasiDosenInsert.route) },
                modifier = modifier
            )
        }

        // Insert Dosen Screen
        composable(route = DestinasiDosenInsert.route) {
            InsertDosenView(
                onBack = { navController.popBackStack() },
                onNavigate = { navController.popBackStack() },
                modifier = modifier
            )
        }

        // Home Matakuliah Screen
        composable(route = DestinasiHomeMataKuliah.route) {
            HomeMatakuliahViews(
                onDetailClick = { kode ->
                    navController.navigate("${DestinasiDetailMataKuliah.route}/$kode")
                },
                onBack = { navController.popBackStack() },
                onAddMatakuliah = { navController.navigate(DestinasiInsertMataKuliah.route) },
                modifier = modifier
            )
        }

        // Insert Matakuliah Screen
        composable(route = DestinasiInsertMataKuliah.route) {
            InsertMataKuliahViews(
                onBack = { navController.popBackStack() },
                onNavigate = { navController.popBackStack() },
                modifier = modifier
            )
        }

        // Detail Matakuliah Screen
        composable(
            DestinasiDetailMataKuliah.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailMataKuliah.KODE) { type = NavType.StringType }
            )
        ) {
            val kode = it.arguments?.getString(DestinasiDetailMataKuliah.KODE)
            kode?.let { id ->
                DetailMatakuliahViews(
                    onBack = { navController.popBackStack() },
                    onEditClick = { kode ->
                        navController.navigate("${DestinasiUpdateMataKuliah.route}/$kode")
                    },
                    onDeleteClick = { navController.popBackStack() },
                    modifier = modifier
                )
            }
        }

        // Update Matakuliah Screen
        composable(
            DestinasiUpdateMataKuliah.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdateMataKuliah.KODE) { type = NavType.StringType }
            )
        ) {
            UpdateMataKuliahViews(
                onBack = { navController.popBackStack() },
                onNavigate = { navController.popBackStack() },
                modifier = modifier
            )
        }
    }
}

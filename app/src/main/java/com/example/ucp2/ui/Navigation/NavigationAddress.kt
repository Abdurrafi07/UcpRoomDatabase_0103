package com.example.ucp2.ui.Navigation

interface NavigationAddress {
    val route: String
}

object DestinasiSplashHome : NavigationAddress {
    override val route = "SplashHome"
}

object DestinasiHomeDosen : NavigationAddress {
    override val route: String = "HomeDosen"
}

object DestinasiDosenInsert : NavigationAddress {
    override val route = "InsertDosen"
}

object DestinasiHomeMataKuliah : NavigationAddress{
    override val route: String = "HomeMataKuliah"
}

object DestinasiInsertMataKuliah : NavigationAddress {
    override val route = "InsertMataKuliah"
}

object DestinasiDetailMataKuliah : NavigationAddress{
    override val route: String = "DetailMataKuliah"
    const val KODE = "kode"
    val routesWithArg = "$route/{$KODE}"
}

object DestinasiUpdateMataKuliah: NavigationAddress {
    override val route: String = "UpdateMataKuliah"
    const val KODE = "kode"
    val routesWithArg = "$route/{$KODE}"
}
package com.example.ucp2

import android.app.Application
import com.example.ucp2.data.dependenciesinjection.ContainerApp

class DosenKuliahApp : Application() {
    // Fungsinya untuk menyimpan instance ContainerApp
    lateinit var containerApp: ContainerApp

    override fun onCreate() {
        super.onCreate()
        // Membuat instance ContinerApp
        containerApp = ContainerApp(this)
        // instance adalah object yang dibuat dari class
    }
}
package com.co.labscm20251_gr03.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ContactDataViewModel : ViewModel() {
    var telefono by mutableStateOf("")
        private set
    var correo by mutableStateOf("")
        private set
    var pais by mutableStateOf("")
        private set
    var ciudad by mutableStateOf("")
        private set
    var direccion by mutableStateOf("")
        private set

    fun onTelefonoChange(new: String) { telefono = new }
    fun onCorreoChange(new: String) { correo = new }
    fun onPaisChange(new: String) { pais = new }
    fun onCiudadChange(new: String) { ciudad = new }
    fun onDireccionChange(new: String) { direccion = new }
}
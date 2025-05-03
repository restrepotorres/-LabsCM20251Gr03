package com.co.labscm20251_gr03.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class PersonalDataViewModel : ViewModel() {
    var nombres by mutableStateOf("")
        private set
    var apellidos by mutableStateOf("")
        private set
    var fechaNacimiento by mutableStateOf<Long?>(null)
        private set
    var escolaridad by mutableStateOf("")
        private set
    var sexoElegido by mutableIntStateOf(3)
        private set

    fun onNombresChange(new: String) { nombres = new }
    fun onApellidosChange(new: String) { apellidos = new }
    fun onFechaChange(new: Long) { fechaNacimiento = new }
    fun onEscolaridadChange(new: String) { escolaridad = new }
    fun onSexoSelected(index: Int) { sexoElegido = index }
}
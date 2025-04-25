package com.co.labscm20251_gr03.ui.element

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import com.co.labscm20251_gr03.util.obtenerCiudades
import com.co.labscm20251_gr03.util.obtenerPaises

@Composable
fun CampoPais(
    pais: String,
    onPaisChange: (String) -> Unit,
) {
    val context = LocalContext.current

    var sugerenciasPais by remember { mutableStateOf(listOf<String>()) }

    OutlinedTextField(
        value = pais,
        onValueChange = {
            onPaisChange(it)
            obtenerPaises(context, it) { suggestions -> sugerenciasPais = suggestions }
        },
        label = { Text("País*") },
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Words,
            autoCorrect = false,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        )
    )

    sugerenciasPais.forEach {
        TextButton(onClick = { onPaisChange(it); sugerenciasPais = emptyList() }) {
            Text(it)
        }
    }
}

@Composable
fun CampoCiudad(
    ciudad: String,
    onCiudadChange: (String) -> Unit,
    pais: String,
) {
    val context = LocalContext.current

    var sugerenciasCiudad by remember { mutableStateOf(listOf<String>()) }

    OutlinedTextField(
        value = ciudad,
        onValueChange = {
            onCiudadChange(it)
            obtenerCiudades(context, pais, it) { suggestions -> sugerenciasCiudad = suggestions }
        },
        label = { Text("Ciudad") },
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Words,
            autoCorrect = false,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        )
    )

    sugerenciasCiudad.forEach {
        TextButton(onClick = { onCiudadChange(it); sugerenciasCiudad = emptyList() }) {
            Text(it)
        }
    }
}
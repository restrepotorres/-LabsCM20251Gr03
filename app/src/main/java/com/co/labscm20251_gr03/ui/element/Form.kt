package com.co.labscm20251_gr03.ui.element

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.co.labscm20251_gr03.util.obtenerCiudades
import com.co.labscm20251_gr03.util.obtenerPaises

@Composable
fun Encabezado(title: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(top = 48.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterStart).padding(vertical = 16.dp, horizontal = 24.dp)
        )
    }
}

@Composable
fun CampoPais(
    pais: String,
    onPaisChange: (String) -> Unit,
) {
    val context = LocalContext.current

    var sugerenciasPais by remember { mutableStateOf(listOf<String>()) }

    Column {
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
}

@Composable
fun CampoCiudad(
    ciudad: String,
    onCiudadChange: (String) -> Unit,
    pais: String,
) {
    val context = LocalContext.current

    var sugerenciasCiudad by remember { mutableStateOf(listOf<String>()) }

    Column{
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
}
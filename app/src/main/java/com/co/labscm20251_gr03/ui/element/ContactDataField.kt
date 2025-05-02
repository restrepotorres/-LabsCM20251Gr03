package com.co.labscm20251_gr03.ui.element

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material.icons.rounded.Place
import androidx.compose.material3.Icon
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.co.labscm20251_gr03.util.obtenerCiudades
import com.co.labscm20251_gr03.util.obtenerPaises

@Composable
fun CampoTelefono (
    telefono: String,
    onTelefonoChange: (String) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            Icons.Rounded.Phone,
            contentDescription = "Phone Icon",
            modifier = Modifier.padding(end = 12.dp).size(32.dp)
        )

        OutlinedTextField(
            value = telefono,
            onValueChange = onTelefonoChange,
            label = { Text("Teléfono*") },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = false,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )
    }
}

@Composable
fun CampoCorreo (
    correo: String,
    onCorreoChange: (String) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            Icons.Rounded.Email,
            contentDescription = "Email Icon",
            modifier = Modifier
                .padding(end = 12.dp)
                .size(32.dp)
        )

        OutlinedTextField(
            value = correo,
            onValueChange = onCorreoChange,
            label = { Text("Correo*") },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = false,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )
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

    Row {
        Box(modifier = Modifier.height(64.dp), contentAlignment = Alignment.CenterStart) {
            Icon(
                Icons.Rounded.Place,
                contentDescription = "Place Icon",
                modifier = Modifier
                    .padding(end = 12.dp)
                    .size(32.dp)
            )
        }
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
}

@Composable
fun CampoCiudad(
    ciudad: String,
    onCiudadChange: (String) -> Unit,
    pais: String,
) {
    val context = LocalContext.current

    var sugerenciasCiudad by remember { mutableStateOf(listOf<String>()) }

    Row {
        Box(modifier = Modifier.height(64.dp), contentAlignment = Alignment.CenterStart) {
            Icon(
                Icons.Rounded.Place,
                contentDescription = "Place Icon",
                modifier = Modifier
                    .padding(end = 12.dp)
                    .size(32.dp)
            )
        }
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
}

@Composable
fun CampoDireccion (
    direccion: String,
    onDireccionChange: (String) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            Icons.Rounded.Home,
            contentDescription = "Home Icon",
            modifier = Modifier
                .padding(end = 12.dp)
                .size(32.dp)
        )

        OutlinedTextField(
            value = direccion,
            onValueChange = onDireccionChange,
            label = { Text("Dirección") },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                autoCorrect = false,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            )
        )
    }
}
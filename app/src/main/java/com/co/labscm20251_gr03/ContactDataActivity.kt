package com.co.labscm20251_gr03

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.co.labscm20251_gr03.ui.theme.LabsCM20251Gr03Theme
import java.util.Calendar

class ContactDataActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Data()

        }
    }
}


@Composable
fun Data() {
    var telefono by rememberSaveable { mutableStateOf("") }
    var correo by rememberSaveable { mutableStateOf("") }
    var pais by rememberSaveable { mutableStateOf("") }
    var ciudad by rememberSaveable { mutableStateOf("") }
    var direccion by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = "Información de Contacto")

        OutlinedTextField(
            value = telefono,
            onValueChange = { telefono = it },
            label = { Text("Teléfono*") },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = false,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )

        OutlinedTextField(
            value = correo,
            onValueChange = { correo = it },
            label = { Text("Correo*") },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = false,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )
        )

        OutlinedTextField(
            value = pais,
            onValueChange = { pais = it },
            label = { Text("País*") },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                autoCorrect = false,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
        )

        OutlinedTextField(
            value = ciudad,
            onValueChange = { ciudad = it },
            label = { Text("Ciudad") },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                autoCorrect = false,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
        )

        OutlinedTextField(
            value = direccion,
            onValueChange = { direccion = it },
            label = { Text("Dirección") },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                autoCorrect = false,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            )
        )

        Button(onClick = {
            //Lo siguiente es para imprimir por consola
            val direccionLinea = if (direccion.isNotBlank()) "Dirección: $direccion                (es campo opcional)\n\n" else ""
            val ciudadLinea = if (ciudad.isNotBlank()) "Ciudad: $ciudad                                      (es campo opcional)\n" else ""
            val mensaje = """
                    Información de contacto: 
                    Teléfono: $telefono 
                    $direccionLinea
                    Email: $correo 
                    País: $pais 
                    $ciudadLinea
                """.trimIndent()
            Log.d("Formulario", mensaje)
            if (telefono.isBlank() || correo.isBlank() || pais.isBlank()) {
                Toast.makeText(context, "Por favor completa los campos obligatorios", Toast.LENGTH_SHORT).show()
            } else {

            }
        }) {
            Text("Siguiente")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun dataPreview() {
    LabsCM20251Gr03Theme {
        Data()
    }
}
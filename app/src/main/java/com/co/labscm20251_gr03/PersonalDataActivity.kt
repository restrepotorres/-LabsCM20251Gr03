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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.co.labscm20251_gr03.ui.theme.LabsCM20251Gr03Theme
import java.util.Calendar

class PersonalDataActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Formulario()

        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun Formulario() {
    var nombre by rememberSaveable { mutableStateOf("") }
    var apellidos by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current
    var fechaNacimiento by rememberSaveable { mutableStateOf("Seleccionar fecha") }
    var escolaridad by rememberSaveable { mutableStateOf("") }
    val opcionesEscolaridad = listOf("Primaria", "Secundaria", "Universitaria", "Otro")
    val opcionesDeSexo = listOf("Hombre", "Mujer", "Otro", "Prefiero no decirlo")
    val (sexoElegido, onOptionSelected) = rememberSaveable { mutableStateOf(opcionesDeSexo[3]) }

    var expanded by rememberSaveable { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current
    val apellidoFocusRequester = remember { FocusRequester() }

    val datePickerDialog = DatePickerDialog(
        LocalContext.current,
        { _, year, month, dayOfMonth ->
            fechaNacimiento = "$dayOfMonth/${month + 1}/$year"
        },
        Calendar.getInstance().get(Calendar.YEAR),
        Calendar.getInstance().get(Calendar.MONTH),
        Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
    )

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = "Información Personal")

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombres*") },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Characters,
                autoCorrect = false,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { apellidoFocusRequester.requestFocus() }
            )
        )

        OutlinedTextField(
            value = apellidos,
            onValueChange = { apellidos = it },
            label = { Text("Apellidos*") },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Characters,
                autoCorrect = false,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            ),
            modifier = Modifier.focusRequester(apellidoFocusRequester)
        )

        Text(text = "Sexo*")
        Column(modifier = Modifier.selectableGroup()) {
            opcionesDeSexo.forEach { opcion ->
                Row(
                    Modifier.selectable(
                        selected = (opcion == sexoElegido),
                        onClick = { onOptionSelected(opcion) },
                        role = Role.RadioButton
                    )
                ) {
                    RadioButton(
                        selected = (opcion == sexoElegido),
                        onClick = null
                    )
                    Text(
                        text = opcion,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Fecha de Nacimiento*")
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { datePickerDialog.show() }) {
                Text(fechaNacimiento)
            }
        }

        Box {
            Button(onClick = { expanded = true }) {
                Text(
                    if (escolaridad.isEmpty()) "Escolaridad" else escolaridad,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                opcionesEscolaridad.forEach { opcion ->
                    DropdownMenuItem(
                        text = { Text(opcion) },
                        onClick = {
                            escolaridad = opcion
                            expanded = false
                        }
                    )
                }
            }
        }

        Button(onClick = {
            //Lo siguiente es para imprimir por consola
            val nombreCompleto = "$nombre $apellidos"
            val sexoLinea = if (sexoElegido != "Prefiero no decirlo") "\n\n$sexoElegido" else ""
            val escolaridadLinea = if (escolaridad.isNotBlank()) "\n\n$escolaridad" else ""
            val mensaje = """
            Información personal: 
            $nombreCompleto$sexoLinea
            Nació el $fechaNacimiento$escolaridadLinea """.trimIndent()
            Log.d("Formulario", mensaje)

            if (nombre.isBlank() || apellidos.isBlank() || fechaNacimiento == "Seleccionar fecha") {
                Toast.makeText(context, "Por favor completa todos los campos obligatorios", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(context, ContactDataActivity::class.java)
                context.startActivity(intent)
            }
        }) {
            Text("Siguiente")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LabsCM20251Gr03Theme {
        Formulario()
    }
}
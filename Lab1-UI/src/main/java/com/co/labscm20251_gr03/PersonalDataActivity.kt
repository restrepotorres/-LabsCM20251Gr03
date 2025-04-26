package com.co.labscm20251_gr03

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.co.labscm20251_gr03.ui.theme.LabsCM20251Gr03Theme
import java.util.Calendar
import java.util.Locale

class PersonalDataActivity : ComponentActivity() {
    override fun attachBaseContext(newBase: Context) {
        val lang = getSavedLanguage(newBase) ?: "en" // fallback to English
        val updatedContext = updateLocale(newBase, lang)
        super.attachBaseContext(updatedContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Formulario()

        }
    }
}

fun updateLocale(context: Context, language: String): Context {
    saveLanguage(context, language)

    val locale = Locale(language)
    Locale.setDefault(locale)

    val config = context.resources.configuration
    config.setLocale(locale)
    config.setLayoutDirection(locale)

    return context.createConfigurationContext(config)
}


fun saveLanguage(context: Context, lang: String) {
    context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        .edit()
        .putString("lang", lang)
        .apply()
}

fun getSavedLanguage(context: Context): String? {
    return context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        .getString("lang", null)
}

fun loadLanguage(context: Context): String {
    return context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        .getString("lang", "en") ?: "en"
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
    val activity = context as? Activity
    val defaultFecha = stringResource(R.string.select_date)
    val requiredFielAlert = stringResource(R.string.alert_mandatory_fields)
    var fechaNacimiento by rememberSaveable { mutableStateOf(defaultFecha) }
    var escolaridad by rememberSaveable { mutableStateOf("") }
    val opcionesEscolaridad = listOf(stringResource(R.string.school_primary),
        stringResource(R.string.school_secondary),
        stringResource(R.string.school_university),
        stringResource(R.string.school_other))
    val opcionesDeSexo = listOf(stringResource(R.string.gender_male),
        stringResource(R.string.gender_female),
        stringResource(R.string.gender_other),
        stringResource(R.string.gender_rathernot))
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
        Text(stringResource(R.string.personal_info))

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text(stringResource(R.string.names)) },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
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
            label = { Text(stringResource(R.string.second_names)) },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                autoCorrect = false,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            ),
            modifier = Modifier.focusRequester(apellidoFocusRequester)
        )

        Text(stringResource(R.string.sex))
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
            Text(stringResource(R.string.birth_date))
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { datePickerDialog.show() }) {
                Text(fechaNacimiento)
            }
        }

        Box {
            Button(onClick = { expanded = true }) {
                Text(
                    if (escolaridad.isEmpty()) stringResource(R.string.schooling_level) else escolaridad,
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
                Toast.makeText(context, requiredFielAlert, Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(context, ContactDataActivity::class.java)
                context.startActivity(intent)
            }
        }) {
            Text(stringResource(R.string.next_label))
        }

        Button(onClick = {
            val newLang = if (Locale.getDefault().language == "en") "es" else "en"
            saveLanguage(context, newLang)

            activity?.let {
                val intent = it.intent
                it.finish()
                it.startActivity(intent)
            }
        }) {
            Text(text = stringResource(id = R.string.switch_language))
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
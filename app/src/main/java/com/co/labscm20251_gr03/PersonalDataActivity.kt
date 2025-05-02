package com.co.labscm20251_gr03

import android.app.DatePickerDialog
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.co.labscm20251_gr03.ui.element.CampoApellidos
import com.co.labscm20251_gr03.ui.element.CampoEscolaridad
import com.co.labscm20251_gr03.ui.element.CampoFechaNacimiento
import com.co.labscm20251_gr03.ui.element.CampoNombres
import com.co.labscm20251_gr03.ui.element.CampoSexo
import com.co.labscm20251_gr03.ui.element.Encabezado
import com.co.labscm20251_gr03.ui.theme.LabsCM20251Gr03Theme
import java.util.Calendar

class PersonalDataActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FormularioDatosPersonales()
        }
    }
}

@Composable
fun FormularioDatosPersonales() {
    var nombres by rememberSaveable { mutableStateOf("") }
    var apellidos by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current
    var fechaNacimiento by rememberSaveable { mutableStateOf("Seleccionar") }
    var escolaridad by rememberSaveable { mutableStateOf("") }
    val opcionesEscolaridad = listOf("Primaria", "Secundaria", "Universitaria", "Otro")
    val opcionesDeSexo = listOf("Hombre", "Mujer", "Otro", "Prefiero no decirlo")
    val (sexoElegido, onOptionSelected) = rememberSaveable { mutableStateOf(opcionesDeSexo[3]) }

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

    val configuracion = LocalConfiguration.current
    val esHorizontal = configuracion.orientation == Configuration.ORIENTATION_LANDSCAPE
    val scrollState = rememberScrollState()

    Column {
        Encabezado("Información personal")

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            if (esHorizontal) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CampoNombres(
                        nombres = nombres,
                        onNombresChange = { nombres = it },
                        apellidoFocusRequester = apellidoFocusRequester
                    )

                    CampoApellidos(
                        apellidos = apellidos,
                        onApellidosChange = { apellidos = it},
                        focusManager = focusManager,
                        apellidoFocusRequester = apellidoFocusRequester
                    )
                }

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    CampoSexo(
                        opcionesDeSexo = opcionesDeSexo,
                        sexoElegido = sexoElegido,
                        onOptionSelected = onOptionSelected
                    )

                    CampoFechaNacimiento(
                        fechaNacimiento = fechaNacimiento,
                        datePickerDialog = datePickerDialog
                    )
                }

                CampoEscolaridad(
                    escolaridad = escolaridad,
                    onEscolaridadChange = { escolaridad = it },
                    opcionesEscolaridad = opcionesEscolaridad,
                )
            } else {
                CampoNombres(
                    nombres = nombres,
                    onNombresChange = { nombres = it },
                    apellidoFocusRequester = apellidoFocusRequester
                )

                CampoApellidos(
                    apellidos = apellidos,
                    onApellidosChange = { apellidos = it},
                    focusManager = focusManager,
                    apellidoFocusRequester = apellidoFocusRequester
                )

                CampoSexo (
                    opcionesDeSexo = opcionesDeSexo,
                    sexoElegido = sexoElegido,
                    onOptionSelected = onOptionSelected
                )

                CampoFechaNacimiento(
                    fechaNacimiento = fechaNacimiento,
                    datePickerDialog = datePickerDialog
                )

                CampoEscolaridad(
                    escolaridad = escolaridad,
                    onEscolaridadChange = { escolaridad = it },
                    opcionesEscolaridad = opcionesEscolaridad,
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Button(onClick = {
                    //Lo siguiente es para imprimir por consola
                    val nombreCompleto = "$nombres $apellidos"
                    val sexoLinea =
                        if (sexoElegido != "Prefiero no decirlo") "\n\n$sexoElegido" else ""
                    val escolaridadLinea = if (escolaridad.isNotBlank()) "\n\n$escolaridad" else ""
                    val mensaje = """
                    Información personal: 
                    $nombreCompleto$sexoLinea
                    Nació el $fechaNacimiento$escolaridadLinea """.trimIndent()
                    Log.d("Formulario", mensaje)

                    if (nombres.isBlank() || apellidos.isBlank() || fechaNacimiento == "Seleccionar fecha") {
                        Toast.makeText(
                            context,
                            "Por favor completa todos los campos obligatorios",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        val intent = Intent(context, ContactDataActivity::class.java)
                        context.startActivity(intent)
                    }
                }) {
                    Text("Siguiente")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FormularioDatosPersonalesPreview() {
    LabsCM20251Gr03Theme {
        FormularioDatosPersonales()
    }
}
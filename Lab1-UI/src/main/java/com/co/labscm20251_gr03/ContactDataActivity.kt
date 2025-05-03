package com.co.labscm20251_gr03

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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.co.labscm20251_gr03.ui.element.CampoCiudad
import com.co.labscm20251_gr03.ui.element.CampoCorreo
import com.co.labscm20251_gr03.ui.element.CampoDireccion
import com.co.labscm20251_gr03.ui.element.CampoPais
import com.co.labscm20251_gr03.ui.element.CampoTelefono
import com.co.labscm20251_gr03.ui.element.Encabezado
import com.co.labscm20251_gr03.ui.theme.LabsCM20251Gr03Theme

class ContactDataActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FormularioDatosContacto()
        }
    }
}

@Composable
fun FormularioDatosContacto() {
    var telefono by rememberSaveable { mutableStateOf("") }
    var correo by rememberSaveable { mutableStateOf("") }
    var pais by rememberSaveable { mutableStateOf("") }
    var ciudad by rememberSaveable { mutableStateOf("") }
    var direccion by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current
    val requiredFieldAlert = stringResource(R.string.alert_mandatory_fields)

    val configuracion = LocalConfiguration.current
    val esHorizontal = configuracion.orientation == Configuration.ORIENTATION_LANDSCAPE
    val scrollState = rememberScrollState()

    Column {
        Encabezado(stringResource(R.string.contact_info))

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            if (esHorizontal) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        CampoTelefono(
                            telefono = telefono,
                            onTelefonoChange = { telefono = it }
                        )

                        CampoCorreo(
                            correo = correo,
                            onCorreoChange = { correo = it }
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        CampoPais(
                            pais = pais,
                            onPaisChange = { pais = it },
                        )

                        CampoCiudad(
                            ciudad = ciudad,
                            onCiudadChange = { ciudad = it },
                            pais = pais,
                        )
                    }

                    CampoDireccion(
                        direccion = direccion,
                        onDireccionChange = { direccion = it }
                    )
                }
            } else {
                CampoTelefono(
                    telefono = telefono,
                    onTelefonoChange = { telefono = it }
                )

                CampoCorreo (
                    correo = correo,
                    onCorreoChange = { correo = it }
                )

                CampoPais(
                    pais = pais,
                    onPaisChange = { pais = it },
                )

                CampoCiudad(
                    ciudad = ciudad,
                    onCiudadChange = { ciudad = it },
                    pais = pais,
                )

                CampoDireccion (
                    direccion = direccion,
                    onDireccionChange = { direccion = it }
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
                    val direccionLinea =
                        if (direccion.isNotBlank()) "Dirección: $direccion                (es campo opcional)\n\n" else ""
                    val ciudadLinea =
                        if (ciudad.isNotBlank()) "Ciudad: $ciudad                                      (es campo opcional)\n" else ""
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
                        Toast.makeText(
                            context,
                            requiredFieldAlert,
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {

                    }
                }) {
                    Text(stringResource(R.string.next_label))
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun FormularioDatosContactoPreview() {
    LabsCM20251Gr03Theme {
        FormularioDatosContacto()
    }
}
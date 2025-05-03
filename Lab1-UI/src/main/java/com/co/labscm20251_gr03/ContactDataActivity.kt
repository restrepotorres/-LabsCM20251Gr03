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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.co.labscm20251_gr03.ui.ContactDataViewModel
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
            val vm: ContactDataViewModel = viewModel()

            FormularioDatosContacto(
                telefono = vm.telefono,
                onTelefonoChange = vm::onTelefonoChange,
                correo = vm.correo,
                onCorreoChange = vm::onCorreoChange,
                pais = vm.pais,
                onPaisChange = vm::onPaisChange,
                ciudad = vm.ciudad,
                onCiudadChange = vm::onCiudadChange,
                direccion = vm.direccion,
                onDireccionChange = vm::onDireccionChange
            )
        }
    }
}

@Composable
fun FormularioDatosContacto(
    telefono: String,
    onTelefonoChange: (String) -> Unit,
    correo: String,
    onCorreoChange: (String) -> Unit,
    pais: String,
    onPaisChange: (String) -> Unit,
    ciudad: String,
    onCiudadChange: (String) -> Unit,
    direccion: String,
    onDireccionChange: (String) -> Unit
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val configuracion = LocalConfiguration.current
    val esHorizontal = configuracion.orientation == Configuration.ORIENTATION_LANDSCAPE
    val requiredFieldAlert = stringResource(R.string.alert_mandatory_fields)

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
                            onTelefonoChange = onTelefonoChange
                        )

                        CampoCorreo(
                            correo = correo,
                            onCorreoChange = onCorreoChange
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        CampoPais(
                            pais = pais,
                            onPaisChange = onPaisChange,
                        )

                        CampoCiudad(
                            ciudad = ciudad,
                            onCiudadChange = onCiudadChange,
                            pais = pais,
                        )
                    }

                    CampoDireccion(
                        direccion = direccion,
                        onDireccionChange = onDireccionChange
                    )
                }
            } else {
                CampoTelefono(
                    telefono = telefono,
                    onTelefonoChange = onTelefonoChange
                )

                CampoCorreo (
                    correo = correo,
                    onCorreoChange = onCorreoChange
                )

                CampoPais(
                    pais = pais,
                    onPaisChange = onPaisChange,
                )

                CampoCiudad(
                    ciudad = ciudad,
                    onCiudadChange = onCiudadChange,
                    pais = pais,
                )

                CampoDireccion (
                    direccion = direccion,
                    onDireccionChange = onDireccionChange
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
        val vm: ContactDataViewModel = viewModel()

        FormularioDatosContacto(
            telefono = vm.telefono,
            onTelefonoChange = vm::onTelefonoChange,
            correo = vm.correo,
            onCorreoChange = vm::onCorreoChange,
            pais = vm.pais,
            onPaisChange = vm::onPaisChange,
            ciudad = vm.ciudad,
            onCiudadChange = vm::onCiudadChange,
            direccion = vm.direccion,
            onDireccionChange = vm::onDireccionChange
        )
    }
}
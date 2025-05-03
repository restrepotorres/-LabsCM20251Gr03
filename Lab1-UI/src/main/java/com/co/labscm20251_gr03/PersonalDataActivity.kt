package com.co.labscm20251_gr03

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.co.labscm20251_gr03.ui.PersonalDataViewModel
import com.co.labscm20251_gr03.ui.element.CampoApellidos
import com.co.labscm20251_gr03.ui.element.CampoEscolaridad
import com.co.labscm20251_gr03.ui.element.CampoFechaNacimiento
import com.co.labscm20251_gr03.ui.element.CampoNombres
import com.co.labscm20251_gr03.ui.element.CampoSexo
import com.co.labscm20251_gr03.ui.element.Encabezado
import com.co.labscm20251_gr03.ui.theme.LabsCM20251Gr03Theme

class PersonalDataActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val vm: PersonalDataViewModel = viewModel()

            FormularioDatosPersonales(
                nombres            = vm.nombres,
                onNombresChange    = vm::onNombresChange,
                apellidos         = vm.apellidos,
                onApellidosChange = vm::onApellidosChange,
                fechaNacimiento   = vm.fechaNacimiento,
                onFechaChange     = vm::onFechaChange,
                escolaridad       = vm.escolaridad,
                onEscolaridadChange = vm::onEscolaridadChange,
                sexoElegido        = vm.sexoElegido,
                onSexoSelected      = vm::onSexoSelected
            )
        }
    }
}

@Composable
fun FormularioDatosPersonales(
    nombres: String,
    onNombresChange: (String) -> Unit,
    apellidos: String,
    onApellidosChange: (String) -> Unit,
    fechaNacimiento: Long?,
    onFechaChange: (Long) -> Unit,
    escolaridad: String,
    onEscolaridadChange: (String) -> Unit,
    sexoElegido: Int,
    onSexoSelected: (Int) -> Unit,
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val configuracion = LocalConfiguration.current
    val esHorizontal = configuracion.orientation == Configuration.ORIENTATION_LANDSCAPE
    val focusManager = LocalFocusManager.current
    val apellidoFocusRequester = remember { FocusRequester() }
    val requiredFieldAlert = stringResource(R.string.alert_mandatory_fields)

    Column {
        Encabezado(stringResource(R.string.personal_info))

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
                        onNombresChange = onNombresChange,
                        apellidoFocusRequester = apellidoFocusRequester
                    )

                    CampoApellidos(
                        apellidos = apellidos,
                        onApellidosChange = onApellidosChange,
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
                        sexoElegido = sexoElegido,
                        onSexoSelected = onSexoSelected
                    )

                    CampoFechaNacimiento(
                        fechaNacimiento = fechaNacimiento,
                        onFechaChange = onFechaChange
                    )
                }

                CampoEscolaridad(
                    escolaridad = escolaridad,
                    onEscolaridadChange = onEscolaridadChange,
                )
            } else {
                CampoNombres(
                    nombres = nombres,
                    onNombresChange = onNombresChange,
                    apellidoFocusRequester = apellidoFocusRequester
                )

                CampoApellidos(
                    apellidos = apellidos,
                    onApellidosChange = onApellidosChange,
                    focusManager = focusManager,
                    apellidoFocusRequester = apellidoFocusRequester
                )

                CampoSexo (
                    sexoElegido = sexoElegido,
                    onSexoSelected = onSexoSelected
                )

                CampoFechaNacimiento(
                    fechaNacimiento = fechaNacimiento,
                    onFechaChange = onFechaChange
                )

                CampoEscolaridad(
                    escolaridad = escolaridad,
                    onEscolaridadChange = onEscolaridadChange,
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
                        if (sexoElegido != 3) "\n\n$sexoElegido" else ""
                    val escolaridadLinea = if (escolaridad.isNotBlank()) "\n\n$escolaridad" else ""
                    val mensaje = """
                    Información personal: 
                    $nombreCompleto$sexoLinea
                    Nació el $fechaNacimiento$escolaridadLinea """.trimIndent()
                    Log.d("Formulario", mensaje)

                    if (nombres.isBlank() || apellidos.isBlank() || fechaNacimiento == null) {
                        Toast.makeText(
                            context,
                            requiredFieldAlert,
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        val intent = Intent(context, ContactDataActivity::class.java)
                        context.startActivity(intent)
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
fun FormularioDatosPersonalesPreview() {
    LabsCM20251Gr03Theme {
        val vm: PersonalDataViewModel = viewModel()

        FormularioDatosPersonales(
            nombres            = vm.nombres,
            onNombresChange    = vm::onNombresChange,
            apellidos         = vm.apellidos,
            onApellidosChange = vm::onApellidosChange,
            fechaNacimiento   = vm.fechaNacimiento,
            onFechaChange     = vm::onFechaChange,
            escolaridad       = vm.escolaridad,
            onEscolaridadChange = vm::onEscolaridadChange,
            sexoElegido        = vm.sexoElegido,
            onSexoSelected      = vm::onSexoSelected
        )
    }
}
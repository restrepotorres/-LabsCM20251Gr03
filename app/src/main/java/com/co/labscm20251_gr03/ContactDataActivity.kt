package com.co.labscm20251_gr03

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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material.icons.rounded.Place
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.co.labscm20251_gr03.ui.element.CampoCiudad
import com.co.labscm20251_gr03.ui.element.CampoPais
import com.co.labscm20251_gr03.ui.theme.LabsCM20251Gr03Theme

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
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "Información de Contacto")

        Row (verticalAlignment = Alignment.CenterVertically){
            Icon(
                Icons.Rounded.Phone,
                contentDescription = "Phone Icon",
                modifier = Modifier.padding(end = 12.dp).size(32.dp)
            )

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
        }

        Row (verticalAlignment = Alignment.CenterVertically){
            Icon(
                Icons.Rounded.Email,
                contentDescription = "Email Icon",
                modifier = Modifier.padding(end = 12.dp).size(32.dp)
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
        }

        Row {
            Box(modifier = Modifier.height(64.dp), contentAlignment = Alignment.CenterStart) {
                Icon(
                    Icons.Rounded.Place,
                    contentDescription = "Place Icon",
                    modifier = Modifier.padding(end = 12.dp).size(32.dp)
                )
            }

            CampoPais(
                    pais = pais,
                    onPaisChange = { pais = it },
                )
        }

        Row {
            Box(modifier = Modifier.height(64.dp), contentAlignment = Alignment.CenterStart) {
                Icon(
                    Icons.Rounded.Place,
                    contentDescription = "Place Icon",
                    modifier = Modifier.padding(end = 12.dp).size(32.dp)
                )
            }

            CampoCiudad(
                ciudad = ciudad,
                onCiudadChange = { ciudad = it },
                pais = pais,
            )
        }

        Row (verticalAlignment = Alignment.CenterVertically) {
            Icon(
                Icons.Rounded.Home,
                contentDescription = "Home Icon",
                modifier = Modifier.padding(end = 12.dp).size(32.dp)
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
                        "Por favor completa los campos obligatorios",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {

                }
            }) {
                Text("Siguiente")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DataPreview() {
    LabsCM20251Gr03Theme {
        Data()
    }
}
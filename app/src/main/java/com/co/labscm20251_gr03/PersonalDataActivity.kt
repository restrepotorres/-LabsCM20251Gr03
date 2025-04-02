package com.co.labscm20251_gr03

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.co.labscm20251_gr03.ui.theme.LabsCM20251Gr03Theme
import java.time.LocalDate

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
    var nombres: String
    var nombre by remember { mutableStateOf(TextFieldValue()) }
    var apellidos by remember { mutableStateOf(TextFieldValue()) }
    var sexo by remember { mutableStateOf("") }
    var fechaNacimiento: LocalDate
    var gradoEscolaridad = listOf<String>("Primaria", "Secundaria", "Universitaria", "Otro")

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = "Información Personal")

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombres*") }
        )
        OutlinedTextField(
            value = apellidos,
            onValueChange = { apellidos = it },
            label = { Text("Apellidos*") }
        )

        Text(text = "Sexo*")
        Row{
            Text("Masculino")
            Spacer(modifier = Modifier.width(16.dp))
            RadioButton(
                selected = sexo == "Masculino",
                onClick = {sexo = "Masculino"}
            )

            Text("Femenino")
            Spacer(modifier = Modifier.width(16.dp))
            RadioButton(
                selected = (sexo == "Femenino"),
                onClick = {sexo = "Femenino"}
            )

        }
        Button(onClick = { println("la buena") }) {
            Text("Enviar")
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
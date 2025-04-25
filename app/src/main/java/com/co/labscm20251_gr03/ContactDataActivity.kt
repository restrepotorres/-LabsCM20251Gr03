package com.co.labscm20251_gr03

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.volley.NetworkResponse
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.co.labscm20251_gr03.ui.theme.LabsCM20251Gr03Theme
import org.json.JSONObject
import java.util.Locale

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

        CampoPais(
            pais = pais,
            onPaisChange = { pais = it },
        )

        CampoCiudad(
            ciudad = ciudad,
            onCiudadChange = { ciudad = it },
            pais = pais,
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

@Composable
fun CampoPais(
    pais: String,
    onPaisChange: (String) -> Unit,
) {
    val context = LocalContext.current

    var sugerenciasPais by remember { mutableStateOf(listOf<String>()) }

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

@Composable
fun CampoCiudad(
    ciudad: String,
    onCiudadChange: (String) -> Unit,
    pais: String,
) {
    val context = LocalContext.current

    var sugerenciasCiudad by remember { mutableStateOf(listOf<String>()) }

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

fun obtenerPaises(context: Context, query: String, callback: (List<String>) -> Unit) {
    val lang = Locale.getDefault().language
    val url = "https://wft-geo-db.p.rapidapi.com/v1/geo/countries?namePrefix=$query&limit=5&languageCode=$lang"

    val queue = Volley.newRequestQueue(context)
    val request = object : StringRequest(Method.GET, url, { response ->
        val json = JSONObject(response)
        val data = json.getJSONArray("data")
        val countries = List(data.length()) {
            data.getJSONObject(it).getString("name")
        }
        callback(countries)
    }, { error ->
        callback(emptyList())
    }) {
        override fun getHeaders() = mapOf(
            "X-RapidAPI-Key" to "6e259679f8msheb61e5105e917eep1bbe4djsn2ba3e4db0587",
            "X-RapidAPI-Host" to "wft-geo-db.p.rapidapi.com"
        )

        override fun parseNetworkResponse(response: NetworkResponse): Response<String> {
            val parsed = String(response.data, charset("UTF-8"))
            return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response))
        }
    }

    queue.add(request)
}

fun obtenerCiudades(context: Context, country: String, query: String, callback: (List<String>) -> Unit) {
    val lang = Locale.getDefault().language
    val encodedCountry = country.take(2).uppercase()
    val url = "https://wft-geo-db.p.rapidapi.com/v1/geo/cities?namePrefix=$query&countryIds=$encodedCountry&limit=5&languageCode=$lang"

    val queue = Volley.newRequestQueue(context)
    val request = object : StringRequest(Method.GET, url, { response ->
        val json = JSONObject(response)
        val data = json.getJSONArray("data")
        val cities = List(data.length()) {
            data.getJSONObject(it).getString("name")
        }
        callback(cities)
    }, { error ->
        callback(emptyList())
    }) {
        override fun getHeaders() = mapOf(
            "X-RapidAPI-Key" to "6e259679f8msheb61e5105e917eep1bbe4djsn2ba3e4db0587",
            "X-RapidAPI-Host" to "wft-geo-db.p.rapidapi.com"
        )

        override fun parseNetworkResponse(response: NetworkResponse): Response<String> {
            val parsed = String(response.data, charset("UTF-8"))
            return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response))
        }
    }

    queue.add(request)
}
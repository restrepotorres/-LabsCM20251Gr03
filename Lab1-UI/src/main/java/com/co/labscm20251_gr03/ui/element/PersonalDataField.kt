package com.co.labscm20251_gr03.ui.element

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.co.labscm20251_gr03.util.obtenerCiudades
import com.co.labscm20251_gr03.util.obtenerPaises
import androidx.compose.ui.res.stringResource
import com.co.labscm20251_gr03.R

@Composable
fun CampoNombres(
    nombres : String,
    onNombresChange: (String) -> Unit,
    apellidoFocusRequester: FocusRequester
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Column {
            Icon(
                Icons.Rounded.Person,
                contentDescription = "Person Icon",
                modifier = Modifier.padding(end = 12.dp).size(32.dp)
            )
        }

        OutlinedTextField(
            value = nombres,
            onValueChange = onNombresChange,
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
    }
}

@Composable
fun CampoApellidos(
    apellidos : String,
    onApellidosChange: (String) -> Unit,
    focusManager: FocusManager,
    apellidoFocusRequester: FocusRequester
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            Icons.Rounded.Person,
            contentDescription = "Person Icon",
            modifier = Modifier.padding(end = 12.dp).size(32.dp)
        )
        OutlinedTextField(
            value = apellidos,
            onValueChange = onApellidosChange,
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
    }
}

@Composable
fun CampoSexo (
    opcionesDeSexo: List<String>,
    sexoElegido: String,
    onOptionSelected: (String) -> Unit
){
    Row {
        Icon(
            Icons.Rounded.Face,
            contentDescription = "Face Icon",
            modifier = Modifier.padding(end = 12.dp).size(32.dp)
        )
        Text(text = stringResource(R.string.sex), modifier = Modifier.padding(end = 12.dp))
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
    }
}

@Composable
fun CampoFechaNacimiento(
    fechaNacimiento: String,
    datePickerDialog: DatePickerDialog
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            Icons.Rounded.DateRange,
            contentDescription = "Date Range Icon",
            modifier = Modifier.padding(end = 12.dp).size(32.dp)
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(stringResource(R.string.birth_date))
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                shape = RoundedCornerShape(8.dp),
                onClick = { datePickerDialog.show() }) {
                Text(fechaNacimiento)
            }
        }
    }
}

@Composable
fun CampoEscolaridad (
    escolaridad: String,
    onEscolaridadChange: (String) -> Unit,
    opcionesEscolaridad: List<String>,

) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            Icons.Rounded.Info,
            contentDescription = "Info Icon",
            modifier = Modifier.padding(end = 12.dp).size(32.dp)
        )

        Box {
            Button(shape = RoundedCornerShape(8.dp), onClick = { expanded = true }) {
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
                            onEscolaridadChange(opcion)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}
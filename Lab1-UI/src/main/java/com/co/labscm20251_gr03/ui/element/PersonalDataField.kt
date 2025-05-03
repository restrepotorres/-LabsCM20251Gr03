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
import com.co.labscm20251_gr03.R
import java.util.Calendar

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
    sexoElegido: Int,
    onSexoSelected: (Int) -> Unit
){
    val opcionesDeSexo = listOf(stringResource(R.string.gender_male),
        stringResource(R.string.gender_female),
        stringResource(R.string.gender_other),
        stringResource(R.string.gender_rathernot))

    Row {
        Icon(
            Icons.Rounded.Face,
            contentDescription = "Face Icon",
            modifier = Modifier.padding(end = 12.dp).size(32.dp)
        )
        Text(text = stringResource(R.string.sex), modifier = Modifier.padding(end = 12.dp))
        Column(modifier = Modifier.selectableGroup()) {
            opcionesDeSexo.forEachIndexed { idx, opcion ->
                Row(
                    Modifier.selectable(
                        selected = (idx == sexoElegido),
                        onClick = { onSexoSelected(idx) },
                        role = Role.RadioButton
                    )
                ) {
                    RadioButton(
                        selected = (idx == sexoElegido),
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
    fechaNacimiento: Long?,
    onFechaChange: (Long) -> Unit,
) {
    val context = LocalContext.current
    val textoPredeterminado = stringResource(R.string.select_date)

    val textoMostrado = fechaNacimiento?.let {
        val cal = Calendar.getInstance().apply { timeInMillis = it }
        "${cal.get(Calendar.DAY_OF_MONTH)}/${cal.get(Calendar.MONTH)+1}/${cal.get(Calendar.YEAR)}"
    } ?: textoPredeterminado

    val datePicker = remember {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val cal = Calendar.getInstance().apply {
                    set(year, month, dayOfMonth)
                }
                onFechaChange(cal.timeInMillis)
            },
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH),
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        )
    }

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
                onClick = { datePicker.show() }) {
                Text(textoMostrado)
            }
        }
    }
}

@Composable
fun CampoEscolaridad (
    escolaridad: String,
    onEscolaridadChange: (String) -> Unit,

) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    val opcionesEscolaridad = listOf(stringResource(R.string.school_primary),
        stringResource(R.string.school_secondary),
        stringResource(R.string.school_university),
        stringResource(R.string.school_other))

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
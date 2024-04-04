package com.gtihub.vitorfg8.pettracks.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextInputDatePicker() {
    val focusManager = LocalFocusManager.current
    var showDatePickerDialog by remember {
        mutableStateOf(false)
    }
    val datePickerState = rememberDatePickerState()
    val selectedDate = datePickerState.selectedDateMillis?.toLocalDateFormat() ?: ""

    if (showDatePickerDialog) {
        DatePickerDialog(
            onDismissRequest = { showDatePickerDialog = false },
            confirmButton = {
                Button(
                    onClick = {
                        showDatePickerDialog = false
                    }) {
                    Text(text = "Escolher data")
                }
            }) {
            DatePicker(state = datePickerState)
        }
    }
    TextField(
        value = selectedDate,
        onValueChange = { },
        Modifier
            .padding(vertical = 8.dp, horizontal = 32.dp)
            .fillMaxWidth()
            .onFocusEvent {
                if (it.isFocused) {
                    showDatePickerDialog = true
                    focusManager.clearFocus(force = true)
                }
            },
        label = {
            Text("Date")
        },
        readOnly = true
    )
}

fun Long.toLocalDateFormat(): String {
    val date = Date(this)
    val dateFormat = SimpleDateFormat.getDateInstance(SimpleDateFormat.SHORT, Locale.getDefault())
    val pattern = (dateFormat as SimpleDateFormat).toPattern()
    val formatter = SimpleDateFormat(pattern, Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone("GMT")
    }
    return formatter.format(date)
}
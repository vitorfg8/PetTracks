package com.github.vitorfg8.pettracks.presentation.components

import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.github.vitorfg8.pettracks.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerTextField(
    selectedDate: Date,
    onDateSelected: (date: Date) -> Unit,
    modifier: Modifier = Modifier,
) {
    val datePickerState = rememberDatePickerState()
    val focusManager = LocalFocusManager.current
    var showDatePickerDialog by remember {
        mutableStateOf(false)
    }

    val selectedDateString = selectedDate.time.toLocalDateFormat()

    if (showDatePickerDialog) {
        DatePickerDialog(
            onDismissRequest = { showDatePickerDialog = false },
            confirmButton = {
                Button(
                    onClick = {
                        showDatePickerDialog = false
                        onDateSelected(
                            Date(
                                datePickerState.selectedDateMillis ?: System.currentTimeMillis()
                            )
                        )
                    }) {
                    Text(text = stringResource(R.string.choose_date))
                }
            }) {
            DatePicker(state = datePickerState)
        }
    }
    BaseTextField(
        value = selectedDateString,
        onValueChange = {},
        modifier.onFocusEvent {
                if (it.isFocused) {
                    showDatePickerDialog = true
                    focusManager.clearFocus(force = true)
                }
            },
        label = {
            Text(stringResource(R.string.date))
        },
        readOnly = true
    )
}

private fun Long.toLocalDateFormat(): String {
    val date = Date(this)
    val dateFormat = SimpleDateFormat.getDateInstance(SimpleDateFormat.SHORT, Locale.getDefault())
    val pattern = (dateFormat as SimpleDateFormat).toPattern()
    val formatter = SimpleDateFormat(pattern, Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone("GMT")
    }
    return formatter.format(date)
}

@Preview
@Composable
private fun TextInputDatePickerPreview() {
    val year = 2023
    val month = Calendar.MAY
    val day = 2
    val calendar = Calendar.getInstance()
    calendar.set(year, month, day)
    val date: Date = calendar.time
    DatePickerTextField(selectedDate = date, onDateSelected = {})
}

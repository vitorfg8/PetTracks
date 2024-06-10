package com.github.vitorfg8.pettracks.presentation.components

import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
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
import com.github.vitorfg8.pettracks.utils.updateTime
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldTimePicker(
    selectedDate: Date,
    onDateSelected: (date: Date) -> Unit,
    modifier: Modifier = Modifier,
) {
    val timePickerState = rememberTimePickerState()
    val focusManager = LocalFocusManager.current
    var showTimePickerDialog by remember {
        mutableStateOf(false)
    }

    val selectedTimeString = selectedDate.time.toLocalTimeFormat()

    if (showTimePickerDialog) {
        TimePickerDialog(
            onDismissRequest = { showTimePickerDialog = false },
            confirmButton = {
                Button(
                    onClick = {
                        showTimePickerDialog = false
                        onDateSelected(
                            selectedDate.updateTime(timePickerState.hour, timePickerState.minute)
                        )
                    }) {
                    Text(text = stringResource(R.string.choose_date))
                }
            }) {
            TimePicker(state = timePickerState)
        }
    }
    BaseTextField(
        value = selectedTimeString,
        onValueChange = {},
        modifier = modifier
            .onFocusEvent {
                if (it.isFocused) {
                    showTimePickerDialog = true
                    focusManager.clearFocus(force = true)
                }
            },
        label = {
            Text(stringResource(R.string.time))
        },
        readOnly = true
    )
}

fun Long.toLocalTimeFormat(): String {
    val date = Date(this)
    val dateFormat = SimpleDateFormat.getTimeInstance(SimpleDateFormat.SHORT, Locale.getDefault())
    val pattern = (dateFormat as SimpleDateFormat).toPattern()
    val formatter = SimpleDateFormat(pattern, Locale.getDefault()).apply {
        timeZone = TimeZone.getDefault()
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
    calendar.set(Calendar.HOUR_OF_DAY, 12)
    calendar.set(Calendar.MINUTE, 0)
    val date: Date = calendar.time
    TextFieldTimePicker(selectedDate = date, onDateSelected = {})
}

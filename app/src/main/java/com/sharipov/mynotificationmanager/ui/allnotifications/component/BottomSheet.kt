package com.sharipov.mynotificationmanager.ui.allnotifications.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.maxkeppeker.sheets.core.models.base.SheetState
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.sharipov.mynotificationmanager.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun bottomSheet(
    showFilters: Boolean,
    showFiltersValue: () -> Unit
): String{
    val sheetState = rememberSheetState()
    val scope = rememberCoroutineScope()
    val fromDataCalendarState = rememberSheetState()
    val toDataCalendarState = rememberSheetState()
    val fromDate = remember { mutableStateOf("") }
    val toDate = remember { mutableStateOf("") }

    if (showFilters) {
        ModalBottomSheet(
            onDismissRequest = {
                scope.launch {
                    sheetState.hide()
                }
                showFiltersValue()
            },
        ) {
            BottomSheetContent(fromDataCalendarState, toDataCalendarState, fromDate, toDate)
        }
    }

    CalendarDialog(
        state = fromDataCalendarState,
        config = CalendarConfig(
            monthSelection = true,
            yearSelection = true
        ),
        selection = CalendarSelection.Date { _date ->
            fromDate.value = _date.toString()
        }
    )

    CalendarDialog(
        state = toDataCalendarState,
        config = CalendarConfig(
            monthSelection = true,
            yearSelection = true
        ),
        selection = CalendarSelection.Date { _date ->
            toDate.value = _date.toString()
        }
    )
    return fromDate.value + ":" + toDate.value
}

@Composable
fun BottomSheetContent(
    fromDataCalendarState: SheetState,
    toDataCalendarState: SheetState,
    fromDate: MutableState<String>,
    toDate: MutableState<String>
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 36.dp, end = 36.dp, bottom = 54.dp)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clickable {
                    fromDataCalendarState.show()
                },
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(stringResource(id = R.string.from) + " " + fromDate.value)
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = Icons.Default.DateRange,
                contentDescription = null
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clickable {
                    toDataCalendarState.show()
                },
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(stringResource(id = R.string.to) + " " + toDate.value)
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = Icons.Default.DateRange,
                contentDescription = null
            )
        }
    }
}
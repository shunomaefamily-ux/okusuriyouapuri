package com.example.myapplication.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.presentation.CheckRequestViewModel

@Composable
fun CheckRequestScreen(
    personId: Long,
    vm: CheckRequestViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val state by vm.uiState.collectAsState()

    LaunchedEffect(personId) {
        vm.loadCurrent(personId)
    }

    if (state.isLoading) {
        Box(Modifier.fillMaxSize()) {
            CircularProgressIndicator()
        }
        return
    }

    val check = state.checkRequest

    if (check == null) {
        Column(Modifier.padding(16.dp)) {
            Text("現在の服薬確認はありません")

            Spacer(Modifier.height(16.dp))

            Button(onClick = { vm.loadCurrent(personId) }) {
                Text("再読み込み")
            }
        }
        return
    }

    LazyColumn(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(check.title, style = MaterialTheme.typography.headlineSmall)
        }

        item {
            Text("対象者: ${check.person_name}")
        }

        item {
            Text("予定時刻: ${check.scheduled_at}")
        }

        item {
            Text("お薬", style = MaterialTheme.typography.titleMedium)
        }

        items(check.items) { item ->
            Card {
                Column(Modifier.padding(12.dp)) {
                    Text(item.name)
                    Text("${item.dose_amount}${item.dose_unit}")
                }
            }
        }

        item {
            Spacer(Modifier.height(12.dp))

            Button(
                onClick = { vm.confirm() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("飲みました")
            }
        }

        state.message?.let {
            item {
                Text(it)
            }
        }
    }
}
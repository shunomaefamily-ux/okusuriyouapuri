package com.example.myapplication.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import com.example.myapplication.presentation.PersonSelectViewModel

@Composable
fun PersonSelectScreen(
    onPersonSelected: (Long) -> Unit,
    onOpenWeb: () -> Unit,
    vm: PersonSelectViewModel = viewModel()
) {
    val state by vm.uiState.collectAsState()

    if (state.isLoading) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("この端末を使う人を選んでください")

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onOpenWeb,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Webを見る")
        }

        Spacer(modifier = Modifier.height(16.dp))

        state.people.forEach { person ->
            Button(
                onClick = { onPersonSelected(person.id) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(person.name)
            }

            Spacer(modifier = Modifier.height(12.dp))
        }

        state.message?.let {
            Text(it)
        }
    }
}
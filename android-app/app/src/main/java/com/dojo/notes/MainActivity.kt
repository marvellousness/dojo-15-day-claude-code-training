package com.dojo.notes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.dojo.notes.ui.NotesScreen
import com.dojo.notes.ui.NotesViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val repository = (application as NotesApp).repository
        setContent {
            MaterialTheme {
                Surface(Modifier.safeDrawingPadding()) {
                    val vm: NotesViewModel = viewModel(
                        factory = viewModelFactory { initializer { NotesViewModel(repository) } },
                    )
                    val state by vm.uiState.collectAsStateWithLifecycle()
                    NotesScreen(state, onAdd = vm::addNote, onToggle = vm::toggleDone, onSync = vm::sync)
                }
            }
        }
    }
}

package com.dojo.notes

import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dojo.notes.ui.NotesScreen
import com.dojo.notes.ui.NotesUiState
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NotesScreenTest {
    @get:Rule val compose = createComposeRule()

    @Test
    fun typingAndTappingAddSendsTheNote() {
        val added = mutableListOf<String>()
        compose.setContent {
            NotesScreen(NotesUiState(), onAdd = { added += it }, onToggle = {}, onSync = {})
        }

        compose.onNodeWithTag("noteInput").performTextInput("buy milk")
        compose.onNodeWithTag("addButton").performClick()

        assertEquals(listOf("buy milk"), added)
    }

    @Test
    fun syncButtonIsDisabledWhileSyncing() {
        compose.setContent {
            NotesScreen(NotesUiState(isSyncing = true), onAdd = {}, onToggle = {}, onSync = {})
        }

        compose.onNodeWithTag("syncButton").assertIsNotEnabled()
    }
}

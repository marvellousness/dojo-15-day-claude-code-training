package com.dojo.notes

import com.dojo.notes.data.NotesRepository
import com.dojo.notes.ui.NotesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class NotesViewModelTest {
    private val dispatcher = UnconfinedTestDispatcher()
    private val dao = FakeNoteDao()
    private val api = RecordingSyncApi()

    @Before fun setUp() = Dispatchers.setMain(dispatcher)
    @After fun tearDown() = Dispatchers.resetMain()

    @Test
    fun `sync reports how many notes were pushed`() = runTest(dispatcher) {
        val vm = NotesViewModel(NotesRepository(dao, api))
        backgroundScope.launch { vm.uiState.collect {} }

        vm.addNote("a")
        vm.sync()
        advanceUntilIdle()

        val state = vm.uiState.first()
        assertEquals("Synced 1 note(s)", state.message)
        assertFalse(state.isSyncing)
    }

    @Test
    fun `sync failure is shown, not thrown`() = runTest(dispatcher) {
        api.failWith = IllegalStateException("offline")
        val vm = NotesViewModel(NotesRepository(dao, api))
        backgroundScope.launch { vm.uiState.collect {} }

        vm.addNote("a")
        vm.sync()
        advanceUntilIdle()

        assertEquals("Sync failed: offline", vm.uiState.first().message)
    }
}

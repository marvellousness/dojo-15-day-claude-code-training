package com.dojo.notes

import com.dojo.notes.data.NotesRepository
import com.dojo.notes.ui.NotesViewModel
import com.dojo.notes.ui.SyncResult
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class NotesViewModelTest {
    private val dispatcher = StandardTestDispatcher()
    private val dao = FakeNoteDao()
    private val api = RecordingSyncApi()

    @Before fun setUp() = Dispatchers.setMain(dispatcher)
    @After fun tearDown() = Dispatchers.resetMain()

    @Test
    fun `sync reports how many notes were pushed`() = runTest(dispatcher) {
        val vm = NotesViewModel(NotesRepository(dao, api, dispatcher))
        backgroundScope.launch { vm.uiState.collect {} }

        vm.addNote("a")
        vm.sync()
        advanceUntilIdle()

        val state = vm.uiState.first()
        assertEquals(SyncResult.Synced(1), state.syncResult)
        assertFalse(state.isSyncing)
    }

    @Test
    fun `sync with nothing to send still reports a result`() = runTest(dispatcher) {
        val vm = NotesViewModel(NotesRepository(dao, api, dispatcher))
        backgroundScope.launch { vm.uiState.collect {} }

        vm.sync()
        advanceUntilIdle()

        assertEquals(SyncResult.Synced(0), vm.uiState.first().syncResult)
    }

    @Test
    fun `sync failure is shown, not thrown`() = runTest(dispatcher) {
        api.failWith = IllegalStateException("offline")
        val vm = NotesViewModel(NotesRepository(dao, api, dispatcher))
        backgroundScope.launch { vm.uiState.collect {} }

        vm.addNote("a")
        vm.sync()
        advanceUntilIdle()

        assertEquals(SyncResult.Failed("offline"), vm.uiState.first().syncResult)
    }

    @Test
    fun `isSyncing is true while a sync is in flight and a second tap is ignored`() = runTest(dispatcher) {
        val gate = CompletableDeferred<Unit>()
        api.gate = gate
        val vm = NotesViewModel(NotesRepository(dao, api, dispatcher))
        backgroundScope.launch { vm.uiState.collect {} }
        vm.addNote("a")
        advanceUntilIdle()

        vm.sync()
        vm.sync()
        runCurrent()
        assertTrue(vm.uiState.value.isSyncing)

        gate.complete(Unit)
        advanceUntilIdle()

        assertFalse(vm.uiState.value.isSyncing)
        assertEquals(1, api.tokenCalls)
        assertEquals(1, api.pushed.size)
    }
}

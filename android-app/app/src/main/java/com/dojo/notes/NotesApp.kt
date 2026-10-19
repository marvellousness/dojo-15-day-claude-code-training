package com.dojo.notes

import android.app.Application
import com.dojo.notes.data.FakeSyncApi
import com.dojo.notes.data.NotesDatabase
import com.dojo.notes.data.NotesRepository

/** Manual dependency wiring; small enough that a DI framework would be noise. */
class NotesApp : Application() {
    val repository: NotesRepository by lazy {
        NotesRepository(NotesDatabase.create(this).noteDao(), FakeSyncApi())
    }
}

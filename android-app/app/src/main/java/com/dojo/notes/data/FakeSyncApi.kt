package com.dojo.notes.data

import kotlinx.coroutines.delay

/** Remote backend contract. Swapped for a fake so tests and exercises control latency. */
interface SyncApi {
    suspend fun fetchToken(): String
    suspend fun push(token: String, notes: List<Note>): List<Long>
}

/**
 * In-memory stand-in for a real backend. [latencyMs] simulates a slow network,
 * which is what makes threading and ANR bugs visible during the training exercises.
 */
class FakeSyncApi(private val latencyMs: Long = 1_500) : SyncApi {
    private var tokenCounter = 0

    override suspend fun fetchToken(): String {
        delay(latencyMs)
        tokenCounter++
        return "token-$tokenCounter"
    }

    override suspend fun push(token: String, notes: List<Note>): List<Long> {
        require(token.isNotBlank()) { "missing token" }
        delay(latencyMs)
        return notes.map { it.id }
    }
}

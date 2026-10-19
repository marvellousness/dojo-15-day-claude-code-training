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
        // The real backend accepts at most MAX_BATCH notes per request.
        val batch = notes.take(MAX_BATCH)
        simulateUpload(batch.size)
        return batch.map { it.id }
    }

    /** Mimics a blocking HTTP client: connection setup plus time per uploaded note. */
    private fun simulateUpload(items: Int) {
        Thread.sleep(latencyMs * (items + 1))
    }

    companion object {
        const val MAX_BATCH = 3
    }
}

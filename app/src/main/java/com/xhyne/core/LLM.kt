package com.xhyne.core

import java.util.concurrent.ConcurrentHashMap

interface LLMProvider {
    suspend fun generateResponse(prompt: String): String
}

object ModelRegistry {
    private val providers: ConcurrentHashMap<String, LLMProvider> = ConcurrentHashMap()

    fun addProvider(name: String, provider: LLMProvider) {
        providers[name] = provider
    }

    fun removeProvider(name: String) {
        providers.remove(name)
    }

    fun getProvider(name: String): LLMProvider? {
        return providers[name]
    }
}
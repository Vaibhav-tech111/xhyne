package com.xhyne.core

object Agent {
    suspend fun processCommand(command: String): String {
        return when {
            command.startsWith("call") -> "Calling action triggered."
            else -> "Command not recognized."
        }
    }
}
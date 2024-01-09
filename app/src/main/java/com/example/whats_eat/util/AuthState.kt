package com.example.whats_eat.util

sealed class AuthState {
    data class IsLoading(val isLoading: Boolean): AuthState()
    data class AuthResult(val result: Boolean): AuthState()
}

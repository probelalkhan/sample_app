package net.simplifiedcoding.ui.auth

interface LoginListener {
    fun onStarted()
    fun onSuccess()
    fun onFailure(s: String)
}
package com.j4yesh.tictoetoemultiplayer.Responses

data class LoginResponse(
    val lossCnt: Int,
    val token: String,
    val username: String,
    val winCnt: Int
)
package com.j4yesh.tictoetoemultiplayer.ui.Game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import org.json.JSONObject
import java.net.URI

class GameViewModel : ViewModel() {

    private val _board = MutableLiveData<Array<Array<String>>>().apply {
        value = Array(3) { Array(3) { "" } }
    }
    val board: LiveData<Array<Array<String>>> = _board

    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status

    private val _myTurn = MutableLiveData<Boolean>(false)
    val myTurn: LiveData<Boolean> = _myTurn

    private var currentSymbol = ""
    private lateinit var socket: WebSocketClient

    fun joinGame(username: String, roomId: String) {
        val uri = URI("wss://tictactoe-online-mf1f.onrender.com/game/$username/$roomId")

        socket = object : WebSocketClient(uri) {
            override fun onOpen(handshakedata: ServerHandshake?) {
                _status.postValue("Connected. Waiting for opponent...")
            }

            override fun onMessage(message: String?) {
                message?.let { handleMessage(it) }
            }

            override fun onClose(code: Int, reason: String?, remote: Boolean) {
                _status.postValue("Connection closed.")
            }

            override fun onError(ex: Exception?) {
                _status.postValue("Error: ${ex?.message}")
            }
        }
        socket.connect()
    }

    private fun handleMessage(message: String) {
        when {
            message.startsWith("Game started!") -> {
                _status.postValue(message)
                currentSymbol = if (message.contains("You are X")) "X" else "O"
                _myTurn.postValue(message.contains("Your turn"))
            }

            message.startsWith("Move:") -> {
                val data = message.removePrefix("Move:").split(",")
                if (data.size == 3) {
                    val row = data[0].trim().toInt()
                    val col = data[1].trim().toInt()
                    val symbol = data[2].trim()
                    applyMove(row, col, symbol)
                    _myTurn.postValue(symbol != currentSymbol)
                }
            }

            message.startsWith("Win:") -> {
                _status.postValue("Player ${message.split(":")[1]} wins!")
                _myTurn.postValue(false)
            }

            message == "Your turn." -> {
                _status.postValue("Your turn!")
                _myTurn.postValue(true)
            }

            message == "Not your turn!" -> {
                _status.postValue("Not your turn!")
            }
        }
    }

    private fun applyMove(row: Int, col: Int, symbol: String) {
        val updated = _board.value?.map { it.clone() }?.toTypedArray()
        updated?.get(row)?.set(col, symbol)
        _board.postValue(updated)
    }

    fun makeMove(row: Int, col: Int) {
        if (_myTurn.value == true && _board.value?.get(row)?.get(col).isNullOrBlank()) {
            val move = JSONObject().apply {
                put("type", "move")
                put("position", "$row,$col")
            }
            socket.send(move.toString())
        }
    }
}

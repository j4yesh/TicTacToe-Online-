package com.j4yesh.tictoetoemultiplayer.ui.Game

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.GridLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.j4yesh.tictoetoemultiplayer.R

class GameActivity : AppCompatActivity() {
    private lateinit var viewModel: GameViewModel
    private lateinit var cells: List<TextView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        viewModel = ViewModelProvider(this)[GameViewModel::class.java]

        val boardContainer: GridLayout = findViewById(R.id.boardGrid)
        val statusText: TextView = findViewById(R.id.statusText)
        val usernameInput: EditText = findViewById(R.id.usernameInput)
        val roomIdInput: EditText = findViewById(R.id.roomIdInput)
        val joinButton: Button = findViewById(R.id.joinGameButton)

        cells = (0 until 9).map { i ->
            val cell = boardContainer.getChildAt(i) as TextView
            cell.setOnClickListener {
                viewModel.makeMove(i / 3, i % 3)
            }
            cell
        }

        joinButton.setOnClickListener {
            val username = usernameInput.text.toString()
            val roomId = roomIdInput.text.toString()
            viewModel.joinGame(username, roomId)
        }

        viewModel.status.observe(this) { statusText.text = it }

        viewModel.board.observe(this) { board ->
            for (i in 0..2) {
                for (j in 0..2) {
                    cells[i * 3 + j].text = board[i][j]
                }
            }
        }
    }
}
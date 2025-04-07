package com.j4yesh.tictoetoemultiplayer.ui.Game

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.GridLayout
import android.widget.ProgressBar
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
//        val usernameInput: EditText = findViewById(R.id.usernameInput)
//        val roomIdInput: EditText = findViewById(R.id.roomIdInput)
//        val joinButton: Button = findViewById(R.id.joinGameButton)
        var loadingSpinner: ProgressBar=findViewById(R.id.loadingSpinner)
        loadingSpinner.visibility=View.GONE

        cells = (0 until 9).map { i ->
            val cell = boardContainer.getChildAt(i) as TextView
            cell.setOnClickListener {
                viewModel.makeMove(i / 3, i % 3)
            }
            cell
        }

//        joinButton.setOnClickListener {
//            val username = usernameInput.text.toString()
//            val roomId = roomIdInput.text.toString()
//            loadingSpinner.visibility = View.VISIBLE
//            viewModel.joinGame(username, roomId)
//        }

            val username = intent.getStringExtra("username")
            val roomId = intent.getStringExtra("roomid")
            val usernameView: TextView=findViewById<TextView>(R.id.username)
            usernameView.text="Username: "+username;
            val roomidView:TextView=findViewById<TextView>(R.id.roomid)
            roomidView.text="Room ID: "+roomId;
            loadingSpinner.visibility = View.VISIBLE
            viewModel.joinGame(username.toString(), roomId.toString())


        viewModel.status.observe(this) { statusText.text = it }
        viewModel.isLoading.observe(this) { isLoading ->
            loadingSpinner.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
        viewModel.board.observe(this) { board ->
            for (i in 0..2) {
                for (j in 0..2) {
                    cells[i * 3 + j].text = board[i][j]
                }
            }
        }
    }
}

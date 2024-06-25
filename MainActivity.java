package com.mini.app2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];
    private boolean player1Turn = true;
    private int roundCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize buttons
        initializeButtons();

        // Set click listener for reset button
        Button resetButton = findViewById(R.id.button_reset);
        resetButton.setOnClickListener(v -> resetGame());
    }

    private void initializeButtons() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }
    }

    @Override
    public void onClick(View v) {
        Button clickedButton = (Button) v;
        if (!clickedButton.getText().toString().isEmpty()) {
            return;
        }

        if (player1Turn) {
            clickedButton.setText("X");
        } else {
            clickedButton.setText("O");
        }

        roundCount++;

        if (checkForWin()) {
            declareWinner();
        } else if (roundCount == 9) {
            declareDraw();
        } else {
            player1Turn = !player1Turn;
        }
    }

    private boolean checkForWin() {
        String[][] field = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        // Check rows
        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1]) &&
                    field[i][0].equals(field[i][2]) &&
                    !field[i][0].isEmpty()) {
                return true;
            }
        }

        // Check columns
        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i]) &&
                    field[0][i].equals(field[2][i]) &&
                    !field[0][i].isEmpty()) {
                return true;
            }
        }

        // Check diagonals
        if (field[0][0].equals(field[1][1]) &&
                field[0][0].equals(field[2][2]) &&
                !field[0][0].isEmpty()) {
            return true;
        }
        if (field[0][2].equals(field[1][1]) &&
                field[0][2].equals(field[2][0]) &&
                !field[0][2].isEmpty()) {
            return true;
        }

        return false;
    }

    private void declareWinner() {
        String winner = player1Turn ? "Player X" : "Player O";
        Toast.makeText(this, winner + " wins!", Toast.LENGTH_SHORT).show();
        resetGame();
    }

    private void declareDraw() {
        Toast.makeText(this, "It's a draw!", Toast.LENGTH_SHORT).show();
        resetGame();
    }

    private void resetGame() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        player1Turn = true;
        roundCount = 0;
    }
}
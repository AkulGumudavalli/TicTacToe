import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TicTacToeFrame extends JFrame {
    private final char[][] board = new char[3][3];
    private char currentPlayer = 'X';
    private int moveCount = 0;

    private final TicTacToeButton[][] buttons = new TicTacToeButton[3][3];

    public TicTacToeFrame() {
        super("Tic Tac Toe");
        setLayout(new BorderLayout());

        JPanel boardPanel = new JPanel(new GridLayout(3, 3));
        BoardButtonListener listener = new BoardButtonListener();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                board[row][col] = ' '; // empty square
                buttons[row][col] = new TicTacToeButton(row, col);
                buttons[row][col].setFont(new Font("Arial", Font.BOLD, 60));
                buttons[row][col].addActionListener(listener);
                boardPanel.add(buttons[row][col]);
            }
        }
        add(boardPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    TicTacToeFrame.this,
                    "Are you sure you want to quit?",
                    "Confirm Quit",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        bottomPanel.add(quitButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // Frame settings
        setSize(400, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void resetBoard() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                board[row][col] = ' ';
                buttons[row][col].setText("");
                buttons[row][col].setEnabled(true);
            }
        }
        currentPlayer = 'X';
        moveCount = 0;
    }

    private boolean checkWin(char player) {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player &&
                    board[i][1] == player &&
                    board[i][2] == player) {
                return true;
            }
        }
        // Check columns
        for (int j = 0; j < 3; j++) {
            if (board[0][j] == player &&
                    board[1][j] == player &&
                    board[2][j] == player) {
                return true;
            }
        }
        // Check diagonals
        if (board[0][0] == player &&
                board[1][1] == player &&
                board[2][2] == player) {
            return true;
        }
        if (board[0][2] == player &&
                board[1][1] == player &&
                board[2][0] == player) {
            return true;
        }
        return false;
    }


    private void gameOver(String message) {
        int option = JOptionPane.showConfirmDialog(
                this,
                message + "\nDo you want to play again?",
                "Game Over",
                JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            resetBoard();
        } else {
            System.exit(0);
        }
    }

    private class BoardButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            TicTacToeButton btn = (TicTacToeButton) e.getSource();
            int row = btn.getRow();
            int col = btn.getCol();

            if (board[row][col] != ' ') {
                JOptionPane.showMessageDialog(
                        TicTacToeFrame.this,
                        "Illegal move! Square already taken. Please try again.",
                        "Illegal Move",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            board[row][col] = currentPlayer;
            btn.setText(String.valueOf(currentPlayer));
            btn.setEnabled(false);
            moveCount++;

            if (moveCount >= 5 && checkWin(currentPlayer)) {
                gameOver("Player " + currentPlayer + " wins!");
                return;
            }


            if (moveCount == 9) {
                gameOver("The game is a tie!");
                return;
            }


            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        }
    }

    /**
     * A subclass of JButton that stores its board coordinates.
     */
    private static class TicTacToeButton extends JButton {
        private final int row;
        private final int col;

        public TicTacToeButton(int row, int col) {
            super();
            this.row = row;
            this.col = col;
        }

        public int getRow() {
            return row;
        }

        public int getCol() {
            return col;
        }
    }
}

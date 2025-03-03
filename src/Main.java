public class Main {
    public static void main(String[] args) {
        // Ensure the GUI is created on the Event Dispatch Thread.
        javax.swing.SwingUtilities.invokeLater(TicTacToeFrame::new);
    }
}

import javax.swing.*;

public class Main {
    private JFrame frame;

    public Main() {
        frame = new JFrame("Airline Seat Reservation System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null); // Center the frame
        showSeatInputScreen();
    }

    public void showSeatInputScreen() {
        SeatInput seatInput = new SeatInput(this);
        frame.setContentPane(seatInput.getContentPane());
        frame.revalidate();
        frame.repaint();
    }

    public void showSeatReservationScreen(int seatNum) {
        SeatReservation seatReservation = new SeatReservation(seatNum);
        frame.setContentPane(seatReservation);
        frame.revalidate();
        frame.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main main = new Main();
            main.frame.setVisible(true);
        });
    }
}

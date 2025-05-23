import javax.swing.*;
import java.awt.*;

public class SeatLayout extends JPanel {
    private final JButton[] seats;

    public SeatLayout(int seatNum, SeatReservation seatReservation) {
        this.seats = new JButton[50]; // Initialize 50 seats for a fixed layout
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(700, 400));

        // Create the grid of seats
        JPanel seatGrid = new JPanel(new GridLayout(5, 10, 5, 5)); // 5 rows, 10 columns
        seatGrid.setBorder(BorderFactory.createTitledBorder("Airplane Seat Layout"));
        initializeSeats(seatGrid, seatNum, seatReservation);

        // Add labels indicating which side of the plane is which
        JLabel frontLabel = new JLabel("Front", SwingConstants.CENTER);
        JLabel backLabel = new JLabel("Back", SwingConstants.CENTER);
        JLabel windowsTopLabel = new JLabel("Windows", SwingConstants.CENTER);
        JLabel windowsBottomLabel = new JLabel("Windows", SwingConstants.CENTER);

        // Add components to the seat grid
        add(seatGrid, BorderLayout.CENTER);
        add(frontLabel, BorderLayout.EAST);
        add(backLabel, BorderLayout.WEST);
        add(windowsTopLabel, BorderLayout.NORTH);
        add(windowsBottomLabel, BorderLayout.SOUTH);
    }

    private void initializeSeats(JPanel seatGrid, int seatNum, SeatReservation seatReservation) {
        // For seat numbering purposes
        char[] rowLetters = {'A', 'B', 'C', 'D', 'E'};
        int columns = 10;

        for (int i = 0; i < 50; i++) {
            // Determine seat number for numbering purposes
            int row = i / columns;
            int column = (i % columns) + 1;
            String seatLabel = rowLetters[row] + String.valueOf(column);

            // Create seat button
            JButton seat = new JButton(seatLabel);
            seat.setPreferredSize(new Dimension(50, 50)); // Fixed size for each seat
            seat.setBackground(Color.LIGHT_GRAY); // Disabled seats are light gray
            seat.setOpaque(true);
            seat.setBorderPainted(false);
            seat.setEnabled(false); // Seats are initially disabled, meaning you can't click on it

            // If current seat is less than number of seats, current seat is part of the number of seats
            if (i < seatNum) {
                seat.setBackground(Color.GREEN);
            }

            // When the seat is enabled and clicked, it will redirect to SeatReservation's handleSeatClick method
            seat.addActionListener(e -> seatReservation.handleSeatClick(seat));

            // Add seat to seatGrid
            seats[i] = seat;
            seatGrid.add(seat);
        }
    }

    public JButton[] getSeats() {
        return seats;
    }
}

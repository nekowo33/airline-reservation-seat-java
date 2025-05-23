import javax.swing.*;
import java.awt.*;

public class SeatReservation extends JPanel {
    private SeatLayout seatLayout;
    private int currentSeatCount;

    public SeatReservation(int seatNum) {
        this.currentSeatCount = seatNum;
        setLayout(new BorderLayout(0, 10));

        // Create the airplane seat layout
        seatLayout = new SeatLayout(seatNum, this);
        JPanel airplanePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        airplanePanel.add(seatLayout);
        add(airplanePanel, BorderLayout.CENTER);

        // Create control panel
        JPanel controlPanel = new JPanel();
        JButton reserveButton = new JButton("Reserve Seat");
        JButton cancelButton = new JButton("Cancel Seat");
        JButton addSeatsButton = new JButton("Add Seats");
        JButton removeSeatsButton = new JButton("Remove Seats");

        controlPanel.add(reserveButton);
        controlPanel.add(cancelButton);
        controlPanel.add(addSeatsButton);
        controlPanel.add(removeSeatsButton);
        add(controlPanel, BorderLayout.SOUTH);

        // Add action listeners for buttons
        reserveButton.addActionListener(e -> setReserveMode(true));
        cancelButton.addActionListener(e -> setReserveMode(false));
        addSeatsButton.addActionListener(e -> addSeats());
        removeSeatsButton.addActionListener(e -> removeSeats());
    }

    public void addSeats() {
        String input = JOptionPane.showInputDialog(this, "How many seats do you want to add?");
        if (input == null || input.isEmpty()) return;

        try {
            int seatsToAdd = Integer.parseInt(input);
            if (seatsToAdd < 1) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number greater than 0.");
                return;
            }

            if (currentSeatCount + seatsToAdd > 50) {
                JOptionPane.showMessageDialog(this, "Cannot add that many seats. Maximum limit is 50.");
                return;
            }

            int addedSeats = 0;
            for (JButton seat : seatLayout.getSeats()) {
                if (seat.getBackground() == Color.LIGHT_GRAY && addedSeats < seatsToAdd) {
                    seat.setBackground(Color.GREEN); // Change to available color
                    seat.setEnabled(false);
                    addedSeats++;
                }
            }

            currentSeatCount += addedSeats;
            JOptionPane.showMessageDialog(this, seatsToAdd + " seats added successfully.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number.");
        }
    }

    public void removeSeats() {
        String input = JOptionPane.showInputDialog(this, "How many seats do you want to remove?");
        if (input == null || input.isEmpty()) return;

        try {
            int seatsToRemove = Integer.parseInt(input);
            if (seatsToRemove < 1) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number greater than 0.");
                return;
            }

            if (currentSeatCount - seatsToRemove < 10) {
                JOptionPane.showMessageDialog(this, "Cannot remove that many seats. At least 10 seats must remain available.");
                return;
            }

            int removedSeats = 0;
            JButton[] seats = seatLayout.getSeats();
            for (int i = 49; i >= 0; i--) {
                if ((seats[i].getBackground() == Color.GREEN || seats[i].getBackground() == Color.RED)
                        && removedSeats < seatsToRemove) {
                    seats[i].setBackground(Color.LIGHT_GRAY); // Disable the seat
                    removedSeats++;
                }
            }

            currentSeatCount -= removedSeats;
            JOptionPane.showMessageDialog(this, seatsToRemove + " seats removed successfully.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number.");
        }
    }

    public void setReserveMode(boolean isReserveMode) {
        JButton[] seats = seatLayout.getSeats();

        // Check whether there are seats to reserve or cancel for its respective mode
        int matchingSeats = 0;

        for (JButton seat : seats) {
            if (isReserveMode && seat.getBackground() == Color.GREEN) {
                matchingSeats++;
            } else if (!isReserveMode && seat.getBackground() == Color.RED) {
                matchingSeats++;
            }
        }

        // If no matching seats, tell user there's no seats to be enabled in the first place
        if (matchingSeats == 0) {
            String message = isReserveMode ? "No available seats to reserve." : "No reserved seats to cancel.";
            JOptionPane.showMessageDialog(this, message);
            return;
        }

        // If any seats are available, enable their respective seat buttons
        for (JButton seat : seats) {
            if (isReserveMode) {
                // Enable only available seats for reservation
                seat.setEnabled(seat.getBackground() == Color.GREEN);
            } else {
                // Enable only reserved seats for cancellation
                seat.setEnabled(seat.getBackground() == Color.RED);
            }
        }

        if (isReserveMode) {
            JOptionPane.showMessageDialog(this, "Please select a seat to reserve.");
        } else {
            JOptionPane.showMessageDialog(this, "Please select a seat to cancel.");
        }
    }

    // This is the method that is run when a seat is enabled and clicked
    public void handleSeatClick(JButton seat) {
        if (seat.isEnabled()) {
            String seatNumber = seat.getText();
            if (seat.getBackground() == Color.GREEN) {
                seat.setBackground(Color.RED); // Reserve the seat
                JOptionPane.showMessageDialog(this, "Seat " + seatNumber + " reserved.");
            } else if (seat.getBackground() == Color.RED) {
                seat.setBackground(Color.GREEN); // Cancel the reservation
                JOptionPane.showMessageDialog(this, "Seat " + seatNumber + " reservation canceled.");
            }

            // Disables all seats again after reserving/cancelling a seat
            resetSeatMode();
        }
    }

    public void resetSeatMode() {
        for (JButton seat : seatLayout.getSeats()) {
            seat.setEnabled(false);
        }
    }
}

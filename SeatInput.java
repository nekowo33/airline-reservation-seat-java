import javax.swing.*;
import java.awt.*;

public class SeatInput {
    private JPanel contentPane;
    private JFormattedTextField seatNumTextField;
    private JButton seatNumButton;
    private JLabel seatNumLabel;
    private JLabel seatNumLimit;
    private JLabel titleLabel;
    private Main main;

    public SeatInput(Main main) {
        this.main = main;

        // Initialize components
        contentPane = new JPanel(new GridLayout(4, 1));
        titleLabel = new JLabel("Welcome to the Airline Seat Reservation System!", SwingConstants.CENTER);
        seatNumLabel = new JLabel("Please enter number of seats", SwingConstants.CENTER);
        seatNumLimit = new JLabel("(10 minimum : 50 maximum)", SwingConstants.CENTER);
        seatNumTextField = new JFormattedTextField();
        seatNumButton = new JButton("Submit");

        // Title styling
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // Label styling
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new GridBagLayout());
        labelPanel.setBorder(BorderFactory.createEmptyBorder(80, 0, 10, 0));

        Font labelFont = new Font("Arial", Font.PLAIN, 24);
        seatNumLabel.setFont(labelFont);
        seatNumLimit.setFont(labelFont);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 5, 0);
        labelPanel.add(seatNumLabel, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        labelPanel.add(seatNumLimit, gbc);

        // Input and button styling
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 25));
        seatNumTextField.setFont(new Font("Arial", Font.PLAIN, 24));
        seatNumButton.setFont(new Font("Arial", Font.BOLD, 24));
        seatNumTextField.setPreferredSize(new Dimension(200, 50));
        seatNumButton.setPreferredSize(new Dimension(150, 50));

        inputPanel.add(seatNumTextField);
        inputPanel.add(seatNumButton);

        // Add components to the panel
        contentPane.add(titleLabel);
        contentPane.add(labelPanel);
        contentPane.add(inputPanel);

        // Button code
        seatNumButton.addActionListener(e -> {
            if (seatNumTextField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(contentPane, "Seat number cannot be empty.");
                return;
            }

            int seatNum;
            try {
                seatNum = Integer.parseInt(seatNumTextField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(contentPane, "Please enter a valid number.");
                return;
            }

            if (seatNum > 50) {
                JOptionPane.showMessageDialog(contentPane, "Seat number cannot exceed 50.");
            } else if (seatNum < 10) {
                JOptionPane.showMessageDialog(contentPane, "Seat number must be at least 10.");
            } else {
                main.showSeatReservationScreen(seatNum);
            }
        });
    }

    public JPanel getContentPane() {
        return contentPane;
    }
}

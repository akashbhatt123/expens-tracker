import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpenseTrackerApp {
    private List<Expense> expenses = new ArrayList<>();
    private Map<String, Double> categorySummaries = new HashMap<>();
    private JFrame frame;
    private DefaultListModel<Expense> expenseListModel = new DefaultListModel<>();
    private JList<Expense> expenseList;
    private JTextField descriptionField;
    private JTextField amountField;
    private JTextField categoryField;
    private JLabel summaryLabel;

    public ExpenseTrackerApp() {
        frame = new JFrame("Expense Tracker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        // Define colors
        Color primaryColor = new Color(144, 238, 144); // Light Green
        Color brownColor = new Color(139, 69, 19);

        // Set the background color of the frame
        frame.getContentPane().setBackground(primaryColor);

        // Create and set custom fonts for labels and buttons
        Font labelFont = new Font("Arial", Font.BOLD, 16);
        Font buttonFont = new Font("Arial", Font.PLAIN, 14);

        // Create a panel for input fields
        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        inputPanel.setBackground(primaryColor);

        // Create and style labels
        JLabel descriptionLabel = new JLabel("Description:");
        descriptionLabel.setFont(labelFont);
        descriptionLabel.setForeground(brownColor);

        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setFont(labelFont);
        amountLabel.setForeground(brownColor);

        JLabel categoryLabel = new JLabel("Category:");
        categoryLabel.setFont(labelFont);
        categoryLabel.setForeground(brownColor);

        // Create and style text fields
        descriptionField = new JTextField();
        amountField = new JTextField();
        categoryField = new JTextField();

        // Create and style the "Add Expense" button
        JButton addButton = new JButton("Add Expense");
        addButton.setFont(buttonFont);
        addButton.setForeground(brownColor);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addExpense();
            }
        });

        // Create a panel for the "Add Expense" button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(primaryColor);
        buttonPanel.add(addButton);

        // Create and style the summary label
        summaryLabel = new JLabel("Category Summary: ");
        summaryLabel.setFont(labelFont);
        summaryLabel.setForeground(brownColor);

        // Create a panel for the summary label
        JPanel summaryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        summaryPanel.setBackground(primaryColor);
        summaryPanel.add(summaryLabel);

        // Add the components to the input panel
        inputPanel.add(descriptionLabel);
        inputPanel.add(descriptionField);
        inputPanel.add(amountLabel);
        inputPanel.add(amountField);
        inputPanel.add(categoryLabel);
        inputPanel.add(categoryField);

        // Add the input panel, button panel, and summary panel to the frame
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.add(summaryPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void addExpense() {
        String description = descriptionField.getText();
        String amountText = amountField.getText();
        String category = categoryField.getText();

        if (description.isEmpty() || amountText.isEmpty() || category.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please fill in all fields.");
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Invalid amount format.");
            return;
        }

        Expense expense = new Expense(description, amount, category);
        expenses.add(expense);
        expenseListModel.addElement(expense);

        updateCategorySummary(category, amount);

        // Clear input fields
        descriptionField.setText("");
        amountField.setText("");
        categoryField.setText("");
    }

    private void updateCategorySummary(String category, double amount) {
        if (categorySummaries.containsKey(category)) {
            double currentTotal = categorySummaries.get(category);
            categorySummaries.put(category, currentTotal + amount);
        } else {
            categorySummaries.put(category, amount);
        }

        updateSummaryLabel();
    }

    private void updateSummaryLabel() {
        StringBuilder summaryText = new StringBuilder("Category Summary: ");
        for (Map.Entry<String, Double> entry : categorySummaries.entrySet()) {
            summaryText.append(entry.getKey()).append(" - ₹").append(entry.getValue()).append(" \n ");
        }
        summaryLabel.setText(summaryText.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ExpenseTrackerApp();
            }
        });
    }

    private class Expense {
        private String description;
        private double amount;
        private String category;

        public Expense(String description, double amount, String category) {
            this.description = description;
            this.amount = amount;
            this.category = category;
        }

        @Override
        public String toString() {
            return description + " - ₹" + amount + " (" + category + ")";
        }
    }
}

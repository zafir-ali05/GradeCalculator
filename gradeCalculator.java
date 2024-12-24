import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class gradeCalculator {

    private static JFrame frame;
    private static JTextArea textArea;
    private static JTextField nameField, gradeField, weightField;
    private static ArrayList<Assignment> assignments;

    public static void main(String[] args) {
        assignments = new ArrayList<>();
        initializeGUI();
    }

    private static void initializeGUI() {

        Color beige = new Color(255, 232, 214);
        Color skin = new Color(221, 190, 169); 

        frame = new JFrame("Grade Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);

        // Main container
        Container container = frame.getContentPane();
        container.setLayout(new BorderLayout());

        // Input panel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(skin);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Assignment name
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel assignmentNameLabel = new JLabel("Assignment Name:");
        assignmentNameLabel.setForeground(Color.WHITE);
        inputPanel.add(assignmentNameLabel, gbc);

        nameField = new JTextField(15);
        gbc.gridx = 1;
        nameField.setBackground(beige);
        inputPanel.add(nameField, gbc);

        // Grade
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel gradeLabel = new JLabel("Grade (%):");
        gradeLabel.setForeground(Color.WHITE);
        inputPanel.add(gradeLabel, gbc);

        gradeField = new JTextField(15);
        gbc.gridx = 1;
        gradeField.setBackground(beige);
        inputPanel.add(gradeField, gbc);

        // Weight
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel weightLabel = new JLabel("Weight (% of total):");
        weightLabel.setForeground(Color.WHITE);
        inputPanel.add(weightLabel, gbc);

        weightField = new JTextField(15);
        gbc.gridx = 1;
        weightField.setBackground(beige);
        inputPanel.add(weightField, gbc);

        // Buttons
        JPanel buttonsPanel = new JPanel(new FlowLayout());
        buttonsPanel.setBackground(skin);

        JButton addButton = new JButton("Add Assignment");
        addButton.setBackground(beige);
        //addButton.setForeground(Color.WHITE);
        addButton.addActionListener(e -> addAssignment());
        buttonsPanel.add(addButton);

        JButton calculateButton = new JButton("Calculate Average");
        calculateButton.setBackground(beige);
        //calculateButton.setForeground(Color.WHITE);
        calculateButton.addActionListener(e -> calculate());
        buttonsPanel.add(calculateButton);

        JButton resetButton = new JButton("Reset");
        resetButton.setBackground(beige);
        //resetButton.setForeground(Color.WHITE);
        resetButton.addActionListener(e -> reset());
        buttonsPanel.add(resetButton);

        JButton quitButton = new JButton("Quit");
        quitButton.setBackground(beige);
        //quitButton.setForeground(Color.WHITE);
        quitButton.addActionListener(e -> quit());
        buttonsPanel.add(quitButton);

        // Text area to display results
        textArea = new JTextArea(16, 40);
        textArea.setBackground(skin);
        textArea.setForeground(Color.WHITE);
        textArea.setEditable(false);
        textArea.setFont(new Font("Georgia", Font.PLAIN, 14));
        textArea.setText("Welcome to the Grade Calculator!\n\n");
        
        JScrollPane scrollPane = new JScrollPane(textArea);

        // Add components to frame
        container.add(inputPanel, BorderLayout.NORTH);
        container.add(buttonsPanel, BorderLayout.CENTER);
        container.add(scrollPane, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private static void addAssignment() {
        String name = nameField.getText().trim();
        String gradeText = gradeField.getText().trim();
        String weightText = weightField.getText().trim();

        // Validate inputs
        if (name.isEmpty() || gradeText.isEmpty() || weightText.isEmpty()) {
            textArea.append("Error: All fields must be filled out.\n");
            return;
        }

        double grade, weight;
        try {
            grade = Double.parseDouble(gradeText);
            weight = Double.parseDouble(weightText);

            if (grade < 0 || grade > 100) {
                textArea.append("Error: Grade must be between 0 and 100.\n");
                return;
            }
            if (weight < 0 || weight > 100) {
                textArea.append("Error: Weight must be between 0 and 100.\n");
                return;
            }

        } catch (NumberFormatException ex) {
            textArea.append("Error: Grade and Weight must be numeric values.\n");
            return;
        }

        // Add assignment to the list
        assignments.add(new Assignment(name, grade, weight));
        textArea.append(String.format("Added: %s \n", name));

        // Clear input fields
        nameField.setText("");
        gradeField.setText("");
        weightField.setText("");
    }

    private static void calculate() {
        if (assignments.isEmpty()) {
            textArea.append("No assignments to calculate.\n");
            return;
        }

        double totalWeightedGrade = 0;
        double totalWeight = 0;

        for (Assignment assignment : assignments) {
            totalWeightedGrade += assignment.getGrade() * (assignment.getWeight() / 100);
            totalWeight += assignment.getWeight();
        }

        if (totalWeight > 100) {
            textArea.append("Error: Total weight exceeds 100%. Please adjust weights.\n");
            return;
        }

        
        textArea.append(String.format("\nYour Current Mark: %.2f\n", totalWeightedGrade));

        if (assignments.isEmpty()) {
            textArea.append("No assignments entered.\n");
            return;
        }


        //To calculate Final Exam Requirements
        totalWeightedGrade = 0;
        totalWeight = 0;

        for (Assignment assignment : assignments) {
            totalWeightedGrade += assignment.getGrade() * (assignment.getWeight() / 100);
            totalWeight += assignment.getWeight();
        }

        double remainingWeight = 100 - totalWeight;
        if (remainingWeight <= 0) {
            textArea.append("Error: No weight left for the final exam.\n");
            return;
        }

        textArea.append("\nRequired % On Final:\n");
        int[] targetGrades = {50, 60, 70, 80, 90, 100};

        for (int target : targetGrades) {
            double requiredFinal = (target - totalWeightedGrade) / (remainingWeight / 100);

                
                textArea.append(String.format("To finish with %d%%: %.2f%%\n", target, requiredFinal));
        }

    }

    
    private static void reset() {
        assignments.clear();
        nameField.setText("");
        gradeField.setText("");
        weightField.setText("");
        textArea.setText("Reset!\n");
    }

    private static void quit() {
        System.exit(0);
    }
}

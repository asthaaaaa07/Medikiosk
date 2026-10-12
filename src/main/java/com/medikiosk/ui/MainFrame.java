
package com.medikiosk.ui;

import com.medikiosk.config.DatabaseConnection;
import com.medikiosk.config.DatabaseException;
import com.medikiosk.dao.PatientDAO;
import com.medikiosk.dao.DashboardDAO;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.RowFilter;
import java.util.concurrent.ExecutionException;
import javax.swing.SwingWorker;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.Color;
import java.awt.Cursor;

public class MainFrame extends JFrame {

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel contentPanel = new JPanel(cardLayout);
private JPanel dashboardPatientsCard;
private JPanel dashboardAppointmentsCard;
private JPanel dashboardCasesCard;
    public MainFrame() {
        super("MediKiosk | Patient Care Management");

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1000, 650));
        setSize(1200, 760);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        contentPanel.setBackground(Theme.BACKGROUND);

        contentPanel.add(createDashboard(), "Dashboard");
        contentPanel.add(createPatientsPage(), "Patients");
       contentPanel.add(createCaseHistoryPage(), "Case History");
        contentPanel.add(createAppointmentsPage(), "Appointments");

        add(createSidebar(), BorderLayout.WEST);
        add(createWorkspace(), BorderLayout.CENTER);

        showScreen("Dashboard");
    }

   
private JPanel createSidebar() {
    JPanel sidebar = new JPanel();
    sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
    sidebar.setBackground(Theme.SIDEBAR);
    sidebar.setPreferredSize(new Dimension(260, 0));
    sidebar.setBorder(
            BorderFactory.createEmptyBorder(28, 16, 24, 16));

    // Brand
    JLabel brand = new JLabel("✚ MediKiosk");
    brand.setFont(Theme.font(Font.BOLD, 25));
    brand.setForeground(Theme.SURFACE);
    brand.setAlignmentX(Component.LEFT_ALIGNMENT);

    JLabel tagline = new JLabel("Patient Care Management");
    tagline.setFont(Theme.font(Font.PLAIN, 12));
    tagline.setForeground(new Color(220, 204, 211));
    tagline.setAlignmentX(Component.LEFT_ALIGNMENT);

    sidebar.add(brand);
    sidebar.add(Box.createVerticalStrut(8));
    sidebar.add(tagline);
    sidebar.add(Box.createVerticalStrut(34));

    // Divider under branding
    JPanel divider = new JPanel();
    divider.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
    divider.setPreferredSize(new Dimension(220, 1));
    divider.setBackground(new Color(105, 76, 89));
    divider.setAlignmentX(Component.LEFT_ALIGNMENT);
    sidebar.add(divider);
    sidebar.add(Box.createVerticalStrut(28));

    // Section label
    JLabel section = new JLabel("WORKSPACE");
    section.setFont(Theme.font(Font.BOLD, 11));
    section.setForeground(new Color(207, 190, 170));
    section.setAlignmentX(Component.LEFT_ALIGNMENT);

    sidebar.add(section);
    sidebar.add(Box.createVerticalStrut(16));

    // Keep all existing navigation actions
    addNavigation(sidebar, "Dashboard");
    addNavigation(sidebar, "Patients");
    addNavigation(sidebar, "Case History");
    addNavigation(sidebar, "Appointments");

    sidebar.add(Box.createVerticalGlue());
    sidebar.add(Box.createVerticalStrut(18));

    // Footer divider
    JPanel footerDivider = new JPanel();
    footerDivider.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
    footerDivider.setPreferredSize(new Dimension(220, 1));
    footerDivider.setBackground(new Color(105, 76, 89));
    footerDivider.setAlignmentX(Component.LEFT_ALIGNMENT);
    sidebar.add(footerDivider);
    sidebar.add(Box.createVerticalStrut(18));

    JLabel footer = new JLabel(
            "<html>MEDIKIOSK<br><br>Patient Care System</html>");
    footer.setFont(Theme.font(Font.PLAIN, 11));
    footer.setForeground(new Color(220, 204, 211));
    footer.setAlignmentX(Component.LEFT_ALIGNMENT);
    sidebar.add(footer);

    return sidebar;
}


    private void addNavigation(JPanel sidebar, String name) {
       
JButton button = new JButton(name);

button.setFont(Theme.font(Font.PLAIN, 14));
button.setForeground(Theme.SIDEBAR_TEXT);
button.setBackground(Theme.SIDEBAR);
button.setFocusPainted(false);
button.setBorderPainted(false);
button.setOpaque(true);
button.setHorizontalAlignment(SwingConstants.LEFT);
button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

// Increase the width and height of each navigation box
button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
button.setPreferredSize(new Dimension(280, 42));

// Add space around the text
button.setBorder(BorderFactory.createEmptyBorder(0, 14, 0, 14));

        button.setFont(Theme.font(Font.PLAIN, 14));
        button.setForeground(java.awt.Color.WHITE);
        button.setBackground(Theme.SIDEBAR);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 8));
        button.setCursor(
                java.awt.Cursor.getPredefinedCursor(
                        java.awt.Cursor.HAND_CURSOR
                )
        );
        button.setOpaque(true);

        button.addActionListener(e -> showScreen(name));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(Theme.SIDEBAR_HOVER);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(Theme.SIDEBAR);
            }
        });
button.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebar.add(button);
        sidebar.add(Box.createVerticalStrut(5));
    }

    private JPanel createWorkspace() {
        JPanel workspace = new JPanel(new BorderLayout());
        workspace.setBackground(Theme.BACKGROUND);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Theme.SURFACE);
        header.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(
                        0, 0, 1, 0, Theme.BORDER
                ),
                BorderFactory.createEmptyBorder(18, 28, 18, 28)
        ));

        JLabel heading = new JLabel("Patient Care Management");
        heading.setFont(Theme.font(Font.BOLD, 17));
        heading.setForeground(Theme.TEXT);

        JLabel status = new JLabel("●  Workspace");
        status.setFont(Theme.font(Font.PLAIN, 12));
        status.setForeground(Theme.ACCENT);

        header.add(heading, BorderLayout.WEST);
        header.add(status, BorderLayout.EAST);

        workspace.add(header, BorderLayout.NORTH);
        workspace.add(contentPanel, BorderLayout.CENTER);

        return workspace;
    }

    private JPanel createDashboard() {
        JPanel page = new JPanel(new BorderLayout(0, 24));
        page.setBackground(Theme.BACKGROUND);
        page.setBorder(BorderFactory.createEmptyBorder(28, 28, 28, 28));

        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.setOpaque(false);

        JLabel heading = Ui.title("Dashboard");
        JLabel subtitle = Ui.subtitle(
                "Overview of your patient care activity"
        );

        heading.setAlignmentX(Component.LEFT_ALIGNMENT);
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        top.add(heading);
        top.add(Box.createVerticalStrut(7));
        top.add(subtitle);
        top.add(Box.createVerticalStrut(24));

        JPanel stats = new JPanel(new GridLayout(1, 3, 16, 0));
        stats.setOpaque(false);
        stats.setMaximumSize(new Dimension(Integer.MAX_VALUE, 135));

       dashboardPatientsCard = createStatCard(
    "Total Patients", "Loading...", "Registered patient records");

dashboardAppointmentsCard = createStatCard(
    "Appointments", "Loading...", "Scheduled visits");

dashboardCasesCard = createStatCard(
    "Case Records", "Loading...", "Medical histories");

stats.add(dashboardPatientsCard);
stats.add(dashboardAppointmentsCard);
stats.add(dashboardCasesCard);
        stats.setAlignmentX(Component.LEFT_ALIGNMENT);
        top.add(stats);

        JPanel welcome = Ui.card();
        welcome.setLayout(new BoxLayout(welcome, BoxLayout.Y_AXIS));

        JLabel welcomeTitle = new JLabel("Welcome to MediKiosk");
        welcomeTitle.setFont(Theme.font(Font.BOLD, 20));
        welcomeTitle.setForeground(Theme.TEXT);

        JLabel welcomeText = Ui.subtitle(
                "<html>Your patient care workspace is ready.<br><br>"
                + "Use the sidebar to explore the available modules. "
                + "Patient records,statistics,case histories "
                + "appointments all in a single click"
                + "</html>"
        );

        welcome.add(welcomeTitle);
        welcome.add(Box.createVerticalStrut(14));
        welcome.add(welcomeText);
page.add(top, BorderLayout.NORTH);
page.add(welcome, BorderLayout.CENTER);



        return page;
    }

   
private JPanel createStatCard(
        String title, String value, String description) {

    JPanel card = Ui.card();
    card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

    JLabel titleLabel = Ui.subtitle(title);

    JLabel valueLabel = new JLabel(value);
    valueLabel.setFont(Theme.font(Font.BOLD, 29));
    valueLabel.setForeground(Theme.ACCENT);
    valueLabel.setName("statValue");

    JLabel descriptionLabel = Ui.subtitle(description);

    card.add(titleLabel);
    card.add(Box.createVerticalStrut(10));
    card.add(valueLabel);
    card.add(Box.createVerticalStrut(5));
    card.add(descriptionLabel);

    return card;
}

private void loadDashboardStatistics(
        JPanel patientsCard,
        JPanel appointmentsCard,
        JPanel casesCard) {

    SwingWorker<int[], Void> worker = new SwingWorker<>() {

        @Override
        protected int[] doInBackground() throws Exception {
            DashboardDAO dao = new DashboardDAO();

            return new int[] {
                dao.getTotalPatients(),
                dao.getTotalAppointments(),
                dao.getTotalCaseRecords()
            };
        }

        @Override
        protected void done() {
            try {
                int[] counts = get();

                updateStatCard(patientsCard, counts[0]);
                updateStatCard(appointmentsCard, counts[1]);
                updateStatCard(casesCard, counts[2]);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                showDashboardError();

            } catch (ExecutionException e) {
                showDashboardError();
            }
        }
    };

    worker.execute();
}

private void updateStatCard(JPanel card, int count) {
    for (Component component : card.getComponents()) {
        if (component instanceof JLabel label
                && "statValue".equals(label.getName())) {
            label.setText(String.valueOf(count));
            return;
        }
    }
}

private void showDashboardError() {
    JOptionPane.showMessageDialog(
            this,
            "Dashboard statistics could not be loaded. "
                    + "Please check your database connection.",
            "Dashboard error",
            JOptionPane.WARNING_MESSAGE
    );
}


    private JPanel createPlaceholder(String title, String description) {
        JPanel page = new JPanel(new BorderLayout(0, 18));
        page.setBackground(Theme.BACKGROUND);
        page.setBorder(BorderFactory.createEmptyBorder(28, 28, 28, 28));

        JPanel text = new JPanel();
        text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));
        text.setOpaque(false);

        JLabel heading = Ui.title(title);
        JLabel subtitle = Ui.subtitle(description);

        heading.setAlignmentX(Component.LEFT_ALIGNMENT);
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        text.add(heading);
        text.add(Box.createVerticalStrut(8));
        text.add(subtitle);

        page.add(text, BorderLayout.NORTH);
        return page;
    }

    private void showScreen(String name) {
    cardLayout.show(contentPanel, name);

    if ("Dashboard".equals(name)
            && dashboardPatientsCard != null
            && dashboardAppointmentsCard != null
            && dashboardCasesCard != null) {

        loadDashboardStatistics(
            dashboardPatientsCard,
            dashboardAppointmentsCard,
            dashboardCasesCard
        );
    }
}
private JPanel createPatientsPage() {
    JPanel page = new JPanel(new BorderLayout(0, 20));
    page.setBackground(Theme.BACKGROUND);
    page.setBorder(BorderFactory.createEmptyBorder(28, 28, 28, 28));

    JLabel heading = Ui.title("Patient Registration");
    JLabel subtitle = Ui.subtitle(
            "Enter patient details to create a new record."
    );

    JPanel headingPanel = new JPanel();
    headingPanel.setLayout(new BoxLayout(headingPanel, BoxLayout.Y_AXIS));
    headingPanel.setOpaque(false);
    heading.setAlignmentX(Component.LEFT_ALIGNMENT);
    subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);
    headingPanel.add(heading);
    headingPanel.add(Box.createVerticalStrut(7));
    headingPanel.add(subtitle);

    JPanel form = Ui.card();
    form.setLayout(new BorderLayout(0, 16));

    JPanel fields = new JPanel(new GridLayout(0, 2, 16, 12));
    fields.setOpaque(false);

    JTextField nameField = new JTextField();
    JTextField dobField = new JTextField();
    JComboBox<String> genderField = new JComboBox<>(
            new String[]{"Select gender", "Female", "Male", "Other"}
    );
    JTextField phoneField = new JTextField();
    JTextField emailField = new JTextField();
    JTextArea addressField = new JTextArea(3, 20);
    addressField.setLineWrap(true);
    addressField.setWrapStyleWord(true);

    fields.add(createLabeledField("Full name *", nameField));
    fields.add(createLabeledField("Date of birth (YYYY-MM-DD)", dobField));
    fields.add(createLabeledField("Gender", genderField));
    fields.add(createLabeledField("Phone", phoneField));
    fields.add(createLabeledField("Email", emailField));
    fields.add(createLabeledField(
            "Address",
            new JScrollPane(addressField)
    ));

    JButton saveButton = new JButton("Save Patient");
    saveButton.setFont(Theme.font(Font.BOLD, 14));
    saveButton.setBackground(Theme.ACCENT);
    saveButton.setForeground(Theme.SURFACE);
    saveButton.setOpaque(true);              // ADD THIS
saveButton.setContentAreaFilled(true);
    saveButton.setFocusPainted(false);
    saveButton.setMargin(new Insets(10, 20, 10, 20));

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    buttonPanel.setOpaque(false);
    buttonPanel.add(saveButton);

    saveButton.addActionListener(e -> {
        String fullName = nameField.getText().trim();

        if (fullName.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter the patient's full name.",
                    "Missing information",
                    JOptionPane.WARNING_MESSAGE
            );
            nameField.requestFocus();
            return;
        }

        LocalDate dob = null;
        String dobText = dobField.getText().trim();

        if (!dobText.isEmpty()) {
            try {
                dob = LocalDate.parse(dobText);
                if (dob.isAfter(LocalDate.now())) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Date of birth cannot be in the future."
                    );
                    return;
                }
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Enter date of birth in YYYY-MM-DD format.",
                        "Invalid date",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }
        }

        String gender = (String) genderField.getSelectedItem();
        if ("Select gender".equals(gender)) {
            gender = null;
        }

        try {
            int patientId = new PatientDAO().savePatient(
                    fullName,
                    dob,
                    gender,
                    phoneField.getText().trim(),
                    emailField.getText().trim(),
                    addressField.getText().trim()
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Patient saved successfully!\nPatient ID: " + patientId,
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            nameField.setText("");
            dobField.setText("");
            genderField.setSelectedIndex(0);
            phoneField.setText("");
            emailField.setText("");
            addressField.setText("");

        } catch (DatabaseException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Database error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    });

    form.add(fields, BorderLayout.CENTER);
    form.add(buttonPanel, BorderLayout.SOUTH);

    
page.add(headingPanel, BorderLayout.NORTH);

// Put registration form and patient records vertically.
JPanel body = new JPanel();
body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
body.setOpaque(false);

// Keep the existing registration form.
form.setAlignmentX(Component.LEFT_ALIGNMENT);
body.add(form);
body.add(Box.createVerticalStrut(20));

// Patient records heading and controls.
JLabel recordsHeading = Ui.title("Registered Patients");
JLabel recordsSubtitle = Ui.subtitle(
        "Search and view patients saved in the database."
);

JPanel recordsHeader = new JPanel();
recordsHeader.setLayout(
        new BoxLayout(recordsHeader, BoxLayout.Y_AXIS));
recordsHeader.setOpaque(false);
recordsHeader.setAlignmentX(Component.LEFT_ALIGNMENT);
recordsHeading.setAlignmentX(Component.LEFT_ALIGNMENT);
recordsSubtitle.setAlignmentX(Component.LEFT_ALIGNMENT);
recordsHeader.add(recordsHeading);
recordsHeader.add(Box.createVerticalStrut(5));
recordsHeader.add(recordsSubtitle);
body.add(recordsHeader);
body.add(Box.createVerticalStrut(12));

JTextField searchField = new JTextField();
searchField.setFont(Theme.font(Font.PLAIN, 14));
searchField.setToolTipText("Search by name, phone, email or patient ID");

JButton refreshButton = new JButton("Refresh");

JPanel controls = new JPanel(new BorderLayout(10, 0));
controls.setOpaque(false);
controls.add(searchField, BorderLayout.CENTER);
controls.add(refreshButton, BorderLayout.EAST);
controls.setAlignmentX(Component.LEFT_ALIGNMENT);
body.add(controls);
body.add(Box.createVerticalStrut(10));

// Table columns.
String[] columns = {
    "Patient ID", "Full Name", "Date of Birth",
    "Gender", "Phone", "Email"
};

DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
};

JTable patientTable = new JTable(tableModel);
patientTable.setRowHeight(30);
patientTable.setFont(Theme.font(Font.PLAIN, 13));
patientTable.getTableHeader().setFont(Theme.font(Font.BOLD, 13));

TableRowSorter<DefaultTableModel> sorter =
        new TableRowSorter<>(tableModel);
patientTable.setRowSorter(sorter);

searchField.getDocument().addDocumentListener(
        new javax.swing.event.DocumentListener() {
    private void filterTable() {
        String query = searchField.getText().trim();

        if (query.isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(
                    RowFilter.regexFilter(
                            "(?i)" + java.util.regex.Pattern.quote(query)));
        }
    }

    @Override
    public void insertUpdate(javax.swing.event.DocumentEvent e) {
        filterTable();
    }

    @Override
    public void removeUpdate(javax.swing.event.DocumentEvent e) {
        filterTable();
    }

    @Override
    public void changedUpdate(javax.swing.event.DocumentEvent e) {
        filterTable();
    }
});

JScrollPane tableScroll = new JScrollPane(patientTable);
tableScroll.setAlignmentX(Component.LEFT_ALIGNMENT);
tableScroll.setPreferredSize(new java.awt.Dimension(800, 220));
tableScroll.setMinimumSize(new java.awt.Dimension(400, 150));
body.add(tableScroll);

// Load patients from MySQL.
Runnable loadPatients = () -> {
    refreshButton.setEnabled(false);

    new SwingWorker<List<Object[]>, Void>() {
        @Override
        protected List<Object[]> doInBackground() throws Exception {
            return new PatientDAO().getAllPatients();
        }

        @Override
        protected void done() {
            try {
                List<Object[]> patients = get();
                tableModel.setRowCount(0);

                for (Object[] patient : patients) {
                    tableModel.addRow(patient);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                        MainFrame.this,
                        "Could not load patients: " + ex.getMessage(),
                        "Database error",
                        JOptionPane.ERROR_MESSAGE
                );
            } finally {
                refreshButton.setEnabled(true);
            }
        }
    }.execute();
};

refreshButton.addActionListener(e -> loadPatients.run());

// Load automatically when the page opens.
loadPatients.run();

JScrollPane bodyScroll = new JScrollPane(body);
bodyScroll.setBorder(null);
bodyScroll.getViewport().setOpaque(false);
bodyScroll.setOpaque(false);
bodyScroll.getVerticalScrollBar().setUnitIncrement(16);

page.add(bodyScroll, BorderLayout.CENTER);

return page;

}

private JPanel createLabeledField(String labelText, JComponent field) {
    JPanel panel = new JPanel(new BorderLayout(0, 6));
    panel.setOpaque(false);

    JLabel label = new JLabel(labelText);
    label.setFont(Theme.font(Font.BOLD, 12));
    label.setForeground(Theme.TEXT);

    field.setFont(Theme.font(Font.PLAIN, 14));

    panel.add(label, BorderLayout.NORTH);
    panel.add(field, BorderLayout.CENTER);

    return panel;
}

private JPanel createCaseHistoryPage() {
    JPanel page = new JPanel(new BorderLayout(0, 20));
    page.setBackground(Theme.BACKGROUND);
    page.setBorder(
            BorderFactory.createEmptyBorder(28, 28, 28, 28));

    JLabel heading = Ui.title("Medical Case History");
    JLabel subtitle = Ui.subtitle(
            "Record medical details for a registered patient.");

    JPanel headingPanel = new JPanel();
    headingPanel.setLayout(
            new BoxLayout(headingPanel, BoxLayout.Y_AXIS));
    headingPanel.setOpaque(false);
    heading.setAlignmentX(Component.LEFT_ALIGNMENT);
    subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);
    headingPanel.add(heading);
    headingPanel.add(Box.createVerticalStrut(7));
    headingPanel.add(subtitle);

    JPanel form = Ui.card();
    form.setLayout(new BorderLayout(0, 16));

    JPanel fields = new JPanel(new GridLayout(0, 2, 16, 12));
    fields.setOpaque(false);

    JComboBox<String> patientField = new JComboBox<>();
    JTextArea symptomsField = new JTextArea(3, 20);
    JTextArea allergiesField = new JTextArea(3, 20);
    JTextArea conditionsField = new JTextArea(3, 20);
    JTextArea medicationsField = new JTextArea(3, 20);
    JComboBox<String> bloodGroupField = new JComboBox<>(
            new String[]{"Select blood group", "A+", "A-", "B+",
                    "B-", "AB+", "AB-", "O+", "O-", "Unknown"}
    );

    symptomsField.setLineWrap(true);
    symptomsField.setWrapStyleWord(true);
    allergiesField.setLineWrap(true);
    allergiesField.setWrapStyleWord(true);
    conditionsField.setLineWrap(true);
    conditionsField.setWrapStyleWord(true);
    medicationsField.setLineWrap(true);
    medicationsField.setWrapStyleWord(true);

    fields.add(createLabeledField("Select patient", patientField));
    fields.add(createLabeledField("Blood group", bloodGroupField));
    fields.add(createLabeledField(
            "Symptoms", new JScrollPane(symptomsField)));
    fields.add(createLabeledField(
            "Allergies", new JScrollPane(allergiesField)));
    fields.add(createLabeledField(
            "Existing conditions", new JScrollPane(conditionsField)));
    fields.add(createLabeledField(
            "Current medications", new JScrollPane(medicationsField)));

    JButton saveButton = new JButton("Save Case History");
    saveButton.setFont(Theme.font(Font.BOLD, 14));
    saveButton.setBackground(Theme.ACCENT);
    saveButton.setForeground(Theme.SURFACE);
    saveButton.setOpaque(true);              // ADD THIS
saveButton.setContentAreaFilled(true);
    saveButton.setFocusPainted(false);
    saveButton.setMargin(new Insets(10, 20, 10, 20));

    JButton refreshPatientsButton = new JButton("Refresh Patients");
refreshPatientsButton.setForeground(Theme.TEXT);
    JPanel buttonPanel = new JPanel(
            new FlowLayout(FlowLayout.RIGHT));
    buttonPanel.setOpaque(false);
    buttonPanel.add(refreshPatientsButton);
    buttonPanel.add(saveButton);

    form.add(fields, BorderLayout.CENTER);
    form.add(buttonPanel, BorderLayout.SOUTH);

    // Store the patient ID separately from the displayed name.
    java.util.Map<String, Integer> patientIds =
            new java.util.LinkedHashMap<>();

    Runnable loadPatients = () -> {
        refreshPatientsButton.setEnabled(false);

        new SwingWorker<List<Object[]>, Void>() {
            @Override
            protected List<Object[]> doInBackground() throws Exception {
                return new PatientDAO().getAllPatients();
            }

            @Override
            protected void done() {
                try {
                    List<Object[]> patients = get();
                    patientField.removeAllItems();
                    patientIds.clear();

                    for (Object[] patient : patients) {
                        int id = ((Number) patient[0]).intValue();
                        String name = String.valueOf(patient[1]);
                        String display = id + " - " + name;

                        patientField.addItem(display);
                        patientIds.put(display, id);
                    }

                    if (patients.isEmpty()) {
                        JOptionPane.showMessageDialog(
                                MainFrame.this,
                                "Register a patient before adding case history.",
                                "No patients found",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                            MainFrame.this,
                            "Could not load patients: " + ex.getMessage(),
                            "Database error",
                            JOptionPane.ERROR_MESSAGE
                    );
                } finally {
                    refreshPatientsButton.setEnabled(true);
                }
            }
        }.execute();
    };

    refreshPatientsButton.addActionListener(e -> loadPatients.run());

    saveButton.addActionListener(e -> {
        String selected = (String) patientField.getSelectedItem();

        if (selected == null || !patientIds.containsKey(selected)) {
            JOptionPane.showMessageDialog(
                    MainFrame.this,
                    "Please register and select a patient first.",
                    "Patient required",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String bloodGroup =
                (String) bloodGroupField.getSelectedItem();

        if ("Select blood group".equals(bloodGroup)) {
            bloodGroup = null;
        }

        try {
            new com.medikiosk.dao.MedicalHistoryDAO()
                    .saveMedicalHistory(
                            patientIds.get(selected),
                            symptomsField.getText().trim(),
                            allergiesField.getText().trim(),
                            conditionsField.getText().trim(),
                            medicationsField.getText().trim(),
                            bloodGroup
                    );

            JOptionPane.showMessageDialog(
                    MainFrame.this,
                    "Case history saved successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            symptomsField.setText("");
            allergiesField.setText("");
            conditionsField.setText("");
            medicationsField.setText("");
            bloodGroupField.setSelectedIndex(0);

        } catch (DatabaseException ex) {
            JOptionPane.showMessageDialog(
                    MainFrame.this,
                    ex.getMessage(),
                    "Database error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    });

   
page.add(headingPanel, BorderLayout.NORTH);

// Keep the existing case-history form.
JPanel body = new JPanel();
body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
body.setOpaque(false);

form.setAlignmentX(Component.LEFT_ALIGNMENT);
body.add(form);
body.add(Box.createVerticalStrut(20));

JLabel recordsHeading = Ui.title("Saved Case Histories");
JLabel recordsSubtitle = Ui.subtitle(
        "View medical histories saved for registered patients.");

recordsHeading.setAlignmentX(Component.LEFT_ALIGNMENT);
recordsSubtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

JPanel recordsHeader = new JPanel();
recordsHeader.setLayout(
        new BoxLayout(recordsHeader, BoxLayout.Y_AXIS));
recordsHeader.setOpaque(false);
recordsHeader.setAlignmentX(Component.LEFT_ALIGNMENT);
recordsHeader.add(recordsHeading);
recordsHeader.add(Box.createVerticalStrut(5));
recordsHeader.add(recordsSubtitle);

body.add(recordsHeader);
body.add(Box.createVerticalStrut(12));

String[] columns = {
    "History ID", "Patient ID", "Patient Name", "Symptoms",
    "Allergies", "Existing Conditions", "Medications",
    "Blood Group", "Recorded At"
};

DefaultTableModel historyModel = new DefaultTableModel(columns, 0) {
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
};

JTable historyTable = new JTable(historyModel);
historyTable.setRowHeight(28);
historyTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
historyTable.setAutoCreateRowSorter(true);
historyTable.getTableHeader().setReorderingAllowed(false);

int[] widths = {75, 75, 150, 180, 150, 180, 180, 90, 160};
for (int i = 0; i < widths.length; i++) {
    historyTable.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
}

JScrollPane historyScroll = new JScrollPane(historyTable);
historyScroll.setPreferredSize(new java.awt.Dimension(900, 220));
historyScroll.setMinimumSize(new java.awt.Dimension(400, 150));
historyScroll.setAlignmentX(Component.LEFT_ALIGNMENT);

body.add(historyScroll);

JButton refreshHistoryButton = new JButton("Refresh Case Histories");
JPanel refreshPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
refreshPanel.setOpaque(false);
refreshPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
refreshPanel.add(refreshHistoryButton);

body.add(Box.createVerticalStrut(8));
body.add(refreshPanel);

// Retrieve saved histories from MySQL.
Runnable loadHistories = () -> {
    refreshHistoryButton.setEnabled(false);

    new SwingWorker<List<Object[]>, Void>() {
        @Override
        protected List<Object[]> doInBackground() throws Exception {
            return new com.medikiosk.dao.MedicalHistoryDAO()
                    .getAllMedicalHistories();
        }

        @Override
        protected void done() {
            try {
                List<Object[]> histories = get();
                historyModel.setRowCount(0);

                for (Object[] history : histories) {
                    historyModel.addRow(history);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                        MainFrame.this,
                        "Could not load case histories: "
                                + ex.getMessage(),
                        "Database error",
                        JOptionPane.ERROR_MESSAGE
                );
            } finally {
                refreshHistoryButton.setEnabled(true);
            }
        }
    }.execute();
};

refreshHistoryButton.addActionListener(e -> loadHistories.run());

// Load the table when the page is created.
loadHistories.run();
loadPatients.run();

JScrollPane bodyScroll = new JScrollPane(body);
bodyScroll.setBorder(null);
bodyScroll.setOpaque(false);
bodyScroll.getViewport().setOpaque(false);
bodyScroll.getVerticalScrollBar().setUnitIncrement(16);

page.add(bodyScroll, BorderLayout.CENTER);

return page;

}


private JPanel createAppointmentsPage() {
    JPanel page = new JPanel(new BorderLayout(0, 16));
    page.setBackground(Theme.BACKGROUND);
    page.setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));

    // Page heading
    JPanel heading = new JPanel();
    heading.setLayout(new BoxLayout(heading, BoxLayout.Y_AXIS));
    heading.setOpaque(false);
    heading.setAlignmentX(Component.LEFT_ALIGNMENT);

    JLabel title = Ui.title("Appointments");
    title.setAlignmentX(Component.LEFT_ALIGNMENT);

    JLabel subtitle = Ui.subtitle(
            "Schedule and manage patient appointments");
    subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

    heading.add(title);
    heading.add(Box.createVerticalStrut(6));
    heading.add(subtitle);

    // Main content
    JPanel body = new JPanel();
    body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
    body.setOpaque(false);

    // Appointment form
    JPanel formCard = Ui.card();
    formCard.setLayout(new BorderLayout(0, 14));
    formCard.setAlignmentX(Component.LEFT_ALIGNMENT);

    JLabel formTitle = Ui.label("Schedule New Appointment");

    JPanel form = new JPanel(new GridLayout(0, 2, 16, 12));
    form.setOpaque(false);

    JComboBox<String> patientCombo = new JComboBox<>();
    patientCombo.setFont(Theme.font(Font.PLAIN, 14));

    JTextField dateField = Ui.textField(15);
    dateField.setText(LocalDate.now().toString());

    JTextField reasonField = Ui.textField(20);

    form.add(Ui.label("Select Patient"));
    form.add(patientCombo);

    form.add(Ui.label("Appointment Date (YYYY-MM-DD)"));
    form.add(dateField);

    form.add(Ui.label("Reason for Appointment"));
    form.add(reasonField);

    JButton saveButton = Ui.primaryButton("Save Appointment");
    saveButton.setForeground(Theme.SURFACE);
    JButton refreshPatientsButton =
            Ui.secondaryButton("Refresh Patients");

    JPanel formButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    formButtons.setOpaque(false);
    formButtons.add(refreshPatientsButton);
    formButtons.add(saveButton);

    JPanel formContent = new JPanel(new BorderLayout(0, 12));
    formContent.setOpaque(false);
    formContent.add(form, BorderLayout.CENTER);
    formContent.add(formButtons, BorderLayout.SOUTH);

    formCard.add(formTitle, BorderLayout.NORTH);
    formCard.add(formContent, BorderLayout.CENTER);

    body.add(formCard);
    body.add(Box.createVerticalStrut(22));

    // Appointments table
    JLabel tableTitle = Ui.title("Appointment Records");
    tableTitle.setFont(Theme.font(Font.BOLD, 20));
    tableTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

    body.add(tableTitle);
    body.add(Box.createVerticalStrut(10));

    String[] columns = {
            "Appointment ID", "Patient ID", "Patient Name",
            "Date", "Reason", "Status"
    };

    DefaultTableModel model = new DefaultTableModel(columns, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    JTable table = new JTable(model);
    table.setRowHeight(28);
    table.setAutoCreateRowSorter(true);
    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

    int[] widths = {110, 85, 180, 120, 240, 120};

    for (int i = 0; i < widths.length; i++) {
        table.getColumnModel().getColumn(i)
                .setPreferredWidth(widths[i]);
    }

    JScrollPane tableScroll = new JScrollPane(table);
    tableScroll.setPreferredSize(new Dimension(900, 240));
    tableScroll.setMinimumSize(new Dimension(400, 150));
    tableScroll.setAlignmentX(Component.LEFT_ALIGNMENT);

    body.add(tableScroll);

    JButton refreshButton =
            Ui.secondaryButton("Refresh Appointments");

    JPanel refreshPanel = new JPanel(
            new FlowLayout(FlowLayout.RIGHT));
    refreshPanel.setOpaque(false);
    refreshPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    refreshPanel.add(refreshButton);

    body.add(Box.createVerticalStrut(8));
    body.add(refreshPanel);

    // Store patient display names and their IDs
    java.util.Map<String, Integer> patientIds =
            new java.util.LinkedHashMap<>();

    Runnable loadPatients = () -> {
        refreshPatientsButton.setEnabled(false);

        new SwingWorker<List<Object[]>, Void>() {
            @Override
            protected List<Object[]> doInBackground() throws Exception {
                return new PatientDAO().getAllPatients();
            }

            @Override
            protected void done() {
                try {
                    List<Object[]> patients = get();

                    patientIds.clear();
                    patientCombo.removeAllItems();

                    for (Object[] patient : patients) {
                        int id = ((Number) patient[0]).intValue();
                        String name = String.valueOf(patient[1]);
                        String display = id + " - " + name;

                        patientIds.put(display, id);
                        patientCombo.addItem(display);
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                            MainFrame.this,
                            "Could not load patients: " + ex.getMessage(),
                            "Database error",
                            JOptionPane.ERROR_MESSAGE
                    );
                } finally {
                    refreshPatientsButton.setEnabled(true);
                }
            }
        }.execute();
    };

    Runnable loadAppointments = () -> {
        refreshButton.setEnabled(false);

        new SwingWorker<List<Object[]>, Void>() {
            @Override
            protected List<Object[]> doInBackground() throws Exception {
                return new com.medikiosk.dao.AppointmentDAO()
                        .getAllAppointments();
            }

            @Override
            protected void done() {
                try {
                    List<Object[]> appointments = get();
                    model.setRowCount(0);

                    for (Object[] appointment : appointments) {
                        model.addRow(appointment);
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                            MainFrame.this,
                            "Could not load appointments: "
                                    + ex.getMessage(),
                            "Database error",
                            JOptionPane.ERROR_MESSAGE
                    );
                } finally {
                    refreshButton.setEnabled(true);
                }
            }
        }.execute();
    };

    // Save appointment
    saveButton.addActionListener(e -> {
        String selected = (String) patientCombo.getSelectedItem();
        String dateText = dateField.getText().trim();
        String reason = reasonField.getText().trim();

        if (selected == null) {
            JOptionPane.showMessageDialog(
                    MainFrame.this,
                    "Please register and select a patient first."
            );
            return;
        }

        if (reason.isEmpty()) {
            JOptionPane.showMessageDialog(
                    MainFrame.this,
                    "Please enter the reason for the appointment."
            );
            return;
        }

        try {
            LocalDate date = LocalDate.parse(dateText);
            int patientId = patientIds.get(selected);

            saveButton.setEnabled(false);

            new SwingWorker<Integer, Void>() {
                @Override
                protected Integer doInBackground() throws Exception {
                    return new com.medikiosk.dao.AppointmentDAO()
                            .saveAppointment(patientId, date, reason);
                }

                @Override
                protected void done() {
                    try {
                        int id = get();

                        JOptionPane.showMessageDialog(
                                MainFrame.this,
                                "Appointment saved successfully! ID: " + id
                        );

                        reasonField.setText("");
                        loadAppointments.run();

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(
                                MainFrame.this,
                                "Could not save appointment: "
                                        + ex.getMessage(),
                                "Database error",
                                JOptionPane.ERROR_MESSAGE
                        );
                    } finally {
                        saveButton.setEnabled(true);
                    }
                }
            }.execute();

        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(
                    MainFrame.this,
                    "Enter the date in YYYY-MM-DD format."
            );
        }
    });

    refreshPatientsButton.addActionListener(e -> loadPatients.run());
    refreshButton.addActionListener(e -> loadAppointments.run());

    JScrollPane bodyScroll = new JScrollPane(body);
    bodyScroll.setBorder(null);
    bodyScroll.setOpaque(false);
    bodyScroll.getViewport().setOpaque(false);
    bodyScroll.getVerticalScrollBar().setUnitIncrement(16);

    page.add(heading, BorderLayout.NORTH);
    page.add(bodyScroll, BorderLayout.CENTER);

    loadPatients.run();
    loadAppointments.run();

    return page;
}




}

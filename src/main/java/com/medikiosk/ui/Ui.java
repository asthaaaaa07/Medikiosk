
package com.medikiosk.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public final class Ui {

    private Ui() {
    }

    public static JLabel title(String text) {
        JLabel label = new JLabel(text);
        label.setFont(Theme.font(Font.BOLD, 27));
        label.setForeground(Theme.TEXT);
        return label;
    }

    public static JLabel subtitle(String text) {
        JLabel label = new JLabel(text);
        label.setFont(Theme.font(Font.PLAIN, 14));
        label.setForeground(Theme.TEXT_MUTED);
        return label;
    }

    public static JLabel label(String text) {
        JLabel label = new JLabel(text);
        label.setFont(Theme.font(Font.BOLD, 13));
        label.setForeground(Theme.TEXT);
        return label;
    }

    public static JPanel card() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Theme.SURFACE);

        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Theme.BORDER, 1),
                new EmptyBorder(20, 20, 20, 20)
        ));

        return panel;
    }

    public static JButton primaryButton(String text) {
        JButton button = new JButton(text);
        button.setFont(Theme.font(Font.BOLD, 13));
        button.setForeground(Theme.SURFACE);

        button.setBackground(Theme.ACCENT);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(new EmptyBorder(10, 16, 10, 16));

        return button;
    }

    public static JButton secondaryButton(String text) {
        JButton button = new JButton(text);
        button.setFont(Theme.font(Font.PLAIN, 13));
        button.setForeground(Theme.TEXT);
        button.setBackground(Theme.SURFACE_ALT);
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Theme.BORDER),
                new EmptyBorder(8, 12, 8, 12)
        ));

        return button;
    }

    public static JTextField textField(int columns) {
        JTextField field = new JTextField(columns);
        field.setFont(Theme.font(Font.PLAIN, 14));
        field.setForeground(Theme.TEXT);
        field.setBackground(Theme.SURFACE);
        field.setCaretColor(Theme.ACCENT);
        field.setPreferredSize(new Dimension(200, 38));

        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Theme.BORDER),
                new EmptyBorder(7, 10, 7, 10)
        ));

        return field;
    }

    public static JTextArea textArea(int rows) {
        JTextArea area = new JTextArea(rows, 20);
        area.setFont(Theme.font(Font.PLAIN, 14));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setForeground(Theme.TEXT);
        area.setBackground(Theme.SURFACE);
        area.setCaretColor(Theme.ACCENT);
        area.setBorder(new EmptyBorder(8, 10, 8, 10));

        return area;
    }

    public static JScrollPane scroll(JTextArea area) {
        JScrollPane pane = new JScrollPane(area);
        pane.setBorder(BorderFactory.createLineBorder(Theme.BORDER));
        pane.getViewport().setBackground(Theme.SURFACE);
        return pane;
    }
}

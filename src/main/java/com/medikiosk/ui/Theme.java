
package com.medikiosk.ui;

import java.awt.Color;
import java.awt.Font;

public final class Theme {

    private Theme() {
        // Utility class: no objects needed.
    }

    // Main palette
    public static final Color SIDEBAR = new Color(64, 35, 51);
    public static final Color SIDEBAR_HOVER = new Color(86, 52, 69);
    public static final Color ACCENT = new Color(94, 54, 73);

    public static final Color BACKGROUND = new Color(146, 149, 121);
    public static final Color SURFACE = new Color(245, 235, 221);
    public static final Color SURFACE_ALT = new Color(235, 226, 208);

    // Text colors
    public static final Color TEXT = new Color(96, 67, 50);
    public static final Color TEXT_MUTED = new Color(111, 91, 77);
    public static final Color SIDEBAR_TEXT = new Color(111, 91, 77);
    public static final Color BORDER = new Color(196, 195, 168);

    // Extra olive shades
    public static final Color OLIVE = new Color(119, 124, 96);
    public static final Color OLIVE_DARK = new Color(91, 99, 72);

    // Status colors
    public static final Color SUCCESS = new Color(65, 105, 76);
    public static final Color WARNING = new Color(169, 112, 48);
    public static final Color DANGER = new Color(155, 62, 62);

    public static Font font(int style, int size) {
        return new Font("SansSerif", style, size);
    }
}

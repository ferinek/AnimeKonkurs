package com.wookie.animecontest.util;

public class StyleUtils {
    private static final String BUTTON_STYLE = "-fx-font-size: 20pt;" +
            "-fx-min-height: 60px;" +
            "-fx-min-width: 60px;" +
            "-fx-background-color: white;" +
            "-fx-border-color: black;" +
            "-fx-text-alignment: center;";

    private String style;

    public StyleUtils() {
        style = BUTTON_STYLE;
    }

    public StyleUtils addBackgroundColor(String color) {
        style = style + String.format("-fx-background-color: %s;", color);
        return this;
    }

    public String build() {
        return style;
    }
}

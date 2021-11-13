package com.comp7082.gogogroceries;

import java.util.stream.Stream;

public enum Category {
    DAIRY,
    MEAT,
    FRUIT,
    VEGETABLE,
    MISCELLANEOUS;

    /**
     * Returns all elements in the Enum as a formatted String[].
     * @return String[]
     */
    public static String[] names() {
        String[] names = Stream.of(Category.values()).map(Category::name).toArray(String[]::new);

        for (int i = 0; i < names.length; i++) {
            names[i] = formatName(names[i]);
        }

        return names;
    }

    private static String formatName(String input) {
        String formatted = input.toLowerCase();
        formatted = formatted.substring(0, 1).toUpperCase() + formatted.substring(1);
        return formatted;
    }
}

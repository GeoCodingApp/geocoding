package de.uni_stuttgart.informatik.sopra.sopraapp.formatters;

public interface CodeFormatter {

    /**
     * Formats a given String of source code if possible.
     * Returns empty String if unable to format.
     *
     * @param code The code to format
     * @return formatted source code
     */
    String format(String code);
}

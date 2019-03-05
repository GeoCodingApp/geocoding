package de.uni_stuttgart.informatik.sopra.sopraapp.formatters;

import com.google.googlejavaformat.java.Formatter;
import com.google.googlejavaformat.java.FormatterException;

class JavaCodeFormatter implements CodeFormatter {

    @Override
    public String format(String code) {

        Formatter formatter = new Formatter();
        try {
            return formatter.formatSource(code);
        } catch (FormatterException e) {
            e.printStackTrace();
        }
        return "";
    }
}

package de.uni_stuttgart.informatik.sopra.sopraapp.formatters;

/**
 * This class serves as dummy formatter if no proper formatter is available.
 * Simply returns code without any formatting action.
 */
public class NullFormatter implements CodeFormatter {
    @Override
    public String format(String code) {
        return code;
    }
}

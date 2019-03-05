package de.uni_stuttgart.informatik.sopra.sopraapp.ui.puzzles.ui.puzzledisplay;

public enum SolutionType {
    GPS, TEXT, QR_CODE;

    public static SolutionType fromOrdinal(int ordinal) {
        SolutionType[] values = SolutionType.values();
        if (ordinal < 0) {
            // throw exception
            throw new IllegalArgumentException();
        }
        if (ordinal <= values.length - 1) {

            return values[ordinal];
        }
        throw new IllegalArgumentException();
    }
}

package saxoncs;

import net.sf.saxon.expr.number.AbstractNumberer;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2014 Saxonica Limited.
// This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
// If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
// This Source Code Form is "Incompatible With Secondary Licenses", as defined by the Mozilla Public License, v. 2.0.
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Numberer class for the Czech language.
 *
 * @author Michael H. Kay, Michal Bric
 */
public class Numberer_cs extends AbstractNumberer {
    // allows changing the suffix for non-date ordinals based on ordinalParam
    private int suffixOffset = 0;
    // Separator between tens and units. Allows customisation: "twenty five",
    // "twenty-five", "twentyfive",
    // or "thirty second", "thirty-second", "thirtysecond".
    private String cardinalSep = "\u00A0";
    // Separator between tens and units. Allows customisation: "thirty second",
    // "thirty-second", "thirtysecond".
    private String ordinalSep = "\u00A0";

    /**
     * Set the separator to be used between tens and units for cardinal numbers.
     * This allows customization of the output, for example "Dvacet Dva",
     * "Dvacet-Dva", or "Dvacetdva". Default is a single space.
     * <p>
     * Currently the only way of calling this is from a subclass, which can be
     * nominated by setting a {@link net.sf.saxon.lib.LocalizerFactory} on the
     * {@link net.sf.saxon.Configuration}
     * </p>
     *
     * @param separator
     *            the separator to be used between tens and units when cardinal
     *            numbers are written as words.
     */
    public void setTensUnitsSeparatorCardinal(final String separator) {
        cardinalSep = separator;
    }

    /**
     * Set the separator to be used between tens and units for ordinal numbers.
     * This allows customization of the output, for example
     * "Dvac\u00e1t\u00fd Prvn\u00ed", "Dvac\u00e1t\u00fd-Prvn\u00ed", or
     * "Dvac\u00e1t\u00fdPrvn\u00ed". Default is a non breaking space.
     * <p>
     * Currently the only way of calling this is from a subclass, which can be
     * nominated by setting a {@link net.sf.saxon.lib.LocalizerFactory} on the
     * {@link net.sf.saxon.Configuration}
     * </p>
     *
     * @param separator
     *            the separator to be used between tens and units when ordinal
     *            numbers are written as words.
     */
    public void setTensUnitsSeparatorOrdinal(final String separator) {
        ordinalSep = separator;
    }

    /**
     * Construct the ordinal suffix for a number, for example "st", "nd", "rd"
     *
     * @param ordinalParam
     *            the value of the ordinal attribute (used in non-English
     *            language implementations)
     * @param number
     *            the number being formatted
     * @return the ordinal suffix to be appended to the formatted number
     */
    @Override
    protected String ordinalSuffix(final String ordinalParam, final long number) {
        return ".";
    }

    /**
     * Format a number as Czech words with specified case options
     *
     * @param number
     *            the number to be formatted
     * @param wordCase
     *            the required case for example {@link #UPPER_CASE},
     *            {@link #LOWER_CASE}, {@link #TITLE_CASE}
     * @return the formatted number
     */
    @Override
    public String toWords(final long number, final int wordCase) {
        String str = toWords(number);
        if (wordCase == UPPER_CASE) {
            return str.toUpperCase();
        } else if (wordCase == LOWER_CASE) {
            return str.toLowerCase();
        } else {
            return str;
        }
    }

    /**
     * Show the number as words in title case. (We choose title case because the
     * result can then be converted algorithmically to lower case or upper
     * case).
     *
     * @param number
     *            the number to be formatted
     * @return the number formatted as Czech words
     */
    @Override
    public String toWords(final long number) {
        return trimmed(toCardinalWords(number));
    }

    // This method delegates the number with appropriate divisor and initial
    // string
    private String toCardinalWords(final long number) {
        if (number >= 1000000000) {
            return handleCardinalNumber(number, 1000000000, "Miliard");
        } else if (number >= 1000000) {
            return handleCardinalNumber(number, 1000000, "Milion");
        } else if (number >= 1000) {
            return handleCardinalNumber(number, 1000, "Tis\u00EDc");
        } else if (number >= 100) {
            return handleCardinalNumber(number, 100, "");
        } else {
            if (number < 20)
                return cardinalSep + czechUnits[(int) number];
            int rem = (int) (number % 10);
            return cardinalSep + czechTens[(int) number / 10] + cardinalSep
                    + czechUnits[rem];
        }
    }

    // Return correct words with appropriate suffixes given the number, divisor
    // and initial string
    private String handleCardinalNumber(final long number, final int divisor,
            final String str) {
        long rem = number % divisor;
        long cnt = number / divisor;
        String result = cardinalSep + str;
        if (divisor == 1000000000) {
            if (cnt == 1) {
                result += "a";
            } else if (cnt < 5) {
                result += "y";
            }
        } else if (divisor == 1000000) {
            if (cnt < 5 && cnt > 1) {
                result += "y";
            } else {
                result += "\u016F";
            }
        } else if (divisor == 1000) {
            if (cnt < 5 && cnt > 1) {
                result += "e";
            }
        } else { // hundreds require some special handling which is why they
                 // return on their own
            if (cnt == 1) {
                result += "Sto";
            } else if (cnt == 2) {
                return cardinalSep + "Dv\u011B" + cardinalSep + "St\u011B"
                        + cardinalSep + toCardinalWords(rem);
            } else if (cnt == 3 || cnt == 4) {
                result += cardinalSep + "Sta";
            } else {
                result += cardinalSep + "Set";
            }
        }
        return ((cnt > 1) ? toCardinalWords(cnt) : "") + result
                + toCardinalWords(rem);
    }

    /**
     * Show an ordinal number as Czech words in a requested case (for example,
     * "Dvac\u00e1t\u00fd prvn\u00ed")
     *
     * @param ordinalParam
     *            the value of the "ordinal" attribute as supplied by the user
     * @param number
     *            the number to be formatted
     * @param wordCase
     *            the required case for example {@link #UPPER_CASE},
     *            {@link #LOWER_CASE}, {@link #TITLE_CASE}
     * @return the formatted number
     */
    @Override
    public String toOrdinalWords(final String ordinalParam, final long number,
            final int wordCase) {
        String s;
        if (ordinalParam.equals("-\u017e") || ordinalParam.equals("-s")
                || ordinalParam.equals("-m") || number > 31) {
            setSuffixOffset(ordinalParam);
            s = nonDateOrdinal(number);
        } else {
            s = dateOrdinal(number);
        }
        if (wordCase == UPPER_CASE) {
            s = s.toUpperCase();
        } else if (wordCase == LOWER_CASE) {
            s = s.toLowerCase();
        }
        return trimmed(s);
    }

    // This method sets the offset needed to achieve proper spelling for
    // different grammatical cases
    // such as "druhá" vs. "druhé"
    private void setSuffixOffset(final String ordinalParam) {
        switch (ordinalParam) {
        case "-\u017e":
            suffixOffset = 1;
            break;
        case "-s":
            suffixOffset = 2;
            break;
        default:
            suffixOffset = 0;
        }
    }

    // Date ordinal method separated because of the different grammatical case
    // and bounded range
    private String dateOrdinal(final long number) {
        if (number < 20) {
            return ordinalUnitsWithCorrectSuffix(number, true);
        }
        long rem = number % 10;
        long cnt = number / 10;
        return czechOrdinalTens[(int) cnt] + ordinalDateSuffixes[1]
                + ordinalSep + ordinalUnitsWithCorrectSuffix(rem, true);
    }

    // Get regular ordinal numbers for when any ordinalParam is used
    private String nonDateOrdinal(final long number) {
        if (number >= 1000000000) {
            return handleOrdinalNumber(number, 1000000000, "Miliardt"
                    + ordinalNumSuffixes[1 + suffixOffset]);
        } else if (number >= 1000000) {
            return handleOrdinalNumber(number, 1000000, "Miliont"
                    + ordinalNumSuffixes[1 + suffixOffset]);
        } else if (number >= 1000) {
            return handleOrdinalNumber(number, 1000, "Tis\u00edc\u00ed");
        } else if (number >= 100) {
            return handleOrdinalNumber(number, 100, "St"
                    + ordinalNumSuffixes[1 + suffixOffset]);
        } else {
            if (number < 20)
                return ordinalUnitsWithCorrectSuffix(number, false);
            int rem = (int) (number % 10);
            return ordinalSep + czechOrdinalTens[(int) number / 10]
                    + ordinalNumSuffixes[1 + suffixOffset] + ordinalSep
                    + ordinalUnitsWithCorrectSuffix(rem, false);
        }
    }

    private String handleOrdinalNumber(final long number, final int divisor,
            final String str) {
        long rem = number % divisor;
        long cnt = number / divisor;
        if (cnt < 20) {
            return ordinalSep + czechOrdinalLeadingUnits[(int) cnt]
                    + ((cnt > 1) ? str.toLowerCase() : str) + ordinalSep
                    + nonDateOrdinal(rem);
        } else {
            return ordinalSep + nonDateOrdinal(cnt) + ordinalSep + str
                    + ordinalSep + nonDateOrdinal(rem);
        }
    }

    // Returns a simple name for number < 20 with with the corresponding
    // gramatical case suffix
    private String ordinalUnitsWithCorrectSuffix(final long number,
            final boolean date) {
        if (number == 0)
            return "";
        String[] suffixes = (date) ? ordinalDateSuffixes : ordinalNumSuffixes;
        String s = czechOrdinalUnits[(int) number];
        if (number == 1 || number == 3) {
            s += suffixes[0];
        } else {
            s += suffixes[1 + suffixOffset];
        }
        return s;
    }

    /**
     * Get a month name or abbreviation
     *
     * @param month
     *            The month number (1=leden, 12=prosinec)
     * @param minWidth
     *            The minimum number of characters
     * @param maxWidth
     *            The maximum number of characters
     */
    @Override
    public String monthName(final int month, final int minWidth,
            final int maxWidth) {
        String name = czechMonths[month];
        if (maxWidth < 8) {
            name = czechMonthAbbreviations[month];
        }
        return minWidthAdjusted(name, minWidth);
    }

    /**
     * Get a day name or abbreviation
     *
     * @param day
     *            The day of the week (1=pond\u011Bl\u00ED, 7=ned\u011Ble)
     * @param minWidth
     *            The minimum number of characters
     * @param maxWidth
     *            The maximum number of characters
     */
    @Override
    public String dayName(final int day, final int minWidth, final int maxWidth) {
        String name = czechDays[day];
        if (maxWidth < 7) {
            name = name.substring(0, 2).toLowerCase();
        }
        return minWidthAdjusted(name, minWidth);
    }

    /**
     * Get Czech version of am/pm based on minute of the day
     *
     * @param minutes
     *            the minutes within the day
     * @param minWidth
     *            minimum width of output
     * @param maxWidth
     *            maximum width of output
     * @return the 'odpoledne' or 'dopoledne' indicator
     */
    @Override
    public String halfDayName(final int minutes, final int minWidth,
            final int maxWidth) {
        String str;
        if (minutes < 12 * 60) {
            str = (maxWidth < 9) ? "dop." : "dopoledne";
        } else {
            str = (maxWidth < 9) ? "odp." : "odppoledne";
        }
        return minWidthAdjusted(str, minWidth);
    }

    /**
     * Get the name fo ran era ('pred nasim letopoctem' or 'naseho letopoctu')
     *
     * @param year
     *            gregorian year
     */
    @Override
    public String getEraName(final int year) {
        return (year < 0) ? "p\u0159. n. l." : "n. l.";
    }

    /**
     * Get the name of a calendar
     *
     * @param code
     *            The code representing the calendar as in the XSLT 2.0 spec
     */
    @Override
    public String getCalendarName(final String code) {
        return code.equals("AD") ? "Gregori\u00e1nsk\u00fd" : code;
    }

    // Returns a string with the length greater or equal to minWidth
    private String minWidthAdjusted(final String str, final int minWidth) {
        if (str.length() >= minWidth) {
            return str;
        }
        return minWidthAdjusted(str + " ", minWidth);
    }

    // Trim method to remove the trailing / duplicate white space separators
    private String trimmed(final String str) {
        String trimmed = "";
        for (int i = 0; i < str.length(); i++) {
            char letter = str.charAt(i);
            if (i == 0 || i == str.length()) {
                if (isSepChar(letter)) {
                    continue;
                }
            } else if (isSepChar(letter) && isSepChar(str.charAt(i - 1))) {
                continue;
            }
            trimmed += letter;
        }
        return trimmed;
    }

    private boolean isSepChar(final char c) {
        return c == ordinalSep.charAt(0) || c == cardinalSep.charAt(0);
    }

    private final String[] czechUnits = { "", "Jedna", "Dva", "T\u0159i",
            "\u010Cty\u0159i", "P\u011Bt", "\u0160est", "Sedm", "Osm",
            "Dev\u011Bt", "Deset", "Jeden\u00E1ct", "Dvan\u00E1ct",
            "T\u0159in\u00E1ct", "\u010Ctrn\u00E1ct", "Patn\u00E1ct",
            "\u0160estn\u00E1ct", "Sedmn\u00E1ct", "Osmn\u00E1ct",
            "Devaten\u00E1ct" };
    private final String[] czechTens = { "", "Deset", "Dvacet", "T\u0159icet",
            "\u010Cty\u0159icet", "Pades\u00E1t", "\u0160edes\u00E1t",
            "Sedmdes\u00E1t", "Osmdes\u00E1t", "Devades\u00E1t" };
    private final String[] czechOrdinalUnits = { "", "Prvn", "Druh",
            "T\u0159et", "\u010Ctvrt", "P\u00E1t", "\u0160est", "Sedm", "Osm",
            "Dev\u00E1t", "Des\u00E1t", "Jeden\u00E1ct", "Dvan\u00E1ct",
            "T\u0159in\u00E1ct", "\u010Ctrn\u00E1ct", "Patn\u00E1ct",
            "\u0160estn\u00E1ct", "Sedmn\u00E1ct", "Osmn\u00E1ct",
            "Devaten\u00E1ct" };
    private final String[] czechOrdinalTens = { "", "Des\u00E1t",
            "Dvac\u00E1t", "T\u0159ic\u00E1t", "\u010Cty\u0159ic\u00E1t",
            "Pades\u00E1t", "\u0160edes\u00E1t", "Sedmdes\u00E1t",
            "Osmdes\u00E1t", "Devades\u00E1t" };
    private final String[] czechOrdinalLeadingUnits = { "", "", "Dvou",
            "T\u0159\u00ed", "\u010Cty\u0159", "P\u011Bti", "\u0160esti",
            "Sedmi", "Osmi", "Dev\u00edti", "Des\u00e1ti", "Jeden\u00e1cti",
            "Dvan\u00e1cti", "T\u0159in\u00e1cti", "\u010Ctrn\u00e1cti",
            "Patn\u00e1cti", "\u0160estn\u00e1cti", "Sedmn\u00e1cti",
            "Osmn\u00e1cti", "Devaten\u00e1cti" };
    private final String[] czechMonths = { "", "ledna", "\u00FAnora",
            "b\u0159ezna", "dubna", "kv\u011Btna", "\u010Dervna",
            "\u010Dervence", "srpna", "z\u00E1\u0159\u00ED", "\u0159\u00EDjna",
            "listopadu", "prosince" };
    private final String[] czechMonthAbbreviations = { "", "Led", "\u00dano",
            "B\u0159e", "Dub", "Kv\u011B", "\u010cvn", "\u010cvc", "Srp",
            "Z\u00E1\u0159", "\u0158\u00EDj", "Lis", "Pro" };
    private final String[] czechDays = { "", "pond\u011Bl\u00ED",
            "\u00FAter\u00FD", "st\u0159eda", "\u010Dtvrtek", "p\u00E1tek",
            "sobota", "ned\u011Ble" };
    private final String[] ordinalDateSuffixes = { "\u00EDho", "\u00e9ho" };
    private final String[] ordinalNumSuffixes = { "\u00ED", "\u00FD", "\u00e1",
            "\u00e9" };
}
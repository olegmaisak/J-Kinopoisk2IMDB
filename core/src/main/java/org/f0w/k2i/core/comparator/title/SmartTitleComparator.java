package org.f0w.k2i.core.comparator.title;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.f0w.k2i.core.comparator.AbstractMovieComparator;
import org.f0w.k2i.core.model.entity.Movie;
import org.f0w.k2i.core.util.string.NumericToWord;
import org.f0w.k2i.core.util.string.Translit;

import java.util.ArrayList;
import java.util.List;

/**
 * Uses multiple unique algorithms for movie title comparison.
 */
public final class SmartTitleComparator extends AbstractMovieComparator {
    private static final List<StringModifier> modifiers;

    static {
        List<StringModifier> list = new ArrayList<>();

        // Original string
        list.add(s -> s);

        // Original string without commas
        list.add(s -> s.replaceAll(",", ""));

        // Original string without colon
        list.add(s -> s.replaceAll(":", ""));

        // Original string without apostrophes
        list.add(s -> s.replaceAll("/([\'\\\\x{0027}]|&#39;|&#x27;)/u", ""));

        // Original string with unescaped XML symbols and removed foreign accents
        list.add(s -> StringUtils.stripAccents(StringEscapeUtils.unescapeXml(s)));

        // Original string without special symbols like unicode etc
        list.add(s -> s.replaceAll("/\\\\u([0-9a-z]{4})/", ""));

        // Original string with part before dash symbol
        list.add(s -> {
            String[] parts = s.split("-");

            return parts[0].trim();
        });

        // Original string with part after dash symbol
        list.add(s -> {
            String[] parts = s.split("-");

            return parts.length > 0 ? parts[parts.length - 1].trim() : "";
        });

        // The + Original string
        list.add(s -> "The " + s);

        // Original string with all whitespace characters replaced with plain backspace
        list.add(s -> s.replaceAll("/\\s+/", " "));

        // Original string with XML symbols replaced with backspace
        list.add(s -> {
            String[] symbols = {"&#xB;", "&#xC;", "&#x1A;", "&#x1B;"};

            for (String symbol : symbols) {
                s = s.replaceAll(symbol, " ");
            }

            return s;
        });

        // Transliterated string
        list.add(Translit::toTranslit);

        // Weakly transliterated string
        list.add(Translit::toWeakerTranslit);

        // Weakly transliterated with lower case and capitalized
        list.add(s -> Translit.toWeakerTranslit(StringUtils.capitalize(s.toLowerCase())));

        // Original string with numeric replaced to text representation
        list.add(s -> {
            String[] words = s.split(" ");

            for (int i = 0; i < words.length; i++) {
                try {
                    words[i] = NumericToWord.convert(Integer.parseInt(words[i]));
                } catch (NumberFormatException ignored) {
                    // Ignore
                }
            }

            return String.join(" ", words);
        });

        // Modifiers using symbols mix
        String[] symbolsMix = {"&", "and", "et"};
        for (final String firstSymbol : symbolsMix) {
            for (final String secondSymbol : symbolsMix) {
                if (firstSymbol.equals(secondSymbol)) {
                    continue;
                }

                list.add(s -> s.replaceAll(firstSymbol, secondSymbol));
            }
        }

        modifiers = ImmutableList.copyOf(list);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean areEqual(Movie movie1, Movie movie2) {
        for (StringModifier m1 : modifiers) {
            for (StringModifier m2 : modifiers) {
                String m1Title = m1.modify(movie1.getTitle());
                String m2Title = m2.modify(movie2.getTitle());

                LOG.debug(
                        "Comparing title '{}' with title '{}', matches = '{}'",
                        m1Title,
                        m2Title,
                        m1Title.equals(m2Title)
                );

                if (m1Title.equals(m2Title)) {
                    return true;
                }
            }
        }

        return false;
    }

    @FunctionalInterface
    interface StringModifier {
        String modify(String string);
    }
}
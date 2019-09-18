package ru.kirillkot.telegram.bot.service;

import java.util.Locale;
import java.util.MissingResourceException;

public class LocalizationService {
    private static final String STRINGS_FILE = "commands";
    private static final Object lock = new Object();

    private static final Utf8ResourceBundle defaultLanguage;

    static {
        synchronized (lock) {
            defaultLanguage = new Utf8ResourceBundle(STRINGS_FILE, Locale.ROOT);
        }
    }

    /**
     * Get a string in default language (en)
     *
     * @param key key of the resource to fetch
     * @return fetched string or error message otherwise
     */
    public String getString(String key) {
        String result;
        try {
            result = defaultLanguage.getString(key);
        } catch (MissingResourceException e) {
            result = "String not found";
        }

        return result;
    }


}

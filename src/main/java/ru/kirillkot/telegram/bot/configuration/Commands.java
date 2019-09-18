package ru.kirillkot.telegram.bot.configuration;

import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@UtilityClass
public final class Commands {

    public static final String commandInitChar = "/";
    public static final String HELP = commandInitChar + "help";
    public static final String WEATHER = commandInitChar + "weather";
    public static final String CURRENCIES = commandInitChar + "currencies";
    public static final String FAGGOT = commandInitChar + "faggot";
    public static final String BEST_MEMBER = commandInitChar + "best_member";
    public static final String MAN = commandInitChar + "man";

    public static List<String> commands = Collections.unmodifiableList(
            Arrays.asList(HELP, WEATHER, CURRENCIES, FAGGOT, BEST_MEMBER, MAN));
}


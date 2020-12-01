package com.dungeonhunters.dungeonhunters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Usage:
 * <li>String msg = Ansi.Red.and(Ansi.BgYellow).format("Hello %s", name)</li>
 * <li>String msg = Ansi.Blink.colorize("BOOM!")</li>
 *
 * Or, if you are adverse to that, you can use the constants directly:
 * <li>String msg = new Ansi(Ansi.ITALIC, Ansi.GREEN).format("Green money")</li>
 * Or, even:
 * <li>String msg = Ansi.BLUE + "scientific"</li>
 *
 * NOTE: Nothing stops you from combining multiple FG colors or BG colors,
 *       but only the last one will display.
 *
 * @author dain
 *
 */
public final class Ansi {

    // Color code strings from:
    // http://www.topmudsites.com/forums/mud-coding/413-java-ansi.html
    public static final String	SANE				= "\u001B[0m";

    public static final String	HIGH_INTENSITY		= "\u001B[1m";
    public static final String	LOW_INTENSITY		= "\u001B[2m";

    public static final String	ITALIC				= "\u001B[3m";
    public static final String	UNDERLINE			= "\u001B[4m";
    public static final String	BLINK				= "\u001B[5m";
    public static final String	RAPID_BLINK			= "\u001B[6m";
    public static final String	REVERSE_VIDEO		= "\u001B[7m";
    public static final String	INVISIBLE_TEXT		= "\u001B[8m";

    public static final String	BLACK				= "\u001B[30m";
    public static final String	RED					= "\u001B[31m";
    public static final String	GREEN				= "\u001B[32m";
    public static final String	YELLOW				= "\u001B[33m";
    public static final String	BLUE				= "\u001B[34m";
    public static final String	MAGENTA				= "\u001B[35m";
    public static final String	CYAN				= "\u001B[36m";
    public static final String	WHITE				= "\u001B[37m";

    public static final String	BACKGROUND_BLACK	= "\u001B[40m";
    public static final String	BACKGROUND_RED		= "\u001B[41m";
    public static final String	BACKGROUND_GREEN	= "\u001B[42m";
    public static final String	BACKGROUND_YELLOW	= "\u001B[43m";
    public static final String	BACKGROUND_BLUE		= "\u001B[44m";
    public static final String	BACKGROUND_MAGENTA	= "\u001B[45m";
    public static final String	BACKGROUND_CYAN		= "\u001B[46m";
    public static final String	BACKGROUND_WHITE	= "\u001B[47m";

    public static final Ansi HighIntensity = new Ansi(HIGH_INTENSITY);
    public static final Ansi Bold = HighIntensity;
    public static final Ansi LowIntensity = new Ansi(LOW_INTENSITY);
    public static final Ansi Normal = LowIntensity;

    public static final Ansi Italic = new Ansi(ITALIC);
    public static final Ansi Underline = new Ansi(UNDERLINE);
    public static final Ansi Blink = new Ansi(BLINK);
    public static final Ansi RapidBlink = new Ansi(RAPID_BLINK);

    public static final Ansi Black = new Ansi(BLACK);
    public static final Ansi Red = new Ansi(RED);
    public static final Ansi Green = new Ansi(GREEN);
    public static final Ansi Yellow = new Ansi(YELLOW);
    public static final Ansi Blue = new Ansi(BLUE);
    public static final Ansi Magenta = new Ansi(MAGENTA);
    public static final Ansi Cyan = new Ansi(CYAN);
    public static final Ansi White = new Ansi(WHITE);

    public static final Ansi BgBlack = new Ansi(BACKGROUND_BLACK);
    public static final Ansi BgRed = new Ansi(BACKGROUND_RED);
    public static final Ansi BgGreen = new Ansi(BACKGROUND_GREEN);
    public static final Ansi BgYellow = new Ansi(BACKGROUND_YELLOW);
    public static final Ansi BgBlue = new Ansi(BACKGROUND_BLUE);
    public static final Ansi BgMagenta = new Ansi(BACKGROUND_MAGENTA);
    public static final Ansi BgCyan = new Ansi(BACKGROUND_CYAN);
    public static final Ansi BgWhite = new Ansi(BACKGROUND_WHITE);

    public static final String cardView =
            "\t\t\t\t\t\t\t\t\t\t\t.----------.\n" +
            "\t\t\t\t\t\t\t\t\t\t\t|4.-----.1 |\n" +
            "\t\t\t\t\t\t\t\t\t\t\t|  ATK:2  |\n" +
            "\t\t\t\t\t\t\t\t\t\t\t|  DEF:3  |\n" +
            "\t\t\t\t\t\t\t\t\t\t\t| '------' |\n" +
            "\t\t\t\t\t\t\t\t\t\t\t`----------'";


    public static final String stiletto =
            " ____________ \n" +
            "|    (__)    | \n" +
            "|     ||     | \n" +
            "|  (______)  | \n" +
            "|    |  |    | \n" +
            "|    |  |    | \n" +
            "|    |  |    | \n" +
            "|     `/     | \n" +
            "|____________| \n";

    public static final String axe =
                    " ____________ \n" +
                    "|            | \n" +
                    "|  ||____/`  | \n" +
                    "|  ||      | | \n" +
                    "|  ||____  | | \n" +
                    "|  ||    `/  | \n" +
                    "|  ||        | \n" +
                    "|  ||        | \n" +
                    "|____________| \n";
    public static final String helmetWooden =
                    " ____________ \n" +
                    "|     xx     | \n" +
                    "|   __xx__   | \n" +
                    "| /        ` | \n" +
                    "| | ()  () | | \n" +
                    "| |        | | \n" +
                    "| | |||||| | | \n" +
                    "|    WOOD    | \n" +
                    "|____________| \n";
    public static final String boots =
                    " ____________ \n" +
                    "| ___        | \n" +
                    "|| -o|       | \n" +
                    "|| -o|       | \n" +
                    "|| -o|______ | \n" +
                    "||    ```   || \n" +
                    "||ww--wwwwww|| \n" +
                    "|            | \n" +
                    "|____________| \n";
    public static final String charm1Amulet =
                    " ____________ \n" +
                    "|o          o| \n" +
                    "| o        o | \n" +
                    "|  o      o  | \n" +
                    "|   o    o   | \n" +
                    "|    $$$$    | \n" +
                    "|  $$$$$$$$  | \n" +
                    "|     $$     | \n" +
                    "|____________| \n";
    public static final String charm2Lightning =
                    " ____________ \n" +
                    "|     ////// | \n" +
                    "|   //////   | \n" +
                    "| //////     | \n" +
                    "|////////////| \n" +
                    "|      ////  | \n" +
                    "|    ////    | \n" +
                    "|  ////      | \n" +
                    "|_//_________| \n";
    public static final String charm3 =
                    " ____________ \n" +
                    "|            | \n" +
                    "|            | \n" +
                    "|    (**)     | \n" +
                    "|   o    o   | \n" +
                    "|  o      o  | \n" +
                    "|   o    o   | \n" +
                    "|            | \n" +
                    "|____________| \n";

    public static final String swordWooden =
                    " ____________ \n" +
                    "|     ||     | \n" +
                    "|     ||     | \n" +
                    "|     ||     | \n" +
                    "|     ||     | \n" +
                    "|  (______)  | \n" +
                    "|     ()     | \n" +
                    "|    WOOD    | \n" +
                    "|____________| \n";
    public static final String swordSteel =
                    " ____________ \n" +
                    "|     ||     | \n" +
                    "|     ||     | \n" +
                    "|     ||     | \n" +
                    "|     ||     | \n" +
                    "|  (______)  | \n" +
                    "|     ()     | \n" +
                    "|    STEEL   | \n" +
                    "|____________| \n";
    public static final String swordSilver =
                    " ____________ \n" +
                    "|     ||     | \n" +
                    "|     ||     | \n" +
                    "|     ||     | \n" +
                    "|     ||     | \n" +
                    "|  (______)  | \n" +
                    "|     ()     | \n" +
                    "|   SILVER   | \n" +
                    "|____________| \n";
    public static final String helmetSteel =
            " ____________ \n" +
                    "|     xx     | \n" +
                    "|   __xx__   | \n" +
                    "| /        ` | \n" +
                    "| | ()  () | | \n" +
                    "| |        | | \n" +
                    "| | |||||| | | \n" +
                    "|   STEEL    | \n" +
                    "|____________| \n";
    public static final String helmetSilver =
            " ____________ \n" +
                    "|     xx     | \n" +
                    "|   __xx__   | \n" +
                    "| /        ` | \n" +
                    "| | ()  () | | \n" +
                    "| |        | | \n" +
                    "| | |||||| | | \n" +
                    "|   SILVER   | \n" +
                    "|____________| \n";
    public static final String shield =
                    " ____________ \n" +
                    "|   ______   | \n" +
                    "| |      x.| | \n" +
                    "| |     x..| | \n" +
                    "| |    x...| | \n" +
                    "| |   x....| | \n" +
                    "|    x..../  | \n" +
                    "|     ../    | \n" +
                    "|____________| \n";
    public static final String armorWooden =
                    " ____________ \n" +
                    "| __________ | \n" +
                    "||          || \n" +
                    "|| |      | || \n" +
                    "||_|      |_|| \n" +
                    "|  |______|  | \n" +
                    "|            | \n" +
                    "|    WOOD    | \n" +
                    "|____________| \n";
    public static final String armorSteel =
                    " ____________ \n" +
                    "| __________ | \n" +
                    "||          || \n" +
                    "|| |      | || \n" +
                    "||_|      |_|| \n" +
                    "|  |______|  | \n" +
                    "|            | \n" +
                    "|    STEEL   | \n" +
                    "|____________| \n";
    public static final String armorSilver =
                    " ____________ \n" +
                    "| __________ | \n" +
                    "||          || \n" +
                    "|| |      | || \n" +
                    "||_|      |_|| \n" +
                    "|  |______|  | \n" +
                    "|            | \n" +
                    "|   SILVER   | \n" +
                    "|____________| \n";

    public static final String pantsWooden =
                    " ____________ \n" +
                    "|  |--[]--|  | \n" +
                    "|  |      |  |\n" +
                    "|  |  ||  |  | \n" +
                    "|  |()||()|  | \n" +
                    "|  |__||__|  | \n" +
                    "|            | \n" +
                    "|    WOOD    | \n" +
                    "|____________| \n";
    public static final String pantsSteel =
                    " ____________ \n" +
                    "|  |--[]--|  | \n" +
                    "|  |      |  |\n" +
                    "|  |  ||  |  | \n" +
                    "|  |()||()|  | \n" +
                    "|  |__||__|  | \n" +
                    "|            | \n" +
                    "|    STEEL   | \n" +
                    "|____________| \n";
    public static final String pantsSilver =
                    " ____________ \n" +
                    "|  |--[]--|  | \n" +
                    "|  |      |  |\n" +
                    "|  |  ||  |  | \n" +
                    "|  |()||()|  | \n" +
                    "|  |__||__|  | \n" +
                    "|            | \n" +
                    "|   SILVER   | \n" +
                    "|____________| \n";


    public static final String playerFight =
                    "(●̮̮̃•̃)\n" +
                    "/█ \\()_)___)\n" +
                    ".Π.\n";

    public static final String opponentFight =
                    "▄──────▄\n" +
                    "█▀█▀█\n" +
                    "█▄█▄█\n" +
                    "──███───▄▄\n" +
                    "──████▐█─█\n" +
                    "──████─────█\n" +
                    "──▀▀▀▀▀▀▀\n";

    public static final String player =
            "||||||||||||||||||||||||\n"+
            "||                    ||\n"+
            "||       ()(()        ||\n"+
            "||     ((()()())      ||\n"+
            "||     | O   O |      ||\n"+
            "||    (| * u * |)     ||\n"+
            "||     |_______|      ||\n"+
            "||    ____| |____     ||\n"+
            "||   |           |    ||\n"+
            "||   |           |    ||\n"+
            "||||||||||||||||||||||||";
    public static final String enemy =
            "||||||||||||||||||||||||\n"+
            "||                    ||\n"+
            "||    ((________))    ||\n"+
            "||    /  v   v   |    ||\n"+
            "||   /   o   o   |    ||\n"+
            "||  *_______     |    ||\n"+
            "||   _|    __    |__  ||\n"+
            "||  |    vvvvvv     | ||\n"+
            "||  |     vvvv      | ||\n"+
            "||  | |    vv     | | ||\n"+
            "||||||||||||||||||||||||\n"+
            "||##enemy_health_bar##||\n"+
            "||||||||||||||||||||||||";
    public static final String gamePanel =
        " ________  ________  _____ ______   _______           ________  ________  ________   _______   ___          \n" +
        "|\\   ____\\|\\   __  \\|\\   _ \\  _   \\|\\  ___ \\         |\\   __  \\|\\   __  \\|\\   ___  \\|\\  ___ \\ |\\  \\         \n" +
        "\\ \\  \\___|\\ \\  \\|\\  \\ \\  \\\\\\__\\ \\  \\ \\   __/|        \\ \\  \\|\\  \\ \\  \\|\\  \\ \\  \\\\ \\  \\ \\   __/|\\ \\  \\        \n" +
        " \\ \\  \\  __\\ \\   __  \\ \\  \\\\|__| \\  \\ \\  \\_|/__       \\ \\   ____\\ \\   __  \\ \\  \\\\ \\  \\ \\  \\_|/_\\ \\  \\       \n" +
        "  \\ \\  \\|\\  \\ \\  \\ \\  \\ \\  \\    \\ \\  \\ \\  \\_|\\ \\       \\ \\  \\___|\\ \\  \\ \\  \\ \\  \\\\ \\  \\ \\  \\_|\\ \\ \\  \\____  \n" +
        "   \\ \\_______\\ \\__\\ \\__\\ \\__\\    \\ \\__\\ \\_______\\       \\ \\__\\    \\ \\__\\ \\__\\ \\__\\\\ \\__\\ \\_______\\ \\_______\\\n" +
        "    \\|_______|\\|__|\\|__|\\|__|     \\|__|\\|_______|        \\|__|     \\|__|\\|__|\\|__| \\|__|\\|_______|\\|_______|\n";


    final private String[] codes;
    final private String codes_str;
    public Ansi(String... codes) {
        this.codes = codes;
        String _codes_str = "";
        for (String code : codes) {
            _codes_str += code;
        }
        codes_str = _codes_str;
    }

    public Ansi and(Ansi other) {
        List<String> both = new ArrayList<String>();
        Collections.addAll(both, codes);
        Collections.addAll(both, other.codes);
        return new Ansi(both.toArray(new String[] {}));
    }

    public String colorize(String original) {
        return codes_str + original + SANE;
    }

    public String format(String template, Object... args) {
        return colorize(String.format(template, args));
    }
}

package xy99.core.mybatis.util;


import xy99.core.mybatis.code.Style;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    public StringUtil() {
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static String convertByStyle(String str, Style style) {
        switch(null.$SwitchMap$com$hand$hap$mybatis$code$Style[style.ordinal()]) {
            case 1:
                return camelhumpToUnderline(str);
            case 2:
                return str.toUpperCase();
            case 3:
                return str.toLowerCase();
            case 4:
                return camelhumpToUnderline(str).toLowerCase();
            case 5:
                return camelhumpToUnderline(str).toUpperCase();
            case 6:
            default:
                return str;
        }
    }

    public static String camelhumpToUnderline(String str) {
        if(isEmpty(str)) {
            return str;
        } else {
            int size;
            char[] chars;
            StringBuilder sb = new StringBuilder((size = (chars = str.toCharArray()).length) * 3 / 2 + 1);

            for(int i = 0; i < size; ++i) {
                char c = chars[i];
                if(isUppercaseAlpha(c)) {
                    sb.append('_').append(c);
                } else {
                    sb.append(toUpperAscii(c));
                }
            }

            return sb.charAt(0) == 95?sb.substring(1):sb.toString();
        }
    }

    public static String underlineToCamelhump(String str) {
        Matcher matcher = Pattern.compile("_[a-z]").matcher(str);
        StringBuilder builder = new StringBuilder(str);

        for(int i = 0; matcher.find(); ++i) {
            builder.replace(matcher.start() - i, matcher.end() - i, matcher.group().substring(1).toUpperCase());
        }

        if(Character.isUpperCase(builder.charAt(0))) {
            builder.replace(0, 1, String.valueOf(Character.toLowerCase(builder.charAt(0))));
        }

        return builder.toString();
    }

    public static boolean isUppercaseAlpha(char c) {
        return c >= 65 && c <= 90;
    }

    public static boolean isLowercaseAlpha(char c) {
        return c >= 97 && c <= 122;
    }

    public static char toUpperAscii(char c) {
        if(isLowercaseAlpha(c)) {
            c = (char)(c - 32);
        }

        return c;
    }

    public static char toLowerAscii(char c) {
        if(isUppercaseAlpha(c)) {
            c = (char)(c + 32);
        }

        return c;
    }
}

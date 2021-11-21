package io.lana.simplespring.lib.utils;

public class CaseUtils {
    private CaseUtils() {
    }

    public static String toSnakeCase(String camelCase) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Character.toLowerCase(camelCase.charAt(0)));

        for (int i = 1; i < camelCase.length(); i++) {
            char c = camelCase.charAt(i);
            char nc = Character.toLowerCase(c);
            if (Character.isUpperCase(c)) {
                stringBuilder.append('_').append(nc);
            } else {
                stringBuilder.append(nc);
            }
        }

        return stringBuilder.toString();
    }
}

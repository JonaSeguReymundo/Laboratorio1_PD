package org.example;

public class MathFormatter {

    public static String format(String expression) {
        if (expression == null || expression.isBlank()) {
            return "";
        }

        return formatExpression(expression.trim());
    }

    private static String formatExpression(String expression) {
        expression = expression.trim();

        if (expression.isEmpty()) {
            return expression;
        }

        if (isWrapped(expression)) {
            return formatExpression(
                    expression.substring(1, expression.length() - 1)
            );
        }

        String[] operators = {"'-'(", "'+'(", "'/'(", "'*'(", "'^'("};

        for (String operator : operators) {
            if (expression.startsWith(operator)
                    && expression.endsWith(")")) {

                String content = expression.substring(
                        operator.length(),
                        expression.length() - 1
                );

                int comma = findTopLevelComma(content);

                if (comma < 0) {
                    continue;
                }

                String left = formatExpression(
                        content.substring(0, comma)
                );

                String right = formatExpression(
                        content.substring(comma + 1)
                );

                switch (operator) {
                    case "'+'(":
                        return left + " + " + right;

                    case "'-'(":
                        return left + " - " + right;

                    case "'/'(":
                        if (left.equals("1")) {
                            return "1/" + right;
                        }
                        return "(" + left + ")/(" + right + ")";

                    case "'*'(":
                        if (left.equals("1")) {
                            return right;
                        }
                        if (right.equals("1")) {
                            return left;
                        }
                        if (left.equals("0") || right.equals("0")) {
                            return "0";
                        }
                        return left + " * " + right;

                    case "'^'(":
                        return left + "^" + right;
                }
            }
        }

        return expression;
    }

    private static int findTopLevelComma(String expression) {
        int depth = 0;

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (c == '(') {
                depth++;
            } else if (c == ')') {
                depth--;
            } else if (c == ',' && depth == 0) {
                return i;
            }
        }

        return -1;
    }

    private static boolean isWrapped(String expression) {
        if (!expression.startsWith("(")
                || !expression.endsWith(")")) {
            return false;
        }

        int depth = 0;

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (c == '(') {
                depth++;
            } else if (c == ')') {
                depth--;

                if (depth == 0 && i < expression.length() - 1) {
                    return false;
                }
            }
        }

        return depth == 0;
    }
}
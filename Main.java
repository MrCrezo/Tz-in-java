import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println(
                """
                        Введите 2 значения в двойных кавычках для строк!
                        При делении и умножении второе значение обязательно цифра!
                        Между значениями один из знаков операции /, *, -, + пробел перед знаком и после!
                        """
        );
        String exp = sc.nextLine();
        char action;
        String[] data;

        if (exp.contains("+")) {
            data = exp.split(" \\+ ");
            action = '+';
        } else if (exp.contains("-")) {
            data = exp.split(" - ", 2);
            action = '-';
        } else if (exp.contains("*")) {
            data = exp.split(" \\* ");
            action = '*';
        } else if (exp.contains("/")) {
            data = exp.split(" / ");
            action = '/';

         }else {
            throw new Exception("Некорректный знак действия");
        }


        if (action == '+' || action == '-') {
            for (String datum : data) {
                if (!datum.startsWith("\"") || !datum.endsWith("\"")) {
                    if (datum.equals(String.valueOf(action))) {
                        continue;
                    }
                    throw new Exception("Значения должны быть в двойных кавычках");
                }
            }
        }

        if (action == '*' || action == '/') {

            if (!data[0].startsWith("\"") || !data[0].endsWith("\"")) {
                throw new Exception("Значения должны быть в двойных кавычках");
            }
            if (data[1].contains("\"")) {
                throw new Exception("Строку можно делить или умножать только на число");
            }
            if (data[1].equals("0")) {
                throw new ArithmeticException("Деление на ноль невозможно");
            }
            try {
                Integer.parseInt(data[1]);
            } catch (NumberFormatException e) {
                throw new Exception("Второе значение должно быть числом");
            }
        }

        for (int i = 0; i < data.length; i++) {
            data[i] = data[i].replace("\"", "");
        }
        switch (action) {
            case '+':
                addition(data);
                break;
            case '-':
                subtraction(data);
                break;
            case '*':
                multiplication(data);
                break;
            case '/':
                division(data);
                break;
        }
    }
    static void addition(String[] data) {
        printInQuotes(data[0] + data[1]);
    }
    static void subtraction(String[] data) {
        for (String datum : data) {
            if (!datum.equals(data[1])) {
                int index = datum.indexOf(data[1]);
                if (index == -1) {
                    printInQuotes(data[0]);
                } else {
                    String result = data[0].substring(0, index);
                    result += data[0].substring(index + data[1].length());
                    printInQuotes(result);
                }
            }
        }
    }
    static void multiplication(String[] data) throws Exception {
        int value = Integer.parseInt(data[1]);
        if (value == 0 || (value > 10)) {
            throw new Exception("Число может быть только от 1 до 10");
        }
        int multiplier = Integer.parseInt(data[1]);
        String result = "";
        for (int i = 0; i < multiplier; i++) {
            result += data[0];
        }
        printInQuotes(result);
    }
    static void division(String[] data) throws Exception {
        int value = Integer.parseInt(data[1]);
        if (value == 0 || (value > 10)) {
            throw new Exception("Число может быть только от 1 до 10");
        }
        int newLen = data[0].length() / Integer.parseInt(data[1]);
        String result = data[0].substring(0, newLen);
        printInQuotes(result);
    }

    static void printInQuotes(String text) {
        if (text.length() > 40) {
            String result = text.substring(0, 40);
            System.out.println(result + "...");
        } else {
            System.out.println("\"" + text + "\"");
        }
    }
}

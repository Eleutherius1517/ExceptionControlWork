import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class App {
    public static String surname = "";

    public static void main(String[] args) throws Exception {
        String choice = prompt("1. Чтобы ввести данные пользователя\n2. Для выхода из приложения введите 2");
        while (!"2".equals(choice)) {
            String userData = "";
            boolean appendWrite = false;
            userData = prompt("Здрвыствуйте! Введите ваши данные в указанной последовательности через пробел: <Фамилия> <Имя> <Отчество> <дата _ рождения в формате формата dd.mm.yyyy> <номер _ телефона> <пол>");
            String[] splitUserData = parseData(userData);
            String finalData = splitUserData[0];
            for (int i = 1; i < splitUserData.length; i++) {
                finalData += " " + splitUserData[i];
            }
            surname += " " + splitUserData[0];
            
            if (surname.contains(splitUserData[0])) {
                appendWrite = true;
            } else {
                appendWrite = false;
            }
            writeInFile(splitUserData[0] + ".txt", appendWrite, finalData);
            choice = prompt("1. Чтобы ввести данные пользователя\n2. Для выхода из приложения введите 2");
        }

    }

    public static String prompt(String message) {
        Scanner scan = new Scanner(System.in);
        System.out.println(message);
        return scan.nextLine();
    }

    public static void writeInFile(String path, boolean flag, String dataUser) {
        try (FileWriter fileWriter = new FileWriter(path, flag);) {
            fileWriter.write(dataUser + "\n");
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String[] parseData(String dataUser){
        String[] splitUserData  = dataUser.split(" ");
        if (splitUserData.length < 6) {
            throw new DataLengthException("Вы ввели МЕНЬШЕ данных чем необходимо!");
        }
        if (splitUserData.length > 6) {
            throw new DataLengthException("Вы ввели БОЛЬШЕ данных чем необходимо!");
        }
        if (!Pattern.matches("[a-zA-Z]+", splitUserData[0]) || !Pattern.matches("[a-zA-Z]+", splitUserData[1]) || !Pattern.matches("[a-zA-Z]+", splitUserData[2])) {
            throw new NamingException("Ошибка!!!! ФИО нужно вводить только буквами!");
        }
        String[] splitCalendarData = splitUserData[3].split("\\.");
        if (splitCalendarData.length != 3) {
            throw new CalendarDataException("Ошибка!!!! Дату рождения нужно указать тольков формате dd.mm.yyyy!");
        }
        if (splitCalendarData[0].length() != 2 || splitCalendarData[1].length() != 2 || splitCalendarData[2].length() != 4) {
            throw new CalendarDataException("Ошибка!!!! Дату рождения нужно указать тольков формате dd.mm.yyyy!");
        }
        if (!splitUserData[4].matches("-?\\d+")) {
            throw new PhoneNumberException("Ошибка!!!! Телефон нужно указать только целым числом без ворматирования!");
        }
        if (splitUserData[5] != "f" || splitUserData[5] != "m") {
            throw new GenderException("Ошибка!!!! Пол нужно указать буквами f или m");
        }
        return splitUserData;
    }
}

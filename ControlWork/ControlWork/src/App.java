import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class App {
    public static String surname = "";

    public static void main(String[] args) throws Exception {
        String choice = prompt("1. Чтобы ввести данные пользователя\n2. Для выхода из приложения введите 2");
        while (!"2".equals(choice)) {
            String userData = "";
            boolean appendWrite = false;
            userData = prompt("Здрвыствуйте! Введите ваши данные в указанной последовательности через пробел: <Фамилия> <Имя> <Отчество> <дата _ рождения в формате формата dd.mm.yyyy> <номер _ телефона> <пол>");
            String[] splitUserData = userData.split(" ");
            String finalData = splitUserData[0];
            for (int i = 1; i < splitUserData.length; i++) {
                finalData += " " + splitUserData[i];
            }
            surname += " " + splitUserData[0];
            if (splitUserData.length < 6) {
                throw new DataLengthException("Вы ввели МЕНЬШЕ данных чем необходимо!");
            }
            if (splitUserData.length > 6) {
                throw new DataLengthException("Вы ввели БОЛЬШЕ данных чем необходимо!");
            }
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
}

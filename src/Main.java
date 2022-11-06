import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<MonthlyReport> monthsData = new ArrayList<>();
        MonthNames monthNames = new MonthNames();
        YearlyReport yearData = null;
        boolean gotMonthData = false;
        boolean gotYearData = false;
        while (true) {
            System.out.println("1. Считать все месячные отчёты");
            System.out.println("2. Считать годовой отчёт");
            System.out.println("3. Сверить отчёты");
            System.out.println("4. Вывести информацию о всех месячных отчётах");
            System.out.println("5. Вывести информацию о годовом отчёте");
            int userInput = scanner.nextInt();
            if (userInput == 1) {
                for (int i = 1; i < 4; i++) {
                    String data = readFileContentsOrNull("resources/m.20210" + i + ".csv");
                    if (!(data == null)) {
                        MonthlyReport s = new MonthlyReport(data);
                        monthsData.add(s);
                    }
                }
                gotMonthData = true;
            }
            else if (userInput == 2) {
                String data = readFileContentsOrNull("resources/y.2021.csv");
                if (!(data == null)) {
                    yearData = new YearlyReport(data);
                }
                gotYearData = true;
            }
            else if (userInput == 3) {
                if (gotMonthData && gotYearData && yearData != null) {
                    boolean isEqual = false;
                    int monthIncome;
                    int monthExpense;
                    for (int i = 0; i < 3; i++) {
                        monthIncome = monthsData.get(i).getMaxIncome();
                        monthExpense = monthsData.get(i).getMaxExpense();
                        if (monthIncome == yearData.getMonthIncome(i*2) && monthExpense == yearData.getMonthExpense(i*2)) {
                            isEqual = true;
                        } else {
                            isEqual = false;
                            System.out.println("Месяц, в котором обнаружено несоответствие - " + monthNames.monthNames.get(i));
                            break;
                        }
                    }
                    if (isEqual){
                        System.out.println("Операция успешно завершена.");
                    }
                }
                else {
                    System.out.println("Отчеты не считаны");
                }
            }
            else if (userInput == 4) {
                if (gotMonthData && gotYearData) {
                    for (int i = 0; i < 3; i++) {
                        System.out.println(monthNames.monthNames.get(i));
                        System.out.println("Самый прибыльный товар - " + monthsData.get(i).getValueItem() + " сумма - " + monthsData.get(i).costValueItem);
                        System.out.println("Самая большая трата " + monthsData.get(i).getMostExpense() + " сумма - " + monthsData.get(i).expenseItem);
                    }
                }
                else {
                    System.out.println("Отчеты не считаны");
                }
            }
            else if (userInput == 5) {
                if (gotMonthData && gotYearData) {
                    System.out.println("Рассматриваемый год - 2021");
                    for (int i = 0; i < 3; i++) {
                        int profit = monthsData.get(i).getMaxIncome() - monthsData.get(i).getMaxExpense();
                        System.out.println("Прибыль за " + monthNames.monthNames.get(i) + " " + profit);
                    }
                    double midIncome;
                    double income = 0.0;
                    for (int i = 0; i < 3; i++) {
                        income +=monthsData.get(i).getMaxIncome();
                    }
                    midIncome = income/3;
                    System.out.println("Средний расход за все месяцы в году - " + midIncome);
                    double midExpense;
                    double expense = 0.0;
                    for (int i = 0; i < 3; i++) {
                        expense +=monthsData.get(i).getMaxExpense();
                    }
                    midExpense = expense/3;
                    System.out.println("Средний доход за все месяцы в году - " + midExpense);
                }
                else {
                    System.out.println("Отчеты не считаны");
                }
            }
            else if (userInput == 0) {
                System.out.println("Выход");
                break;
            }
            else {
                System.out.println("Команда не найдена");
            }
        }

    }
    private static String readFileContentsOrNull(String path)
    {
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с месячным отчётом. Возможно, файл не находится в нужной директории.");
            return null;
        }
    }
        }




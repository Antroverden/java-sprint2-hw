import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static final int YEAR = 2021;
    static final String ROOT = "resources";
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<MonthlyReport> monthsData = null;
        MonthNames monthNames = new MonthNames();
        YearlyReport yearData = null;
        while (true) {
            System.out.println("1. Считать все месячные отчёты");
            System.out.println("2. Считать годовой отчёт");
            System.out.println("3. Сверить отчёты");
            System.out.println("4. Вывести информацию о всех месячных отчётах");
            System.out.println("5. Вывести информацию о годовом отчёте");
            System.out.println("0. Выход");
            int userInput = scanner.nextInt();
            if (userInput == 1) {
                monthsData = new ArrayList<>();
                for (int i = 1; i < 4; i++) {
                    List<String> data;
                    try {
                        data = Files.readAllLines(Path.of(ROOT, "m.20210" + i + ".csv"));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    MonthlyReport s = new MonthlyReport(data);
                        monthsData.add(s);
                }
                System.out.println("Команда выполнена успешно");
            }
            else if (userInput == 2) {
                List<String> data;
                try {
                    data = Files.readAllLines(Path.of(ROOT,"y.2021.csv"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                yearData = new YearlyReport(data);
                System.out.println("Команда выполнена успешно");
            }
            else if (userInput == 3) {
                if (yearData != null && monthsData != null && !monthsData.isEmpty()) {
                    int notEqual = 0;
                    int monthIncome;
                    int monthExpense;
                    for (int i = 0; i < monthsData.size(); i++) {
                        monthIncome = monthsData.get(i).getMaxIncome();
                        monthExpense = monthsData.get(i).getMaxExpense();
                        if (!(monthIncome == yearData.getMonthIncome(i*2) && monthExpense == yearData.getMonthExpense(i*2))) {
                            notEqual++;
                            System.out.println("Месяц, в котором обнаружено несоответствие - " + monthNames.monthNames.get(i));
                        }
                    }
                    if (notEqual == 0) {
                        System.out.println("Операция успешно завершена.");
                    }
                }
                else {
                    System.out.println("Отчеты не считаны");
                }
            }
            else if (userInput == 4) {
                if (monthsData != null && !monthsData.isEmpty()) {
                    for (int i = 0; i < monthsData.size(); i++) {
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
                if (yearData != null) {
                    System.out.println("Рассматриваемый год - " + YEAR);
                    int sizeYear = yearData.items.size()/2;
                    for (int i = 0; i < sizeYear; i++) {
                        int profit = yearData.getMonthIncome(i*2) - yearData.getMonthExpense(i*2);
                        System.out.println("Прибыль за " + monthNames.monthNames.get(i) + " " + profit);
                    }
                    double midIncome;
                    double income = 0.0;
                    for (int i = 0; i < sizeYear; i++) {
                        income += yearData.getMonthIncome(i*2);
                    }
                    midIncome = income/sizeYear;
                    System.out.println("Средний расход за все месяцы в году - " + midIncome);
                    double midExpense;
                    double expense = 0.0;
                    for (int i = 0; i < sizeYear; i++) {
                        expense += yearData.getMonthExpense(i*2);
                    }
                    midExpense = expense/sizeYear;
                    System.out.println("Средний доход за все месяцы в году - " + midExpense);
                }
                else {
                    System.out.println("Отчет не считан");
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
        }
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        final int YEAR = 2021;
        final String ROOT = "resources";
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
            }
            else if (userInput == 2) {
                List<String> data;
                try {
                    data = Files.readAllLines(Path.of(ROOT,"y.2021.csv"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                yearData = new YearlyReport(data);
            }
            else if (userInput == 3) {
                if (yearData != null && monthsData != null && !monthsData.isEmpty()) {
                    boolean isEqual = false;
                    int monthIncome;
                    int monthExpense;
                    for (int i = 0; i < monthsData.size(); i++) {
                        monthIncome = monthsData.get(i).getMaxIncome();
                        monthExpense = monthsData.get(i).getMaxExpense();
                        if (monthIncome == yearData.getMonthIncome(i*2) && monthExpense == yearData.getMonthExpense(i*2)) {
                            isEqual = true;
                        } else {
                            isEqual = false;
                            System.out.println("Месяц, в котором обнаружено несоответствие - " + monthNames.monthNames.get(i));
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
                if (monthsData != null && !monthsData.isEmpty()) {
                    System.out.println("Рассматриваемый год - " + YEAR);
                    for (int i = 0; i < monthsData.size(); i++) {
                        int profit = monthsData.get(i).getMaxIncome() - monthsData.get(i).getMaxExpense();
                        System.out.println("Прибыль за " + monthNames.monthNames.get(i) + " " + profit);
                    }
                    double midIncome;
                    double income = 0.0;
                    for (MonthlyReport monthsDatum : monthsData) {
                        income += monthsDatum.getMaxIncome();
                    }
                    midIncome = income/3;
                    System.out.println("Средний расход за все месяцы в году - " + midIncome);
                    double midExpense;
                    double expense = 0.0;
                    for (MonthlyReport monthsDatum : monthsData) {
                        expense += monthsDatum.getMaxExpense();
                    }
                    midExpense = expense/3;
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
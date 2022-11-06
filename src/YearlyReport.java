import java.util.ArrayList;

public class YearlyReport {

    ArrayList<YearLine> items = new ArrayList<> ();
    public YearlyReport(String fileContents) {
        String [] lines = fileContents.split("\\r?\\n");
        for (int i = 1; i < lines.length; i++) {
            String[] lineContents = lines[i].split(",");
            int month = Integer.parseInt(lineContents[0]);
            int amount = Integer.parseInt(lineContents[1]);
            boolean isExpense = Boolean.parseBoolean(lineContents[2]);
            YearLine line = new YearLine(month, amount, isExpense);
            items.add(line);
        }
    }

    int getMonthIncome (int number) {
        int income = 0;
        if (!items.get(number).isExpense){
            income = items.get(number).amount;
        }
        else if (!items.get(number+1).isExpense){
            income = items.get(number+1).amount;
        }
        return income;
    }
    int getMonthExpense (int number) {
        int expense = 0;
        if (items.get(number).isExpense){
            expense = items.get(number).amount;
        }
        else if (items.get(number+1).isExpense) {
            expense = items.get(number+1).amount;
        }
        return expense;
    }
}

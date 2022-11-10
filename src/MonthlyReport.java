import java.util.ArrayList;
import java.util.List;

public class MonthlyReport {
    String valueItem;
    int costValueItem;
    int expenseItem;
    ArrayList<MonthLine> items = new ArrayList<>();


    MonthlyReport(List<String> lines) {
        for (int i = 1; i < lines.size(); i++) {
            String[] lineContents = lines.get(i).split(",");
            String itemName = lineContents[0];
            boolean isExpense = Boolean.parseBoolean(lineContents[1]);
            int quantity = Integer.parseInt(lineContents[2]);
            int sumOfOne = Integer.parseInt(lineContents[3]);
            MonthLine line = new MonthLine(itemName, isExpense, quantity, sumOfOne);
            items.add(line);
        }
    }
    String getValueItem() {
        int max = 0;
        for (MonthLine item : items) {
            if (!item.isExpense) {
                int itemCost = item.quantity * item.sumOfOne;
                if (max < itemCost) {
                    max = itemCost;
                    valueItem = item.itemName;
                }
            }
        }
        costValueItem = max;
        return valueItem;
    }
    String getMostExpense() {
        int max = 0;
        for (MonthLine item : items) {
            if (item.isExpense) {
                int itemCost = item.quantity * item.sumOfOne;
                if (max < itemCost) {
                    max = itemCost;
                    valueItem = item.itemName;
                }
            }
        }
        expenseItem = max;
        return valueItem;
    }
    int getMaxExpense() {
        int itemExpense = 0;
        for (MonthLine item : items) {
            if (item.isExpense) {
                itemExpense += item.quantity * item.sumOfOne;
            }
        }
        return itemExpense;
    }
    int getMaxIncome() {
        int itemIncome = 0;
        for (MonthLine item : items) {
            if (!item.isExpense) {
                itemIncome += item.quantity * item.sumOfOne;
            }
        }
        return itemIncome;
    }
}
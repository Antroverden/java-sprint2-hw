import java.util.ArrayList;

public class MonthlyReport {
    String valueItem;
    int costValueItem;
    int expenseItem;
    ArrayList<MonthLine> items = new ArrayList<>();


    MonthlyReport(String fileContents) {
        String[] lines = fileContents.split("\\r?\\n");
        for (int i = 1; i < lines.length; i++) {
            String[] lineContents = lines[i].split(",");
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
                    this.valueItem = item.itemName;
                }
            }

        }
        this.costValueItem = max;
        return valueItem;
    }

    String getMostExpense() {
        int max = 0;
        for (MonthLine item : items) {
            if (item.isExpense) {
                int itemCost = item.quantity * item.sumOfOne;
                if (max < itemCost) {
                    max = itemCost;
                    this.valueItem = item.itemName;
                }
            }

        }
        this.expenseItem = max;
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






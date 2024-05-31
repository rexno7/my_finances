package myfinances.chart;

import myfinances.transaction.Category;

public interface IChartEntry {
    String getMerchant();
    Float getAmount();
    Category getCategory();
}

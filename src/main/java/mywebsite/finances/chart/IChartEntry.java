package mywebsite.finances.chart;

import mywebsite.finances.transaction.Category;

public interface IChartEntry {
    String getMerchant();
    Float getAmount();
    Category getCategory();
}

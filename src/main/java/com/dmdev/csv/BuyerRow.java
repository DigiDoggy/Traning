package com.dmdev.csv;

public record BuyerRow(Integer id,
                       Integer ordersNumber,
                       Double caloriesAvg,
                       Double orderPriceAvg) {
}

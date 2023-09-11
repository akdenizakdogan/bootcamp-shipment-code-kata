package com.trendyol.shipment;

import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.stream.Stream;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class Basket {

    private List<Product> products;

    public ShipmentSize getShipmentSize() {

        if (products.size() < 3) {

            ShipmentSize biggestSize = ShipmentSize.SMALL;

            for (Product product : products) {
                if (product.getSize().ordinal() > biggestSize.ordinal()) {
                    biggestSize = product.getSize();
                }
            }

            return biggestSize;
        }

        if (products.stream().allMatch(x -> x.getSize() == ShipmentSize.X_LARGE)) {
            return ShipmentSize.X_LARGE;
        }

        Map<ShipmentSize, Long> groupedProducts = products.stream().collect(groupingBy(Product::getSize, counting()));
        Stream<Map.Entry<ShipmentSize, Long>> sizesBiggerThenThreshold = groupedProducts.entrySet().stream().filter(x -> x.getValue() >= 3);
        Map.Entry<ShipmentSize, Long> sizeCountEntry = sizesBiggerThenThreshold.findFirst().orElse(null);
        ShipmentSize biggestSize;

        if (sizeCountEntry != null) {
            biggestSize = sizeCountEntry.getKey().next();
        } else {
            OptionalInt maxEnumIndex = groupedProducts.keySet().stream().mapToInt(Enum::ordinal).max();
            biggestSize = ShipmentSize.values()[maxEnumIndex.orElse(0)];
        }

        return biggestSize;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}

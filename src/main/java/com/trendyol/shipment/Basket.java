package com.trendyol.shipment;

import java.util.*;
import java.util.stream.Collectors;

public class Basket {

    private List<Product> products;
    private static final int SHIPMENT_SIZE_THRESHOLD = 3;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public ShipmentSize getShipmentSize() {
        if (products.size() < SHIPMENT_SIZE_THRESHOLD) {
            return findBiggestSize();
        }

        var shipmentSizeCountMap = products.stream()
                .collect(Collectors.groupingBy(Product::getSize, Collectors.counting()));

        var sizeCountEntry = shipmentSizeCountMap.entrySet().stream()
                .filter(entry -> entry.getValue() >= SHIPMENT_SIZE_THRESHOLD)
                .findFirst();

        var biggestSize = sizeCountEntry.map(entry -> entry.getKey().next())
                .orElse(getMaxSizeFromShipmentSizeCountMap(shipmentSizeCountMap));

        return biggestSize;
    }

    private ShipmentSize findBiggestSize() {
        return products.stream()
                .map(Product::getSize)
                .max(Comparator.comparing(Enum::ordinal))
                .orElse(ShipmentSize.SMALL);
    }

    private ShipmentSize getMaxSizeFromShipmentSizeCountMap(Map<ShipmentSize, Long> shipmentSizeCountMap) {
        OptionalInt maxEnumIndex = shipmentSizeCountMap.keySet().stream()
                .mapToInt(Enum::ordinal)
                .max();
        return ShipmentSize.values()[maxEnumIndex.orElse(0)];
    }
}
package com.trendyol.shipment;

public enum ShipmentSize {

    SMALL,
    MEDIUM,
    LARGE,
    X_LARGE;

    private static final ShipmentSize[] shipmentSizes = values();

    public ShipmentSize next() {
        int nextIndex = this.ordinal() + 1 == shipmentSizes.length ? this.ordinal() : this.ordinal() + 1;

        return shipmentSizes[nextIndex];
    }
}



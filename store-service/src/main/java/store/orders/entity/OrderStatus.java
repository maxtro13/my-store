package store.orders.entity;

public enum OrderStatus {
    PENDING("PENDING"),
    DELIVERED("DELIVERED"),
    COMPLETED("COMPLETED"),
    PROCESSING("PROCESSING"),
    CANCELED("CANCELED");

    OrderStatus(String status) {

    }
}

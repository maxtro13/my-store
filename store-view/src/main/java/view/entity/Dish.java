package view.entity;

public record Dish(Long id, String name, String description, String category, Boolean availability, Double price, String imageUrl, Long imageId) {

}

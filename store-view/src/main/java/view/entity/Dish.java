package view.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//public record Dish(Long id, String name, String description, String category, Boolean availability, Double price,
//                   String imageUrl, Long imageId) {
//}
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Dish {
    private Long id;
    private String name;
    private String description;
    private String category;
    private Boolean availability;
    private Double price;
    private String imageUrl;
    private Long imageId;
}
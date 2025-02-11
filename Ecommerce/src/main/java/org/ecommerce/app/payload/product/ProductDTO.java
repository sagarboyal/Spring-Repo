package org.ecommerce.app.payload.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {
    private Long productId;
    private String productName;
    private String imageUrl;
    private Integer quantity;
    private Double price;
    private Double discount;
    private Double specialPrice;
    private String description;
}

package com.elixir.elixir.entity.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

import com.elixir.elixir.entity.dto.ProductImageDTO;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private Long productId;
    private String name;
    private String productDescription;
    private Double price;
    private int stock;
    private LocalDateTime datePublished;
    private boolean state;
    private Long varietyId;
    private Long subCategoryId;
    private Long categoryId;
    private List<ProductImageDTO> imagesList;

}

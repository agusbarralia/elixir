package com.elixir.elixir.controllers;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.elixir.elixir.entity.Product;
import lombok.Data;

@Data
public class ProductRequest {
    private Product product;
    private List<MultipartFile> images;

}

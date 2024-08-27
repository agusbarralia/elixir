package com.elixir.elixir.entity.dto;

public class ProductRequest {
    private Long product_id;
    private Long quantity;

    public ProductRequest() {
    }

    public ProductRequest(Long product_id, Long quantity) {
        this.product_id = product_id;
        this.quantity = quantity;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
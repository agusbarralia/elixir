package com.elixir.elixir.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.elixir.elixir.entity.ProductsCart;
import com.elixir.elixir.entity.dto.ProductsCartDTO;
import com.elixir.elixir.service.Interface.ProductCartService;

@RestController
@RequestMapping("productscart")
public class ProductCartController {
    
    @Autowired
    private ProductCartService productCartService;

    @GetMapping("/{cart_id}")
    public List<ProductsCartDTO> getProductCartByCartId(@PathVariable Long cart_id) {
        return productCartService.getProductCartByCartId(cart_id);
    }
    
    // ESTA REQUEST NO DEBERIA DE NECESITAR UN PRODUCTO Y QUE EL PRODUCTCART SE CREE A PARTIR DE EL PRODUCTO DADO???
    @PostMapping("/addtocart")
    public ResponseEntity<List<ProductsCartDTO>> addtoCart(@RequestBody ProductsCart productsCart) {
        ProductsCartDTO result = productCartService.addtoCart(productsCart);
        return ResponseEntity.ok(productCartService.getProductCartByCartId(result.getCart_id()));
    }
    
    @PostMapping("/removeproduct/{productscart_id}")
    public ResponseEntity<Boolean> removeProduct(@PathVariable Long productscart_id){
        productCartService.removeProduct(productscart_id);
        return ResponseEntity.ok(true);
    }

    @PostMapping("/removeallproducts/{cart_id}")
    public ResponseEntity<Boolean> removeAllProducts(@PathVariable Long cart_id){
        productCartService.removeAllProducts(cart_id);
        return ResponseEntity.ok(true);
    }
}

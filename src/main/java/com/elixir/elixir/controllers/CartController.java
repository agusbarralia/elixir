package com.elixir.elixir.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.elixir.elixir.entity.dto.CartDTO;
import com.elixir.elixir.entity.dto.ProductsCartDTO;
import com.elixir.elixir.exceptions.CartNoSuchElementException;
import com.elixir.elixir.exceptions.ProductCartNoSuchElementException;
import com.elixir.elixir.exceptions.ProductNoSuchElementException;
import com.elixir.elixir.service.Interface.CartService;
import com.elixir.elixir.service.Interface.UserService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping
    public ResponseEntity<CartDTO> getCartByUserId() throws CartNoSuchElementException{
        return ResponseEntity.ok(cartService.getCartByUserId());
    }

    @PostMapping("/add")
    public ResponseEntity<ProductsCartDTO> addToCart(@RequestParam Long productId, @RequestParam int quantity) throws CartNoSuchElementException {
        ProductsCartDTO currentProductCartDTO = cartService.addProductToCart(productId, quantity);
        return ResponseEntity.ok(currentProductCartDTO);
    }

    @PutMapping("/update")
    public ResponseEntity<ProductsCartDTO> updateProductQuantity(@RequestParam Long productId, @RequestParam int quantity) throws CartNoSuchElementException, ProductNoSuchElementException, ProductCartNoSuchElementException {
        ProductsCartDTO currentProductCartDTO = cartService.updateProductQuantity(productId, quantity);
        return ResponseEntity.ok(currentProductCartDTO);

    }

    @DeleteMapping("/remove")
    public ResponseEntity<String> removeFromCart(@RequestParam Long productId) throws CartNoSuchElementException, ProductNoSuchElementException, ProductCartNoSuchElementException {
        if (cartService.removeProductFromCart(productId)){
            return ResponseEntity.ok("El producto ha sido eliminado correctamente del carrito.");
        }
        else{
            return ResponseEntity.ok("No ha sido posible eliminar el producto del carrito.");
        }
    }

    @DeleteMapping("/removeAll")
    public ResponseEntity<String> removeAllProductsFromCart() throws CartNoSuchElementException {
        if (cartService.removeAllProductsFromCart()){
            return ResponseEntity.ok("Todos los productos han sido eliminados del carrito.");
        }
        else{
            return ResponseEntity.ok("El carrito ya esta vacio.");
        }
    }

}



package com.elixir.elixir.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

import com.elixir.elixir.entity.Cart;
import com.elixir.elixir.entity.Product;
import com.elixir.elixir.entity.ProductsCart;
import com.elixir.elixir.entity.dto.CartDTO;
import com.elixir.elixir.entity.dto.ProductsCartDTO;
import com.elixir.elixir.entity.User;
import com.elixir.elixir.exceptions.CartDuplicateException;
import com.elixir.elixir.exceptions.CartNoSuchElementException;
import com.elixir.elixir.exceptions.ProductNoSuchElementException;
import com.elixir.elixir.exceptions.ProductCartNoSuchElementException;
import com.elixir.elixir.repository.CartRepository;
import com.elixir.elixir.repository.ProductCartRepository;
import com.elixir.elixir.repository.ProductRepository;
import com.elixir.elixir.service.Interface.CartService;
import com.elixir.elixir.service.Interface.ProductCartService;
import com.elixir.elixir.service.Interface.UserService;


@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductCartService productCartService;

    @Autowired
    private ProductCartRepository productCartRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductRepository productRepository;

    public void createCart(User user) throws CartDuplicateException {
        Optional<Cart> cart = cartRepository.findByUserId(user.getUser_id());
        if (cart.isPresent()) {
            throw new CartDuplicateException();
        } else {
            Cart newCart = new Cart();
            newCart.setUser(user);
            cartRepository.save(newCart);
        }
    }

    public CartDTO getCartByUserId() throws CartNoSuchElementException{
        Optional<Cart> cart = cartRepository.findByUserId(userService.getCurrentUserId());
        if (cart.isPresent()) {
            return convertToDto(cart.get());
        } else {
            throw new CartNoSuchElementException();
        }
    }

    public ProductsCartDTO addProductToCart(Long product_id, int quantity) throws CartNoSuchElementException {
        Optional<Cart> cart = cartRepository.findByUserId(userService.getCurrentUserId());
        if (cart.isPresent()) {
            if (quantity <= 0) {
                throw new IllegalStateException("La cantidad debe ser mayor a 0.");
            }
            ProductsCartDTO productsCartDTO = productCartService.createProductCart(product_id, quantity, cart.get());
            return productsCartDTO;
        }
        else{
            throw new CartNoSuchElementException();
        }
    }

    public ProductsCartDTO updateProductQuantity(Long product_id, int quantity) throws CartNoSuchElementException, ProductNoSuchElementException, ProductCartNoSuchElementException {
        Long userId = userService.getCurrentUserId();
        Cart cart = cartRepository.findByUserId(userId)
                    .orElseThrow(() -> new CartNoSuchElementException());

        Product product = productRepository.findById(product_id)
                    .orElseThrow(() -> new ProductNoSuchElementException());

        ProductsCart productCart = productCartRepository.findByCartAndProduct(cart, product)
                    .orElseThrow(() -> new ProductCartNoSuchElementException());

        if (quantity <= 0) {
            productCartRepository.deleteById(productCart.getProductscart_id());
            return null;
        }

        if (quantity > product.getStock()) {
            throw new IllegalStateException("No hay suficiente stock.");
        }

        productCart.setQuantity(quantity);
        productCart.setSubtotal(productCart.getUnit_price() * quantity * (1 - productCart.getDiscount()));
        productCartRepository.save(productCart);

        return productCartService.convertToDTO(productCart);
    }

    public Boolean removeProductFromCart(Long product_id) throws CartNoSuchElementException, ProductNoSuchElementException, ProductCartNoSuchElementException {
        Long userId = userService.getCurrentUserId();
        Cart cart = cartRepository.findByUserId(userId)
                    .orElseThrow(() -> new CartNoSuchElementException());

        Product product = productRepository.findById(product_id)
                    .orElseThrow(() -> new ProductNoSuchElementException());

        ProductsCart productCart = productCartRepository.findByCartAndProduct(cart, product)
                    .orElseThrow(() -> new ProductCartNoSuchElementException());

        productCartRepository.deleteById(productCart.getProductscart_id());
        return true;
    }

    public Boolean removeAllProductsFromCart() throws CartNoSuchElementException {
        Long userId = userService.getCurrentUserId();
        Cart cart = cartRepository.findByUserId(userId)
                    .orElseThrow(() -> new CartNoSuchElementException());

        if (cart.getProductsCart().isEmpty()) {
            return false;
        }
        else {
            productCartRepository.deleteByCartId(cart.getCart_id());
            return true;
        }
    }


    public CartDTO convertToDto(Cart cart) {
        if (cart == null) {
            return null;
        }
        CartDTO cartDTO = new CartDTO();
        cartDTO.setCartId(cart.getCart_id());
        cartDTO.setUserId(cart.getUser().getUser_id()); 
        cartDTO.setProductsCart( productCartService.convertAllToDTO(cart.getProductsCart()));

        return cartDTO;
    }
}
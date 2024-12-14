package com.example.demo.controllers;

import com.example.demo.infrastructure.common.exceptions.ItemNotFoundException;
import com.example.demo.infrastructure.common.exceptions.UserNotFoundException;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.IntStream;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/cart")
public class CartController {
    private UserRepository userRepository;
    private CartRepository cartRepository;
    private ItemRepository itemRepository;

    @PostMapping("/addToCart")
    public ResponseEntity<Cart> addTocart(@RequestBody ModifyCartRequest request) {
        Cart cart = handleModifyCartRequest(request, false);
        log.info("Item (id: {}) has been added to Cart (id: {}) for User (id: {}) successfully", request.getItemId(), cart.getId(), cart.getUser().getId());
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/removeFromCart")
    public ResponseEntity<Cart> removeFromcart(@RequestBody ModifyCartRequest request) {
        Cart cart = handleModifyCartRequest(request, true);
        log.info("Item (id: {}) has been removed from Cart (id: {}) for User (id: {}) successfully", request.getItemId(), cart.getId(), cart.getUser().getId());
        return ResponseEntity.ok(cart);
    }

    private Cart handleModifyCartRequest(ModifyCartRequest request, boolean remove) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UserNotFoundException(request.getUsername()));
        Item item = itemRepository.findById(request.getItemId())
                .orElseThrow(() -> new ItemNotFoundException(request.getItemId()));
        Cart cart = user.getCart();
        if (remove) {
            IntStream.range(0, request.getQuantity())
                    .forEach(i -> cart.removeItem(item));
        } else {
            IntStream.range(0, request.getQuantity())
                    .forEach(i -> cart.addItem(item));
        }
        cartRepository.save(cart);
        return cart;

    }


}

package com.ym.gulimall.cart.service;

import com.ym.gulimall.cart.vo.Cart;
import com.ym.gulimall.cart.vo.CartItem;

import java.util.concurrent.ExecutionException;

public interface CartService {

    CartItem addToCart(Long skuId, Integer num) throws ExecutionException, InterruptedException;

    CartItem getCartItem(Long skuId);

    Cart getCart() throws ExecutionException, InterruptedException;

    void clearCart(String cartKey);

    void checkItem(Long skuId, Integer check);

    void changeItemCount(Long skuId, Integer num);

    /**
     * delete item in the cart
     * @param skuId
     */
    void deleteItem(Long skuId);
}

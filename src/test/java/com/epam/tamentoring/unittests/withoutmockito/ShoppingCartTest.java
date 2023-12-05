package com.epam.tamentoring.unittests.withoutmockito;

import com.epam.tamentoring.bo.Product;
import com.epam.tamentoring.bo.ShoppingCart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class ShoppingCartTest {
    private ShoppingCart shoppingCart;

    @BeforeEach
    void setUp() {
        shoppingCart = new ShoppingCart(new ArrayList<>());
    }

    @Test
    void userCanAddSingleProductToTheShoppingCart() {
        Product keyboard = new Product(45, "Keyboard", 105, 1);
        shoppingCart.addProductToCart(keyboard);
        assertThat(shoppingCart.getProducts(), hasSize(1));
        assertThat(shoppingCart.getProducts(), hasItem(hasProperty("id", equalTo(45))));
    }

    @Test
    void userCanAddSeveralProductToTheShoppingCart() {
        Product table = new Product(234, "Table", 65.8, 1);
        Product chair = new Product(1409, "Chair", 34.09, 2);
        shoppingCart.addProductToCart(table);
        shoppingCart.addProductToCart(chair);
        assertThat(shoppingCart.getProducts(), hasSize(2));
        assertThat(shoppingCart.getProducts(), allOf(
                hasItem(hasProperty("id", equalTo(234))),
                hasItem(hasProperty("id", equalTo(1409)))
        ));
    }

    @Test
    void userCanAddDuplicateProductToTheShoppingCart() {
        Product table1 = new Product(234, "Table", 65.8, 1);
        Product table2 = new Product(234, "Chair", 65.8, 3);
        shoppingCart.addProductToCart(table1);
        shoppingCart.addProductToCart(table2);
        assertThat(shoppingCart.getProducts(), hasSize(1));
        assertThat(shoppingCart.getProducts(), hasItem(hasProperty("quantity", closeTo(4, 0.001))));
    }

    @Test
    void userCanRemoveProductsFromTheShoppingCart() {
        Product apple = new Product(1, "Apple", 2, 0.50);
        Product orange = new Product(2, "Orange", 1.5, 0.75);
        shoppingCart.addProductToCart(apple);
        shoppingCart.addProductToCart(orange);
        shoppingCart.removeProductFromCart(orange);
        assertThat(shoppingCart.getProducts(), hasSize(1));
        assertThat(shoppingCart.getProducts(), hasItem(hasProperty("id", equalTo(1))));
        assertThat(shoppingCart.getProducts(), not(hasItem(hasProperty("id", equalTo(2)))));
    }

    @Test
    void userCanGetTheTotalPriceOfTheShoppingCart() {
        Product laptop = new Product(768, "Laptop", 1560, 1);
        Product beer = new Product(9949, "Beer", 2.5, 6);
        shoppingCart.getProducts().addAll(Arrays.asList(laptop, beer));
        double expectedTotalPrice = 1560 + 2.5 * 6;
        assertThat(shoppingCart.getCartTotalPrice(), closeTo(expectedTotalPrice, 0.001));
    }
}

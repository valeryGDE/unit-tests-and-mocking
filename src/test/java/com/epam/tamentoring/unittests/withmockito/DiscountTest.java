package com.epam.tamentoring.unittests.withmockito;

import com.epam.tamentoring.bo.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;

@ExtendWith(MockitoExtension.class)
class DiscountTest {
    @Mock
    private DiscountUtility discountUtility;
    @InjectMocks
    private OrderService orderService = new OrderService();

    @Test
    void calculateDiscountWithMocks() {
        ShoppingCart shoppingCart = new ShoppingCart(new ArrayList<>());
        Product product = new Product(3445, "Olive oil", 105.99, 1);
        shoppingCart.addProductToCart(product);
        UserAccount user = new UserAccount("John", "Smith", "1990/10/10", shoppingCart);
        double predefinedDiscount = 3;
        Mockito.when(discountUtility.calculateDiscount(user)).thenReturn(predefinedDiscount);
        double priceWithDiscount = orderService.getOrderPrice(user);
        double expectedPrice = product.getPrice() - predefinedDiscount;
        assertThat(priceWithDiscount, closeTo(expectedPrice, 0.001));
        Mockito.verify(discountUtility, Mockito.times(1)).calculateDiscount(user);
        Mockito.verifyNoMoreInteractions(discountUtility);
    }
}

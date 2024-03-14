package com.github.legionivo.testtask.ui.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ShoppingCartPage {
	private final SelenideElement checkoutButton = $("#checkout");
	private final ElementsCollection shoppingCartItems = $$("div[class='cart_item']");

	@Step("{amountOfItems} should be displayed on the shopping cart page")
	public ShoppingCartPage shoppingCartShouldContainAmountOfItems(Integer amountOfItems) {
		shoppingCartItems.shouldHave(size(amountOfItems));
		return this;
	}

	@Step("Go to Checkout")
	public StepOneCheckoutPage goToCheckout() {
		checkoutButton.click();
		return new StepOneCheckoutPage();
	}
}

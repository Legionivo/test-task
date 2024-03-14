package com.github.legionivo.testtask.ui.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThanOrEqual;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class InventoryPage {
	private final ElementsCollection productsList = $$("div[class='inventory_item']");
	private final SelenideElement shoppingCart = $("a[class='shopping_cart_link']"),
			sortByPriceHighToLowOption = $("option[value='hilo']"),
			sortByNameDescendingOption = $("option[value='za']");

	@Step("Product list should not be empty")
	public InventoryPage productListShouldNotBeEmpty() {
		productsList.shouldHave(sizeGreaterThanOrEqual(1));
		return this;
	}

	@Step("Add product to Cart")
	public InventoryPage addProductToCart(SelenideElement product) {
		SelenideElement button = product.find("button");
		button.click();
		button.shouldHave(exactText("Remove"));
		return this;
	}

	@Step("Shopping cart icon should show {amountOfItems} item/s")
	public InventoryPage shoppingCartShouldShowAmountOfItems(Integer amountOfItems) {
		shoppingCart.shouldHave(text(String.valueOf(amountOfItems)));
		return this;
	}

	@Step("Open shopping cart")
	public ShoppingCartPage goToShoppingCart() {
		shoppingCart.click();
		return new ShoppingCartPage();
	}

	@Step("Sort items by Price(high to low)")
	public InventoryPage sortItemsByPriceHighToLow() {
		sortByPriceHighToLowOption.click();
		return this;
	}

	@Step("Sort items by Name(Z to A)")
	public InventoryPage sortItemsByNameDescendingOrder() {
		sortByNameDescendingOption.click();
		return this;
	}

}

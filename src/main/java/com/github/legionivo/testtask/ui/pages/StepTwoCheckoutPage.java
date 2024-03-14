package com.github.legionivo.testtask.ui.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class StepTwoCheckoutPage {

	private final SelenideElement finishPurchaseButton = $("#finish");
	private final ElementsCollection shoppingCartItems = $$("div[class='cart_item']");

	@Step("{amountOfItems} should be displayed on the second step of checkout")
	public StepTwoCheckoutPage shouldContainAmountOfItems(Integer amountOfItems) {
		shoppingCartItems.shouldHave(size(amountOfItems));
		return this;
	}

	@Step("Confirm the purchase and go to the order confirmation page")
	public StepOneCheckoutPage finishPurchase() {
		finishPurchaseButton.click();
		return new StepOneCheckoutPage();
	}

}

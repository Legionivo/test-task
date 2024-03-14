package com.github.legionivo.testtask.ui.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;

public class CheckoutCompletePage {
	private final SelenideElement orderCompleteHeader = $("h2[class='complete-header']");

	@Step("Order should be successfully placed")
	public CheckoutCompletePage orderShouldBeSuccessfullyPlaced() {
		orderCompleteHeader.shouldHave(exactText("Thank you for your order!"));
		return this;
	}
}

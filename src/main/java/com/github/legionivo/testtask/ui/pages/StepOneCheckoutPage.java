package com.github.legionivo.testtask.ui.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class StepOneCheckoutPage {
	private final SelenideElement firstNameInput = $("#first-name"),
			lastNameInput = $("#last-name"),
			postalCodeInput = $("#postal-code"),
			continueButton = $("#continue");

	@Step("Fill personal information: {firstName}, {lastName}, {zipCode}")
	public StepOneCheckoutPage fillPersonalInformation(String firstName, String lastName, String zipCode) {
		firstNameInput.setValue(firstName);
		lastNameInput.setValue(lastName);
		postalCodeInput.setValue(zipCode);
		return this;
	}

	@Step("Go to final checkout step")
	public StepTwoCheckoutPage goToCheckoutStepTwo() {
		continueButton.click();
		return new StepTwoCheckoutPage();
	}
}

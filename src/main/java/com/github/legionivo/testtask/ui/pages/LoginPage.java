package com.github.legionivo.testtask.ui.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class LoginPage {
	private final SelenideElement userNameInput = $("#user-name"),
			passwordInput = $("#password"),
			logInButton = $("#login-button");

	@Step("Open Login page")
	public LoginPage openPage() {
		open("/");
		return this;
	}

	@Step("Login with account: `{user}` and password: `{password}`")
	public InventoryPage loginAs(String user, String password) {
		userNameInput.setValue(user);
		passwordInput.setValue(password);
		logInButton.click();
		return new InventoryPage();
	}
}

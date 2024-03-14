package com.github.legionivo.testtask.ui.models;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Selenide.$$;

public class CartItems {

	private Integer quantity;
	private String name;

	private String description;

	private Double price;

	private SelenideElement webElement;

	public SelenideElement getWebElement() {
		return webElement;
	}

	private void setWebElement(SelenideElement webElement) {
		this.webElement = webElement;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@Step("Get details of all items in the shopping cart")
	public List<CartItems> getProductInCartDetails() {

		ElementsCollection rows = $$("div[class='cart_item']");

		return rows.asFixedIterable().stream()
				.map(element -> {
					final CartItems cartItems = new CartItems();

					String quantity = element.$("div[class='cart_quantity']").getText();
					cartItems.setQuantity(Integer.valueOf(quantity));

					String name = element.$("div[class='inventory_item_name']").getText();
					cartItems.setName(name);

					String description = element.$("div[class='inventory_item_desc']").getText();
					cartItems.setDescription(description);

					String price = element.$("div[class='inventory_item_price']").getText();
					price = normalizeSymbols(price);
					cartItems.setPrice(Double.valueOf(price));

					cartItems.setWebElement(element);
					return cartItems;
				})
				.collect(Collectors.toList());

	}

	public String normalizeSymbols(final String string) {

		return StringUtils.replace(string, "$", "");

	}
}

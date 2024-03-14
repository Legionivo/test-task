package com.github.legionivo.testtask.ui.models;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Selenide.$$;

public class ProductDetails {

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

	@Step("Get all products details")
	public List<ProductDetails> getProductDetails() {

		ElementsCollection rows = $$("div[class='inventory_item']");

		return rows.asFixedIterable().stream()
				.map(element -> {
					final ProductDetails productDetails = new ProductDetails();
					String name = element.$("div[class='inventory_item_name ']").getText();
					productDetails.setName(name);

					String description = element.$("div[class='inventory_item_desc']").getText();
					productDetails.setDescription(description);

					String price = element.$("div[class='inventory_item_price']").getText();
					price = normalizeSymbols(price);
					productDetails.setPrice(Double.valueOf(price));

					productDetails.setWebElement(element);
					return productDetails;
				})
				.collect(Collectors.toList());

	}

	public String normalizeSymbols(final String string) {

		return StringUtils.replace(string, "$", "");

	}
}

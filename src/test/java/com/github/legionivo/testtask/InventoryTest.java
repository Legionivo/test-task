package com.github.legionivo.testtask;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.github.javafaker.Faker;
import com.github.legionivo.testtask.ui.models.CartItems;
import com.github.legionivo.testtask.ui.models.ProductDetails;
import com.github.legionivo.testtask.ui.pages.InventoryPage;
import com.github.legionivo.testtask.ui.pages.LoginPage;
import com.github.legionivo.testtask.ui.pages.ShoppingCartPage;
import com.github.legionivo.testtask.ui.pages.StepOneCheckoutPage;
import com.github.legionivo.testtask.ui.pages.StepTwoCheckoutPage;
import com.github.legionivo.testtask.ui.pages.CheckoutCompletePage;
import com.github.legionivo.testtask.utils.AllureEnvironmentUtils;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.junit5.AllureJunit5;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Configuration.browser;
import static com.codeborne.selenide.WebDriverRunner.closeWebDriver;
import static com.github.legionivo.testtask.utils.ServerConfig.BASE_CONFIG;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith({AllureJunit5.class})
@DisplayName("Inventory tests")
public class InventoryTest {

	private static final String STANDARD_USERNAME = BASE_CONFIG.standardUserName();
	private static final String STANDARD_USER_PASSWORD = BASE_CONFIG.password();

	@BeforeAll
	public static void prepareSelenide() {
		Configuration.browser = BASE_CONFIG.selenideBrowser();
		Configuration.baseUrl = BASE_CONFIG.baseUrl();
		Configuration.browserSize = BASE_CONFIG.selenideBrowserSize();
		Configuration.timeout = BASE_CONFIG.selenideTimeout();
		SelenideLogger.addListener("allure", new AllureSelenide());
	}

	@AfterAll
	static void tearDown() {
		SelenideLogger.removeListener("allure");
		AllureEnvironmentUtils.create(browser, Configuration.baseUrl);
		closeWebDriver();
	}

	LoginPage loginPage = new LoginPage();
	InventoryPage inventoryPage = new InventoryPage();
	ShoppingCartPage shoppingCartPage = new ShoppingCartPage();
	StepOneCheckoutPage stepOneCheckoutPage = new StepOneCheckoutPage();
	StepTwoCheckoutPage stepTwoCheckoutPage = new StepTwoCheckoutPage();
	CheckoutCompletePage checkoutCompletePage = new CheckoutCompletePage();

	@BeforeEach
	void login() {
		loginPage
				.openPage()
				.loginAs(STANDARD_USERNAME, STANDARD_USER_PASSWORD);
	}

	@Test
	@Tag("Regression")
	@Epic("Inventory")
	@Feature("Purchase")
	@Story("Cart")
	@DisplayName("An item should be added to the cart and is visible on the cart page")
	void itemShouldBeAddedToCartAndVisibleOnCartPage() {
		inventoryPage.productListShouldNotBeEmpty();

		List<ProductDetails> productDetailsList = new ProductDetails().getProductDetails();

		ProductDetails firstProductInTheList = productDetailsList.get(0);
		inventoryPage
				.addProductToCart(firstProductInTheList.getWebElement())
				.shoppingCartShouldShowAmountOfItems(1)
				.goToShoppingCart();

		List<CartItems> productInCartDetails = new CartItems().getProductInCartDetails();
		CartItems itemInCart = productInCartDetails.get(0);
		shoppingCartPage.shoppingCartShouldContainAmountOfItems(1);

		assertAll(
				"Grouped Assertions of Item on Checkout Page",
				() -> assertEquals(firstProductInTheList.getName(), itemInCart.getName(), "Product name is incorrect"),
				() -> assertEquals(firstProductInTheList.getDescription(), itemInCart.getDescription(), "Product description is incorrect"),
				() -> assertEquals(firstProductInTheList.getPrice(), itemInCart.getPrice(), "Product price is incorrect"),
				() -> assertEquals(1, itemInCart.getQuantity(), "Product quantity is incorrect")
		);
	}

	@Test
	@Tag("Regression")
	@Epic("Inventory")
	@Feature("Purchase")
	@Story("Buy product")
	@DisplayName("Ensure the user can complete the purchase/checkout process")
	void userShouldBeAbleToCompleteThePurchase() {
		inventoryPage.productListShouldNotBeEmpty();

		List<ProductDetails> productDetailsList = new ProductDetails().getProductDetails();

		ProductDetails secondProductInTheList = productDetailsList.get(1);
		inventoryPage
				.addProductToCart(secondProductInTheList.getWebElement())
				.shoppingCartShouldShowAmountOfItems(1)
				.goToShoppingCart();

		shoppingCartPage
				.shoppingCartShouldContainAmountOfItems(1)
				.goToCheckout();

		Faker faker = new Faker();

		stepOneCheckoutPage
				.fillPersonalInformation(faker.name().firstName(), faker.name().lastName(), faker.address().zipCode())
				.goToCheckoutStepTwo();

		stepTwoCheckoutPage
				.shouldContainAmountOfItems(1);

		List<CartItems> productDetailsListOnCheckout = new CartItems().getProductInCartDetails();
		CartItems productDetailsOnCheckout = productDetailsListOnCheckout.get(0);

		assertAll(
				"Make sure nothing has changed on the second step of purchase",
				() -> assertEquals(secondProductInTheList.getName(), productDetailsOnCheckout.getName(), "Product name is incorrect"),
				() -> assertEquals(secondProductInTheList.getPrice(), productDetailsOnCheckout.getPrice(), "Product price is incorrect"),
				() -> assertEquals(1, productDetailsOnCheckout.getQuantity(), "Product quantity is incorrect")
		);

		stepTwoCheckoutPage.finishPurchase();
		checkoutCompletePage.orderShouldBeSuccessfullyPlaced();
	}

	@Test
	@Tag("Regression")
	@Epic("Inventory")
	@Feature("Purchase")
	@Story("Sort products by price")
	@DisplayName("Verify that inventory items can be sorted by price, high-to-low, and the sorting is correct")
	void itemsShouldBeCorrectlySortedByPriceHighToLow() {
		inventoryPage
				.productListShouldNotBeEmpty()
				.sortItemsByPriceHighToLow();
		List<ProductDetails> sortedProductDetailsList = new ProductDetails().getProductDetails();

		// 3. Assert that each element in the sorted list is greater than or equal to its successor
		assertAll("prices should be in descending order",
				() -> {
					for (int i = 0; i < sortedProductDetailsList.size() - 1; i++) {
						double currentPrice = sortedProductDetailsList.get(i).getPrice();
						double nextPrice = sortedProductDetailsList.get(i + 1).getPrice();
						assertTrue(currentPrice >= nextPrice,
								"Each product price should be greater than or equal to its successor");
					}
				}
		);
	}

	@Test
	@Tag("Regression")
	@Epic("Inventory")
	@Feature("Purchase")
	@Story("Sort products by name")
	@DisplayName("Ensure that inventory can be sorted by name, Z-to-A, and the sorting is correct")
	void itemsShouldBeCorrectlySortedByNameFromDescendingOrder() {
		inventoryPage
				.productListShouldNotBeEmpty()
				.sortItemsByNameDescendingOrder();

		List<ProductDetails> sortedProductDetailsList = new ProductDetails().getProductDetails();

		List<ProductDetails> sortedList = sortedProductDetailsList.stream()
				.sorted((product1, product2) -> product2.getName().compareTo(product1.getName())).collect(Collectors.toList());

		// 3. Assert that each element in the sorted list is greater than or equal to its predecessor
		assertAll("names should be in descending order",
				() -> {
					for (int i = 0; i < sortedList.size() - 1; i++) {
						String currentName = sortedList.get(i).getName();
						String nextName = sortedList.get(i + 1).getName();
						assertTrue(currentName.compareTo(nextName) >= 0,
								"Each product name should be greater than or equal to its successor in a Z-to-A order");
					}
				}
		);
	}
}

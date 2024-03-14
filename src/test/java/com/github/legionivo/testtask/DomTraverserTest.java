package com.github.legionivo.testtask;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;

import java.util.function.Consumer;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.executeJavaScript;

@DisplayName("Dom traverser Bonus task")
public class DomTraverserTest {

	public static void visitElements(SelenideElement element, Consumer<SelenideElement> callback) {
		// Apply the callback to the current element
		callback.accept(element);

		// Find all child elements. Assuming children can be selected via the ">" CSS selector.
		// This is a simplification and might need adjustment based on actual DOM structure and requirements.
		for (SelenideElement child : element.$$(By.xpath("./*"))) {
			visitElements(child, callback); // Recurse for each child
		}
	}

	public void visitDomElements() {
		String jsCode = "" +
				"function visitElements(element, callback) {" +
				"    callback(element);" +
				"    const children = element.querySelectorAll(':scope > *');" +
				"    children.forEach(child => {" +
				"        visitElements(child, callback);" +
				"    });" +
				"}" +
				// Example callback that logs element's tagName
				"visitElements(arguments[0], function(element) { console.log(element.tagName); });";

		// Assuming you have a SelenideElement, e.g., the body of the page to start with
		SelenideElement rootElement = $("body");

		// Execute the JavaScript function
		executeJavaScript(jsCode, rootElement);
	}

	@Test
	public void testDomTraverseUsingJS() {
		Configuration.browserCapabilities.setCapability("goog:loggingPrefs", java.util.Collections.singletonMap(LogType.BROWSER, java.util.logging.Level.ALL));
		Configuration.browser = "chrome";
		// Open a page before using
		Selenide.open("https://www.saucedemo.com/");
		// Function that prints the tag name of each element to BROWSER CONSOLE
		visitDomElements();
		// This function gathers browser logs and prints them
		LogEntries logEntries = WebDriverRunner.getWebDriver().manage().logs().get(LogType.BROWSER);
		for (LogEntry entry : logEntries) {
			System.out.println(entry.getMessage());
		}
	}

	@Test
	public void testDomTraverseUsingSelenide() {
		Configuration.browser = "chrome";
		// Open a page before using
		Selenide.open("https://www.saucedemo.com/");

		// Use the function with an example callback that prints the tag name of each element
		visitElements($("body"), element -> System.out.println(element.getTagName()));

	}
}

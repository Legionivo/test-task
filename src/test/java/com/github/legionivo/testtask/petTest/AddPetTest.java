package com.github.legionivo.testtask.petTest;

import com.github.legionivo.testtask.api.TestDataGenerator;
import com.github.legionivo.testtask.api.models.pet.Pet;
import com.github.legionivo.testtask.api.steps.PetSteps;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Epic("Pet store")
@Story("Post")
@Feature("Pet")
@DisplayName("Add Pet")
public class AddPetTest {

	private final Pet minDataPet = TestDataGenerator.generatePetWithMinimumData();
	private final Pet fullDataPet = TestDataGenerator.generatePetWillFullData();

	private final PetSteps petSteps = new PetSteps();

	@Test
	@DisplayName("Adding a pet with a minimum set of fields")
	public void createMinDataPet() {
		petSteps.createPetSuccessfully(minDataPet);
	}

	@Test
	@DisplayName("Adding a pet with the maximum set of fields")
	public void createFullDataPet() {
		petSteps.createPetSuccessfully(fullDataPet);
	}

	@Test
	@DisplayName("Processing an incorrect request body using the post method")
	public void postIncorrectJson() {
		petSteps.postBadRequest("{ \"id\": incorrect}");
	}
}


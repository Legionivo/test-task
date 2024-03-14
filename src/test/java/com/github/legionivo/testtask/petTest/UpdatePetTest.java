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
@Story("Put")
@Feature("Pet")
@DisplayName("Update Pet")
public class UpdatePetTest {

	private final Pet fullDataPet = TestDataGenerator.generatePetWillFullData();
	private final Pet modifiedPet = fullDataPet.toBuilder().name("SayMyName").build();
	private final PetSteps petSteps = new PetSteps();

	@Test
	@DisplayName("Pet data update")
	public void updateFullDataPet() {
		petSteps.createPetSuccessfully(fullDataPet)
				.putPetSuccessfully(modifiedPet)
				.assertPetData(modifiedPet);
	}

	@Test
	@DisplayName("Creating a new pet using the PUT method")
	public void putNewPet() {
		petSteps.putPetSuccessfully(fullDataPet).assertPetData(fullDataPet);
	}

	@Test
	@DisplayName("Processing an incorrect request body using the PUT method")
	public void putIncorrectJson() {
		petSteps.putBadRequest("{ \"id\": Value}");
	}
}

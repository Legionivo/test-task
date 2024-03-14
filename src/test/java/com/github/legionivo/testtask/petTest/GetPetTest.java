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
@Story("Get")
@Feature("Pet")
@DisplayName("Get Pet")
public class GetPetTest {

	private final Pet fullDataPet = TestDataGenerator.generatePetWillFullData();
	private final PetSteps petSteps = new PetSteps();

	@Test
	@DisplayName("Get pet")
	public void getPetTest() {
		petSteps.createPetSuccessfully(fullDataPet).assertPetData(fullDataPet);
	}

	@Test
	@DisplayName("Trying to get a pet using a non-existent ID")
	public void getNotFoundPetTest() {
		String notFoundId = "-100500";
		petSteps.getNotFoundPetById(notFoundId);
	}
}

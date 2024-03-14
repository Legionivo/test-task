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
@Story("Delete")
@Feature("Pet")
@DisplayName("Delete Pet")
public class DeletePetTest {

	private final Pet fullDataPet = TestDataGenerator.generatePetWillFullData();
	private final PetSteps petSteps = new PetSteps();

	@Test
	@DisplayName("Deleting a pet")
	public void deletePet() {
		petSteps.createPetSuccessfully(fullDataPet)
				.deletePetById(fullDataPet.getId().toString())
				.getNotFoundPetById(fullDataPet.getId().toString());
	}

	@Test
	@DisplayName("Trying to delete a pet using a non-existent ID")
	public void deleteNotFoundPetTest() {
		petSteps.deleteNotFoundPetById("-10");
	}
}

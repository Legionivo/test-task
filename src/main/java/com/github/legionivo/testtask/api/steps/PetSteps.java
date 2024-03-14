package com.github.legionivo.testtask.api.steps;

import com.github.legionivo.testtask.api.controllers.PetController;
import com.github.legionivo.testtask.api.models.apiResponse.ApiResponse;
import com.github.legionivo.testtask.api.models.pet.Pet;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class PetSteps  {
    PetController petController = new PetController();

    @Step("Successful post of a new pet {pet}")
    public PetSteps createPetSuccessfully(Pet pet) {
        Response response = petController.postPet(pet);
        assertStatusCode(SC_OK, response);
        return this;
    }

    @Step("Post invalid object {pet}")
    public PetSteps postBadRequest(Object pet) {
        Response response = petController.postPet(pet);
        assertStatusCode(SC_BAD_REQUEST, response);
        return this;
    }

    @Step("Successful get pet by ID {petId}")
    public Pet getPetById(String petId) {
        Response response = petController.getPet(petId);
        assertStatusCode(SC_OK, response);
        return response.as(Pet.class);
    }

    @Step("Pet not found on get pet by id {petId}")
    public PetSteps getNotFoundPetById(String petId) {
        Response response = petController.getPet(petId);
        assertStatusCode(SC_NOT_FOUND, response);
        assertErrorMessage("Pet not found", response.as(ApiResponse.class));
        return this;
    }

    @Step("Checking pet fields {expectedPet}")
    public PetSteps assertPetData(Pet expectedPet) {
        Pet pet = getPetById(expectedPet.getId().toString());
        assertThat(pet, is(equalTo(expectedPet)));
        return this;
    }

    @Step("Successful delete of existing pet by id {petId}")
    public PetSteps deletePetById(String petId) {
        Response response = petController.deletePet(petId);
        assertStatusCode(SC_OK, response);
        return this;
    }

    @Step("Trying to delete a non-existent pet by id {petId}")
    public PetSteps deleteNotFoundPetById(String petId) {
        Response response = petController.deletePet(petId);
        assertStatusCode(SC_NOT_FOUND, response);
        return this;
    }

    @Step("Successful pet data update {pet}")
    public PetSteps putPetSuccessfully(Pet pet) {
        Response response = petController.putPet(pet);
        assertStatusCode(SC_OK, response);
        assertPetData(pet);
        return this;
    }

    @Step("Put invalid object {pet}")
    public PetSteps putBadRequest(Object pet) {
        Response response = petController.putPet(pet);
        assertStatusCode(SC_BAD_REQUEST, response);
        return this;
    }

    @Step("Checking the status code {expectedStatus}")
    private void assertStatusCode(int expectedStatus, Response response) {
        assertThat(response.statusCode(), equalTo(expectedStatus));
    }

    @Step("Checking the message {expectedMessage}")
    public void assertErrorMessage(String expectedMessage, ApiResponse apiResponse) {
        assertThat(apiResponse.getMessage(), equalTo(expectedMessage));
    }

}

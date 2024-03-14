package com.github.legionivo.testtask.api.controllers;

import com.github.legionivo.testtask.api.Authentication;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static com.github.legionivo.testtask.utils.ServerConfig.BASE_CONFIG;
import static io.restassured.RestAssured.given;

public class PetController {

	public PetController() {
		RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder()
				.setBaseUri(BASE_CONFIG.baseUri())
				.setBasePath(BASE_CONFIG.basePath())
				.setRelaxedHTTPSValidation()
				.addHeader("api_key", Authentication.getApiKey())
				.setContentType(ContentType.JSON)
				.addFilter(new AllureRestAssured()
						.setRequestTemplate("http-request.ftl")
						.setResponseTemplate("http-response.ftl"))
				.log(LogDetail.ALL);

		RestAssured.requestSpecification = requestSpecBuilder.setRelaxedHTTPSValidation().build();

		ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder()
				.expectContentType(ContentType.JSON);
		responseSpecBuilder.build();
	}

	public Response getPet(String petId) {
		return given()
				.when()
				.get(petId);
	}

	public Response postPet(Object pet) {
		return given()
				.when()
				.body(pet)
				.post();
	}

	public Response putPet(Object pet) {
		return given()
				.when()
				.body(pet)
				.put();
	}

	public Response deletePet(String petId) {
		return given()
				.when()
				.delete(petId);
	}
}

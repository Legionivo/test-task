package com.github.legionivo.testtask.api;

import com.github.legionivo.testtask.api.models.pet.Category;
import com.github.legionivo.testtask.api.models.pet.Pet;
import com.github.legionivo.testtask.api.models.pet.Status;
import com.github.legionivo.testtask.api.models.pet.Tag;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Arrays;
import java.util.Random;

public class TestDataGenerator {

	public static Pet generatePetWithMinimumData() {
		return Pet.builder()
				.id(getRandomInt())
				.name(getRandomString())
				.build();
	}

	public static Pet generatePetWillFullData() {
		return Pet.builder()
				.id(getRandomInt())
				.category(Category.builder().id(getRandomInt()).name(getRandomString()).build())
				.name(getRandomString())
				.photoUrls(Arrays.asList(getRandomString(), getRandomString()))
				.tags(Arrays.asList(Tag.builder().id(getRandomInt()).name(getRandomString()).build(),
						Tag.builder().id(getRandomInt()).name(getRandomString()).build()))
				.status(Status.available)
				.build();
	}

	private static Integer getRandomInt() {
		return new Random().nextInt((65536) - 32768);
	}

	private static String getRandomString() {
		return RandomStringUtils.randomAlphabetic(10);
	}
}

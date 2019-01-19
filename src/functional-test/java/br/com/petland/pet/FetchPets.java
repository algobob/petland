package br.com.petland.pet;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;

import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static java.util.Arrays.asList;

import br.com.petland.FunctionalTestClass;
import br.com.petland.pet.enums.*;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class FetchPets extends FunctionalTestClass {

    @Test
    public void shouldReturnNotPetsWhenThereIsNone() {
        Response response = given().contentType("application/json").when().get("/pets");

        JsonPath jsonPathEvaluator = response.jsonPath();

        assertThat(jsonPathEvaluator.getString("status"), is("not_found"));
        assertThat(jsonPathEvaluator.getInt("code"), is(404));
        assertThat(jsonPathEvaluator.getList("messages"), is(asList("No pets found. Please, add some pets first =)")));
        assertThat(jsonPathEvaluator.getList("data"), is(asList()));

    }

    @Test
    public void shouldReturnAllPets() {

        Pet biju = Pet.builder()
            .name("Biju")
            .gender(PetGender.FEMALE)
            .age(1)
            .type(PetType.DOG)
            .breed(PetBreed.VIRA_LATA)
            .creationDate(LocalDate.now())
            .build();

            Pet luphie = Pet.builder()
            .name("Luphie")
            .gender(PetGender.FEMALE)
            .age(2)
            .type(PetType.DOG)
            .breed(PetBreed.LABRADOR)
            .creationDate(LocalDate.now())
            .build();
            
            Pet xeppy = Pet.builder()
            .name("Xeppy")
            .gender(PetGender.FEMALE)
            .age(1)
            .type(PetType.CAT)
            .creationDate(LocalDate.now())
            .build();

        repositoryHelper.insertPet(biju);
        repositoryHelper.insertPet(luphie);
        repositoryHelper.insertPet(xeppy);

        Response response = given().contentType("application/json").when().get("/pets");

        JsonPath jsonPathEvaluator = response.jsonPath();

        assertThat(jsonPathEvaluator.getString("status"), is("ok"));
        assertThat(jsonPathEvaluator.getInt("code"), is(200));
        assertThat(jsonPathEvaluator.getList("messages"), is(nullValue()));
        assertThat(jsonPathEvaluator.getList("data"), is(asList(xeppy, luphie, biju)));

    }

}
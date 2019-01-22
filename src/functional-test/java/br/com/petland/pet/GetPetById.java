package br.com.petland.pet;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertThat;

import static java.util.Arrays.asList;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import com.google.inject.Guice;
import com.google.inject.Injector;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import br.com.petland.Application;
import br.com.petland.FunctionalTestClass;
import br.com.petland.PetModuleForTest;
import br.com.petland.RepositoryHelper;
import br.com.petland.Resource;
import br.com.petland.pet.enums.PetGender;
import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import xyz.morphia.Key;

// @Ignore
public class GetPetById extends FunctionalTestClass {

    @Test
    public void shouldReturnPetSuccessfully(){
        Pet expectedPet = Pet.builder().name("Luphie").age(12).gender(PetGender.FEMALE).build();
        Key<Pet> key = repositoryHelper.insertPet(expectedPet);
        Response response = given().pathParams("id", key.getId().toString()).when().get("/pets/{id}");

        JsonPath jsonPathEvaluator = response.jsonPath();

        assertThat(jsonPathEvaluator.getString("status"), is("ok"));
        assertThat(jsonPathEvaluator.getInt("code"), is(200));
        assertThat(jsonPathEvaluator.getList("messages"), is(nullValue()));
        assertThat(Pet.fromJson(jsonPathEvaluator.getJsonObject("data").toString()), is(expectedPet));
    }

    @Test
    public void shouldNotFoundPetSuccessfully(){
        Response response = given().pathParams("id", "516d41150364a6a6697136c0").when().get("/pets/{id}");

        JsonPath jsonPathEvaluator = response.jsonPath();

        assertThat(jsonPathEvaluator.getString("status"), is("not_found"));
        assertThat(jsonPathEvaluator.getInt("code"), is(404));
        assertThat(jsonPathEvaluator.getList("messages"), is(asList("Pet not found.")));
        assertThat(jsonPathEvaluator.getJsonObject("data"), is(nullValue()));
    }

}
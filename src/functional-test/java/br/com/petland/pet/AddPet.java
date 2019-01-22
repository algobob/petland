package br.com.petland.pet;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;

import static java.util.Arrays.asList;

import static org.hamcrest.Matchers.*;

import com.google.gson.Gson;
import com.google.inject.Guice;
import com.google.inject.Injector;

import org.hamcrest.CoreMatchers;
import org.junit.After;
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

// @Ignore
public class AddPet extends FunctionalTestClass {

    @Test
    public void shouldReturnBadRequestWhenPetNotProvideProperly(){
        Response response = given().contentType("application/json").body("").when().post("/pets");

        JsonPath jsonPathEvaluator = response.jsonPath();

        assertThat(jsonPathEvaluator.getString("status"), is("bad_request"));
        assertThat(jsonPathEvaluator.getInt("code"), is(400));
        assertThat(jsonPathEvaluator.getList("messages"), is(asList("Bad request =(. Did you provide the data correctly?")));
    }

    @Test
    public void shouldAddPetSuccesfully(){
        Pet pet = Pet.builder().age(10).creationDate(LocalDate.now()).name("luphie").gender(PetGender.FEMALE).build();
        Response response = given().contentType("application/json").body(pet.toJson()).when().post("/pets");

        JsonPath jsonPathEvaluator = response.jsonPath();

        assertThat(jsonPathEvaluator.getString("status"), is("ok"));
        assertThat(jsonPathEvaluator.getInt("code"), is(200));
        assertThat(jsonPathEvaluator.getList("messages"), is(nullValue()));
        assertThat(jsonPathEvaluator.getJsonObject("data").toString(), is(not(nullValue())));
    }

}
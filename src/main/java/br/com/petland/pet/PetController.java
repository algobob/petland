package br.com.petland.pet;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.petland.Resource;
import br.com.petland.pet.enums.PetGender;
import br.com.petland.pet.fetchPets.SearchCriteria;
import lombok.NoArgsConstructor;
import spark.Request;
import spark.Response;

@NoArgsConstructor
public class PetController {
	
	private static Logger logger = LoggerFactory.getLogger(PetController.class);

	@Inject
	private PetService petService;
	
	public PetController(PetService petService) {
		this.petService =  petService;
	}

	public Resource<Pet> getPet(Request request, Response response) {
		
		logger.info("Get Pet by Id", request);
		response.type("application/json");
		
		try {
			String id = request.params("id");
			Pet pet = petService.getPet(id);
	
			if (pet == null)
				return Resource.notFound(pet, "Pet not found.");
			
			return Resource.success(pet); 
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			return Resource.error("Something crazy hapenned. Help!");
		}
	}

	public Resource<String> addPet(Request req, Response res) {

		logger.info("Pet Controller - Add new pet");

		try {
			res.type("application/json");
			String id = petService.addPet(getPetFromRequest(req));
			return Resource.success(id);
		} catch (IllegalStateException e) {
			logger.error("Error when tried to add a pet: "+e.getLocalizedMessage());
			return Resource.badRequest("Bad request =(. Did you provide the data correctly?");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			return Resource.error("Something crazy hapenned. Help!");
		}
		
	}

	public Resource<List<Pet>> fetchPets(Request req, Response res) {
		SearchCriteria searchCriteria = SearchCriteria.builder().build();
		return Resource.success(petService.fetchPets(searchCriteria));
	}

	private Pet getPetFromRequest(Request req) {
		JsonParser parser = new JsonParser();
		JsonObject json = parser.parse(req.body()).getAsJsonObject();
		
		return Pet.builder()
			.age(json.get("age").getAsInt())
			.creationDate(LocalDate.now())
			.name(json.get("name").getAsString())
			.gender(PetGender.valueOf(json.get("gender").getAsString()))
			.build();
	}
}

package br.com.petland.pet;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import br.com.petland.Resource;
import br.com.petland.pet.Pet;
import br.com.petland.pet.PetController;
import br.com.petland.pet.PetService;
import br.com.petland.pet.fetchPets.SearchCriteria;

import org.bson.types.ObjectId;
import spark.Request;
import spark.Response;

public class PetControllerTest {
	
	@Test
	public void shouldGetPetFromService() {
		String id = "5c36b7fa868e67557c764e3a";
		Pet pet = Pet.builder().id(new ObjectId(id)).name("Luphie").age(12).sex("female").build();
		Resource<Pet> expected = Resource.success(pet);
		
		PetService service = mock(PetService.class);
		Request request = mock(Request.class);
		Response response = mock(Response.class);
		
		when(service.getPet(id)).thenReturn(pet);
		when(request.params("id")).thenReturn(id);
		PetController controller = new PetController(service);
		
		assertThat(controller.getPet(request, response), is(expected));
	}

	@Test
	public void shouldCallServiceWhenAddPet() {
		Pet pet = Pet.builder().name("Luphie").age(12).sex("female").build();
		Resource<String> expected = Resource.success("1");
		
		PetService service = mock(PetService.class);
		Request request = mock(Request.class);
		Response response = mock(Response.class);
		
		when(service.addPet(any())).thenReturn("1");
		when(request.body()).thenReturn(pet.toJson());
		PetController controller = new PetController(service);
		
		assertThat(controller.addPet(request, response), is(expected));
	}

	@Test
	public void shouldFetchNoPetsWhenThereAreNoneAdded() {
		List<Pet> noPets = Collections.EMPTY_LIST;
		Resource<List<Pet>> expected = Resource.notFound(noPets, "No pets found. Please, add some pets first =)");
		
		PetService service = mock(PetService.class);
		Request request = mock(Request.class);
		Response response = mock(Response.class);
		
		SearchCriteria searchCriteria = SearchCriteria.builder().build();
		when(service.fetchPets(searchCriteria)).thenReturn(noPets);
		
		PetController controller = new PetController(service);
		
		assertThat(controller.fetchPets(request, response), is(expected));

		verify(service).fetchPets(searchCriteria);
	}

}

package br.com.petland.pet;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

import java.util.Collections;

import javax.inject.Inject;

import com.google.inject.Guice;
import com.google.inject.Injector;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import br.com.petland.PetModuleForTest;
import br.com.petland.pet.DataRepository;
import br.com.petland.pet.Pet;
import br.com.petland.pet.PetService;
import br.com.petland.pet.enums.PetGender;
import br.com.petland.pet.fetchPets.SearchCriteria;

import org.bson.types.ObjectId;
@RunWith(MockitoJUnitRunner.class)
public class PetServiceTest {

	@Mock
	private DataRepository repository;

	@InjectMocks
	private PetService service;

	@Before
    public void setUp() {
		Injector injector = Guice.createInjector(new PetModuleForTest());
		injector.injectMembers(this);
    }
	
	@Test
	public void shouldGetPetFromRepository() {
		String id = "5c36b7fa868e67557c764e3a";
		Pet expectedPet = Pet.builder().id(new ObjectId(id)).name("luphie").age(2).gender(PetGender.FEMALE).build();
		when(repository.getById(id)).thenReturn(expectedPet);
		
		assertThat(service.getPet(id), is(expectedPet));
		verify(repository).getById(id);
	}

	@Test
	public void shouldReturnNullWhenNotFindPetFromRepository() {
		String UNKNOWN_ID = "5c36b7fa868e67557c764e3a";
		when(repository.getById(UNKNOWN_ID)).thenReturn(null);
		
		assertThat(service.getPet(UNKNOWN_ID), is(nullValue()));
		verify(repository).getById(UNKNOWN_ID);
	}

	@Test
	public void shouldAddPetUsingRepository() {
		Pet expectedPet = Pet.builder().name("luphie").age(2).gender(PetGender.FEMALE).build();
		when(repository.add(expectedPet)).thenReturn("1");
		
		assertThat(service.addPet(expectedPet), is("1"));
		verify(repository).add(expectedPet);
	}

	@Test
	public void shouldReturnEmptyListWhenFindNoPets() {
		SearchCriteria searchCriteria = SearchCriteria.builder().build();
				
		when(repository.fetchPets(searchCriteria)).thenReturn(Collections.emptyList());
		
		assertThat(service.fetchPets(searchCriteria), is(Collections.emptyList()));
		verify(repository).fetchPets(searchCriteria);
	}

}

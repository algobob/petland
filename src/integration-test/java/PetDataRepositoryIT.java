import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import java.util.Collections;

import javax.inject.Inject;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mongodb.ServerAddress;

import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.petland.PetModule;
import br.com.petland.PetModuleForTest;
import br.com.petland.RepositoryHelper;
import br.com.petland.pet.DataRepository;
import br.com.petland.pet.Pet;
import br.com.petland.pet.PetDataRepository;
import br.com.petland.pet.enums.PetBreed;
import br.com.petland.pet.enums.PetGender;
import br.com.petland.pet.fetchPets.SearchCriteria;
import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;
import xyz.morphia.Key;

public class PetDataRepositoryIT {
	
	private RepositoryHelper repositoryHelper;

	@Inject
	private DataRepository<Pet> repository;

	private MongoServer server;

	private String id;

	@Before
    public void setUp() {
		Injector injector = Guice.createInjector(new PetModuleForTest());
		injector.injectMembers(this);
		server = new MongoServer(new MemoryBackend());
		server.bind("localhost", 27017);
		
		repositoryHelper = new RepositoryHelper();

    }

    @After
    public void tearDown() {
		server.shutdown();
    }

	@Test
	public void shouldGetPetFromRepository() {
		Pet pet = Pet.builder().name("Luphie").age(12).gender(PetGender.FEMALE).build();

		Key<Pet> key = repositoryHelper.insertPet(pet);
		
		assertThat(repository.getById(key.getId().toString()), is(pet));

		repositoryHelper.removePet(pet);

	}

	@Test
	public void shouldGetNullWhenPetNotFound() {

		Pet pet = Pet.builder().breed(PetBreed.LABRADOR).name("Luphie").age(12).gender(PetGender.FEMALE).build();

		Key<Pet> key = repositoryHelper.insertPet(pet);
		
		assertThat(repository.getById("5c36b7fa868e67557c764e3a"), is(nullValue()));

	}

	@Test
	public void shouldAddPet() {
		Pet pet = Pet.builder().name("Luphie").age(12).gender(PetGender.FEMALE).build();
		String id = repository.add(pet);
		assertThat(id, is(not(nullValue())));
	}

	@Test
	public void shouldReturnEmptyListWhenFindNoPets() {
		SearchCriteria searchCriteria = SearchCriteria.builder().build();
		
		assertThat(repository.fetchPets(searchCriteria), is(Collections.EMPTY_LIST));

	}
}

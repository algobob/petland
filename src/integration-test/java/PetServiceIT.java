import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;
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
import br.com.petland.pet.Pet;
import br.com.petland.pet.PetService;
import br.com.petland.pet.enums.PetGender;
import br.com.petland.pet.fetchPets.SearchCriteria;
import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;
import xyz.morphia.Key;

public class PetServiceIT {
	
	private RepositoryHelper repositoryHelper;

	@Inject
	private PetService service;

	private MongoServer server;

	private String id;

	private Pet pet;

	@Before
    public void setUp() {
		Injector injector = Guice.createInjector(new PetModuleForTest());
		injector.injectMembers(this);
		server = new MongoServer(new MemoryBackend());
		server.bind("localhost", 27017);
		
		repositoryHelper = new RepositoryHelper();
		id = "5c36b7fa868e67557c764e3a";
		pet = Pet.builder().id(new ObjectId(id)).name("Luphie").age(12).gender(PetGender.FEMALE).creationDate(LocalDate.now()).build();

    }

    @After
    public void tearDown() {
        server.shutdown();
    }

	@Test
	public void shouldGetPet() {
		repositoryHelper.insertPet(pet);
		assertThat(service.getPet(id), is(pet));
    }

	@Test
	public void shouldGetNullWhenPetNotFound() {
		Key<Pet> key = repositoryHelper.insertPet(pet);
		assertThat(service.getPet(key.getId().toString().replace('b', 'c')), is(nullValue()));
	}

	@Test
	public void shouldReturnEmptyListWhenNotFoundPets() {
		SearchCriteria searchCriteria = SearchCriteria.builder().build();
		assertThat(service.fetchPets(searchCriteria), is(Collections.emptyList()));
	}

	@Test
	public void shouldAddPet() {
		assertThat(service.addPet(pet),is(not(nullValue())));
	}

}

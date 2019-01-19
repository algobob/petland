package br.com.petland;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

import br.com.petland.pet.Pet;
import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;
import xyz.morphia.Datastore;
import xyz.morphia.Key;
import xyz.morphia.Morphia;

public class RepositoryHelper {

	private Datastore datastore;
    private MongoClient mongoClient;

    public RepositoryHelper(){
        Morphia morphia = new Morphia();

        morphia.mapPackage("br.com.petland");

        mongoClient = new MongoClient();
        Datastore datastore = morphia.createDatastore(mongoClient, "integration_test");
		datastore.ensureIndexes();
        this.datastore = datastore;
    }

    public Datastore getDataStore() {
        return this.datastore;
    }

    public Key<Pet> insertPet(Pet pet){
        return datastore.save(pet);
    }

    public void removePet(Pet pet) {
        datastore.delete(pet);        
    }

    public void shutdown() {
        mongoClient.close();
    }
}
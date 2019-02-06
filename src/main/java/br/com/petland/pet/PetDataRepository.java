package br.com.petland.pet;

import java.util.List;

import com.mongodb.MongoClient;

import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;

import br.com.petland.pet.fetchPets.SearchCriteria;
import xyz.morphia.Datastore;
import xyz.morphia.Morphia;
import xyz.morphia.query.Query;

public class PetDataRepository implements DataRepository<Pet> {

	private Datastore datastore;

    public PetDataRepository() {
        Morphia morphia = new Morphia();

        morphia.mapPackage("br.com.petland");

        MongoClient mongoClient = new MongoClient();
        Datastore datastore = morphia.createDatastore(mongoClient, "petland");
		datastore.ensureIndexes();
        this.datastore = datastore;
    }

    public PetDataRepository(Datastore datastore) {
        this.datastore = datastore;
    }

    @Override
    public Pet getById(String id) {
        return datastore.get(Pet.class, new ObjectId(id));
    }

    @Override
    public String add(Pet pet) {
        return datastore.save(pet).toString();
    }

    @Override
    public List<Pet> fetchPets(SearchCriteria searchCriteria) {
        return buildFetchQuery(searchCriteria).asList();
    }
    
    private Query<Pet> buildFetchQuery(SearchCriteria searchCriteria) {
        
        Query<Pet> query = datastore.createQuery(Pet.class);

        if (StringUtils.isNotBlank(searchCriteria.getName()))
            query.field("name").equalIgnoreCase(searchCriteria.getName());

        if (StringUtils.isNotBlank(searchCriteria.getBreed()))
            query.field("breed").equalIgnoreCase(searchCriteria.getBreed());
        
        // if (StringUtils.isNotBlank(searchCriteria.getAge()))
        //     query.field("age").equal(Integer.valueOf(searchCriteria.getAge()));
            
        // if (StringUtils.isNotBlank(searchCriteria.getSex()))
        //     query.field("gender").equalIgnoreCase(searchCriteria.getSex());
            
        return query;    
    }

}
package br.com.petland;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.TypeLiteral;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.petland.pet.DataRepository;
import br.com.petland.pet.Pet;
import br.com.petland.pet.PetController;
import br.com.petland.pet.PetDataRepository;
import br.com.petland.pet.PetService;
import xyz.morphia.Datastore;

public class PetModule extends AbstractModule{

    private static Logger logger = LoggerFactory.getLogger(PetController.class);

    @Override
    protected void configure() {
        try {
            bind(new TypeLiteral<DataRepository<Pet>>(){}).to(PetDataRepository.class).in(Scopes.SINGLETON);
            bind(PetService.class);
            bind(PetController.class);
        } catch(Exception ex) {
            logger.error("fudeuuuuuu: "+ ex);
        }
    }

}
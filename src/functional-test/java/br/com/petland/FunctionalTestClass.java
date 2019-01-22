package br.com.petland;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mongodb.MongoClientOptions;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

import br.com.petland.Application;
import br.com.petland.RepositoryHelper;
import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;
import io.restassured.RestAssured;

public class FunctionalTestClass {

    protected static MongoServer server;
    protected  static RepositoryHelper repositoryHelper;
    protected  static Application app;
    private static boolean isRunning = false;

    @BeforeClass
    public static void setup(){
        
        if (!isRunning) {
            
            server = new MongoServer(new MemoryBackend());
            server.bind("localhost", 27017);
            
            repositoryHelper = new RepositoryHelper();

            app = new Application(true);
            int port = app.start();

            RestAssured.baseURI = "http://localhost/api";
            RestAssured.port = port;

            isRunning = true;
        }
    }

    public void setup2() {
        Injector injector = Guice.createInjector(new PetModuleForTest());
        injector.injectMembers(this);
    }
}
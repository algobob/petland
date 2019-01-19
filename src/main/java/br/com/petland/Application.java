package br.com.petland;

import static spark.Spark.*;

import javax.inject.Inject;

import com.google.inject.Guice;
import com.google.inject.Injector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.petland.pet.PetController;
import spark.Spark;

public class Application {

    private static Logger logger = LoggerFactory.getLogger(Application.class);

    @Inject
    private PetController controller;

    public Application() {
        Injector injector = Guice.createInjector(new PetModule());
        injector.injectMembers(this);
    }

    public Application(boolean testing) {
        Injector injector = Guice.createInjector(new PetModuleForTest());
        injector.injectMembers(this);
    }

    public int start() {
        port(8080);
        init();
        awaitInitialization();
        routes();
        return port();
    }

    public void routes() {
        path("/api", () -> {
            get("/pets/:id", (req,res) -> controller.getPet(req, res).toJson());
            post("/pets", (req, res) -> controller.addPet(req, res).toJson());
            get("/pets", (req, res) -> controller.fetchPets(req, res).toJson());
        });

        // get("/pet/:id", (req, res) -> {
        //     res.type("application/json");
        //     return controller.getPet(req, res).toJson();
        // });

        // post("/pet/:id", (req, res) -> {
        //     res.type("application/json");
        //     return controller.getPet(req, res).toJson();
        // });
    }

    public void stop() {
        stop();
    }

    public static void main(String[] args) {
        new Application().start();
    }
}
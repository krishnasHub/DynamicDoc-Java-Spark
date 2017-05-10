package dyndoc.rest;

import dyndoc.controllers.BaseController;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.Set;

import static spark.Spark.*;

/**
 * Created by krish on 5/9/2017.
 *
 * This is the REST layer for our app. It has a basic health check that we initialize to make sure that our
 * app is set to the tight version.
 * For all other paths, we call the DocController to give us the right controller and use that controller.
 */
public class RestLayer {
    /**
     * Logger for this class.
     */
    private static Logger LOG = LoggerFactory.getLogger(RestLayer.class);

    /**
     * The entry point to our app.
     * @param args - String arguments that we're not using as of now.
     */
    public static void main(String[] args) throws Exception {
        // Set the reflection framework.
        Reflections reflections = new Reflections("dyndoc.controllers");

        // Get a set of all subclasses of DocController
        Set<Class<? extends BaseController>> controllers = reflections.getSubTypesOf(BaseController.class);

        // For every subclass found, register it to it's path and add a mapping for it.
        for(Class<? extends BaseController> con : controllers) {
            BaseController c = con.newInstance();

            // Initialize the new controller.
            c.Init();

            // Register all routes in it..
            c.RegisterRoutes();
        }

        // The health check request.
        get("/v1/health", (req, res) -> {
            LOG.info("GET request found. Returning OK.");

            return "OK";
        });

    }
}

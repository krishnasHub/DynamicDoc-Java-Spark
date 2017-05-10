package dyndoc.rest;

import dyndoc.controllers.DocController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.DataContentHandler;

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

        // Initialize the controllers.
        DocController.Init();

        // The health check request.
        get("/v1/health", (req, res) -> {
            LOG.info("GET request found. Returning OK.");

            return "OK";
        });

        // Login function.
        get("/v1/login", (req, res) -> {
            LOG.info("Login triggered..");
            return DocController.getController(req).handle(req, res);
        });


    }
}

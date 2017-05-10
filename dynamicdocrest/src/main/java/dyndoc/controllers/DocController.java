package dyndoc.controllers;

import dyndoc.util.Constants;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;


import java.util.*;

/**
 * Created by krish on 5/9/2017.
 * The base class of all the controllers. This is an abstract class that has common code to register all
 * controllers to a path and builds the map the first time around.
 *
 * Subsequent calls should simply pass the right controller to the calling rest layer.
 */
public abstract class DocController {
    /**
     * Logger for this class.
     */
    private static Logger LOG = LoggerFactory.getLogger(DocController.class);

    /**
     * The Controller map held by this base class.
     */
    private static Map<String, DocController> controllableMap = new HashMap<>();

    /**
     * The path to ap all controllers.
     */
    private String path;


    /**
     * Initialize the controller. Build all the maps and initialize the controllers within.
     * @throws Exception - In case something goes bump when initializing..
     */
    public static void Init() throws Exception {
        buildMap();
    }

    /**
     * The static function that, for the request coming in, builds the map if needed
     * and fetches the right Controller for the path passed, based on the Controller's subscription.
     *
     * @param request - the request that came in.
     * @return DocController - The controller that has registered itself to this path.
     * @throws Exception - In case no proper Controller was mapped.
     */
    public static DocController getController(Request request) throws Exception  {
        LOG.info("Looking for a mapping to '" + request.pathInfo() + "' in " + controllableMap);

        return controllableMap.get(request.pathInfo());
    }

    /**
     * Builds the map of the path vs Controller to call.
     * Each Controller is supposed to set it's own path variable to the path it wishes
     * to subscribe to.
     *
     * @throws Exception - In case the Controller is not properly mapped to any path.
     */
    private static void buildMap() throws Exception {
        if(controllableMap.size() != 0)
            return;

        LOG.info("Building Map..");
        // Set the reflection framework.
        Reflections reflections = new Reflections("dyndoc.controllers");

        // Get a set of all subclasses of DocController
        Set<Class<? extends DocController>> controllers = reflections.getSubTypesOf(DocController.class);

        // For every subclass found, register it to it's path and add a mapping for it.
        for(Class<? extends DocController> con : controllers) {
            DocController c = con.newInstance();

            // Initialize the new controller.
            c.Initialize();

            LOG.info("Adding mapping for '" + c.getPath() + "' to " + con.getName());
            controllableMap.put(Constants.VERSION + c.getPath(), c);
        }
    }

    /**
     * Simple getter for Path
     * @return String - The Path value.
     */
    public String getPath() {
        return path;
    }

    /**
     * Simple Setter for this Controller.
     * @param path - Sets the path value this Controller wishes to subscribe to.
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * The abstract method we want to implement in order to handle the request and send out a response.
     * @param request - The incoming request.
     * @param response - The response object to set that can be returned.
     * @return - The Object to return in this REST call.
     * @throws Exception - In case of any errors, throw an Exception.
     */
    public abstract Object handle(Request request, Response response) throws Exception;


    /**
     * Initialize any data oriented functions before we start using this Controller.
     * This initialization should be done in async to avoid any performance delays.
     *
     * @throws Exception - In case something goes wrong..
     */
    public abstract void Initialize() throws Exception;
}

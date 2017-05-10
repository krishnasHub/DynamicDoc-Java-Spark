package dyndoc.controllers;

/**
 * Created by vjayanthi on 5/10/17.
 *
 * Thi is the base class for all controllers. We should include all common functionality
 * into this class.
 * All controllers should subclass this one and implement the following methods..
 */
public abstract class BaseController {

    /**
     * Initialize the controller and any subsequent layers.
     * @throws Exception - In case something goes bump.
     */
    public abstract void Init() throws Exception;

    /**
     * Registers all the routes to whatever work that controller has to do.
     * @throws Exception - In case something goes bad.
     */
    public abstract void RegisterRoutes() throws Exception;
}

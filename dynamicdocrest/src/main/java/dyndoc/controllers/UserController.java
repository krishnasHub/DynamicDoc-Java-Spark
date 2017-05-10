package dyndoc.controllers;

import dyndoc.firebase.LoginDataAccess;
import dyndoc.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static spark.Spark.*;

/**
 * Created by vjayanthi on 5/10/17.
 * The Controller that handles all User related stuff like login, CRUD operations on user.
 */
public class UserController extends BaseController {
    /**
     * Logger for this class.
     */
    private static Logger LOG = LoggerFactory.getLogger(UserController.class);

    /**
     * Initialize the Login Data Access to pull in all User data we need.
     * @throws Exception - In case we could not get hands on the data.
     */
    @Override
    public void Init() throws Exception {
        LOG.info("Initializing the controller..");
        LoginDataAccess.Initialize();
    }

    /**
     * Registers all the controller's calls.
     * As of now, it has login and logout.
     * @throws Exception - In case there's an error, throw an exception.
     */
    @Override
    public void RegisterRoutes() throws Exception {
        // The login part..
        get(Constants.VERSION + "/login/:name/:password", (req, res) -> {
            String name = req.params(":name");
            String password = req.params(":password");

            LOG.info("Login called for " + name);
            LoginDataAccess loginDataAccess = new LoginDataAccess();
            return loginDataAccess.getData();
        });

        // The logout part..
        get(Constants.VERSION + "/logout/:name", (req, res) -> {
            String name = req.params(":name");

            LOG.info("Logout called for " + name);
            LoginDataAccess loginDataAccess = new LoginDataAccess();
            return loginDataAccess.getData();
        });
    }
}

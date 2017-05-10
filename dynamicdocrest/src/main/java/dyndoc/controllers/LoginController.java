package dyndoc.controllers;

import dyndoc.firebase.LoginDataAccess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

/**
 * Created by krish on 5/9/2017.
 *
 * A sample controller built as  POC. Need to add more into this in order to actually do some
 * login stuff.
 */
public class LoginController extends DocController {
    /**
     * Logger for this class.
     */
    private static Logger LOG = LoggerFactory.getLogger(LoginController.class);


    /**
     * Basic constructor that needs to set the path variable to something in order to register it.
     */
    public LoginController() {
        setPath("/login");
    }

    /**
     * Does the login functionality.
     * TODO - the actual login is not yet implemented. Need to work on this.
     *
     * @param request - The incoming request.
     * @param response - The response object to set that can be returned.
     * @return JSON - A json object that gives details about this login.
     * @throws Exception - In case an error in the information passed on.
     */
    @Override
    public Object handle(Request request, Response response) throws Exception {
        LOG.info("Login controller called.");
        LoginDataAccess loginDataAccess = new LoginDataAccess();
        return loginDataAccess.getData();
    }

    /**
     * Starts the Login data async..
     * @throws Exception - In case we could not reach firebase.
     */
    @Override
    public void Initialize() throws Exception {
        LOG.info("Initializing the controller..");
        LoginDataAccess.Initialize();
    }
}

package dyndoc.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import static spark.Spark.*;

/**
 * Created by krish on 5/9/2017.
 */
public class RestLayer {
    private static Logger log = LoggerFactory.getLogger(RestLayer.class);

    public static void main(String[] args) {
        get("/health/v1", (req, res) -> {
            log.info("GET request found. Returning OK.");

            return "OK";
        });
    }
}

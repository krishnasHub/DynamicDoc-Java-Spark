package dyndoc.rest;

import static spark.Spark.*;

/**
 * Created by krish on 5/9/2017.
 */
public class RestLayer {

    public static void main(String[] args) {
        get("/health/v1", (req, res) -> "OK");
    }
}

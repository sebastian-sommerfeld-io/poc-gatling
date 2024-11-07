package io.sommerfeld.petclinic;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;

/**
 * The SystemUnderTest class provides methods to interact with the application
 * which is targeted by the load test.
 */
public class SystemUnderTest {

    public static final String BASE_URL = System.getenv("SYSTEM_UNDER_TEST");

    private static final int HTTP_OK = 200;

    public static ChainBuilder requestHomepage() {
        return exec(http("GET homepage")
                .get("/")
                .check(status().is(HTTP_OK)));
    }

    public static ChainBuilder requestFindOwnersPage() {
        return exec(http("GET find owners page")
                .get("/owners/find")
                .check(status().is(HTTP_OK)));
    }
}

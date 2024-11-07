package io.sommerfeld.petclinic;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import java.time.Duration;

// 1. small constant load
// 2. small constant load

// use params for /owners?lastName=

// Refactor this class into ConstantLoadSimulation
// Create new class IncreasingLoadSimulation
// ??? does this result in 1 single report with all simulations ???

/**
 * TODO ...
 */
public class ConstantLoadSimulation extends Simulation {

    private static final Duration SIM_DURATION = Duration.ofSeconds(120);
    private static final Duration START_DELAY = Duration.ofSeconds(10);
    private static final int USERS_PER_SECOND = 50;

    HttpProtocolBuilder httpProtocol = http.baseUrl(SystemUnderTest.BASE_URL)
            .acceptHeader("text/html,application/xhtml+xml,application/xml")
            .acceptEncodingHeader("gzip, deflate")
            .acceptLanguageHeader("en-US,en;q=0.5")
            .userAgentHeader(
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3");

    ScenarioBuilder scn = scenario("Constant Load Scenario")
            .exec(SystemUnderTest.requestHomepage())
            .exec(SystemUnderTest.requestFindOwnersPage());

    {
        System.out.println("================================================================================");
        System.out.println("System under test\n\t" + SystemUnderTest.BASE_URL);
        System.out.println("================================================================================");

        setUp(
                scn.injectOpen(
                        nothingFor(START_DELAY),
                        constantUsersPerSec(USERS_PER_SECOND).during(SIM_DURATION)))
                .protocols(httpProtocol)
                .assertions(
                        global().failedRequests().percent().is(0.0),
                        forAll().responseTime().percentile3().lt(500) // Ensures 95th percentile response < 500ms
                );

    }

}

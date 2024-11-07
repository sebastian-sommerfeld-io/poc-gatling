package io.sommerfeld.petclinic;

import static io.gatling.javaapi.core.CoreDsl.*;

import io.gatling.javaapi.core.*;
import java.time.Duration;

/**
 * The ConstantLoadSimulation class provides a Gatling simulation that puts a
 * constant load on the system under test for a fixed duration.
 *
 * The simulation injects a constant number of users per second and measures the
 * response time and the percentage of failed requests.
 *
 */
public final class ConstantLoadSimulation extends Simulation {

    private static final Duration START_DELAY = Duration.ofSeconds(10);
    private static final Duration SIM_DURATION = Duration.ofSeconds(120);

    private static final int USERS_PER_SECOND = 100;

    // 95th percentile response < max allowed milliseconds
    private static final int MAX_REQUEST_TIME_MILLIS = 800;
    private static final double MAX_FAILED_REQUEST_PERCENTAGE = 5.0;

    {
        try {
            System.out.println("================================================================================");
            System.out.println("System under test \n  " + SystemUnderTest.BASE_URL);
            System.out.println("================================================================================");

            setUp(
                    SystemUnderTest.walkAppScenario().injectOpen(
                            nothingFor(START_DELAY),
                            constantUsersPerSec(USERS_PER_SECOND).during(SIM_DURATION)))
                    .protocols(SystemUnderTest.httpProtocol())
                    .assertions(
                            global().failedRequests().percent().lt(MAX_FAILED_REQUEST_PERCENTAGE),
                            forAll().responseTime().percentile3().lt(MAX_REQUEST_TIME_MILLIS));
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

}

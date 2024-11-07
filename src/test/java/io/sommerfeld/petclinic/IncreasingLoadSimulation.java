package io.sommerfeld.petclinic;

import static io.gatling.javaapi.core.CoreDsl.*;

import io.gatling.javaapi.core.*;
import java.time.Duration;

/**
 * The IncreasingLoadSimulation class provides a Gatling simulation that puts an
 * increasing load on the system under test.
 *
 * The simulation injects an increasing number of users per second and measures
 * the response time and the percentage of failed requests.
 *
 * The simulation finishes after a fixed number of iterations or when the
 * thresholds for failed requests or response time are exceeded.
 */
public final class IncreasingLoadSimulation extends Simulation {

    private static final Duration START_DELAY = Duration.ofSeconds(10);
    private static final Duration ITERATION_DURATION = Duration.ofSeconds(12);
    private static final int ITERATIONS = 10;

    private static final int USERS_PER_SECOND = 20;

    // 95th percentile response < max allowed milliseconds
    private static final int MAX_REQUEST_TIME_MILLIS = 800;
    private static final double MAX_FAILED_REQUEST_PERCENTAGE = 5.0;

    {
        System.out.println("================================================================================");
        System.out.println("System under test \n  " + SystemUnderTest.BASE_URL);
        System.out.println("================================================================================");

        setUp(
                SystemUnderTest.walkAppScenario().injectOpen(
                        nothingFor(START_DELAY),
                        incrementUsersPerSec(USERS_PER_SECOND)
                                .times(ITERATIONS)
                                .eachLevelLasting(ITERATION_DURATION)
                                .startingFrom(USERS_PER_SECOND)))
                .protocols(SystemUnderTest.httpProtocol())
                .assertions(
                        global().failedRequests().percent().lt(MAX_FAILED_REQUEST_PERCENTAGE),
                        forAll().responseTime().percentile3().lt(MAX_REQUEST_TIME_MILLIS));
    }

}

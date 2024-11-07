// package io.sommerfeld.petclinic;

// import static io.gatling.javaapi.core.CoreDsl.*;
// import static io.gatling.javaapi.http.HttpDsl.*;

// import io.gatling.javaapi.core.*;
// import io.gatling.javaapi.http.*;

// import java.time.Duration;

// /**
// * Gatling load test for the Spring Petclinic application.
// * This test simulates a user journey through the application.
// */
// public class SpringPetclinicSimulation extends Simulation {

// private static final String BASE_URL = System.getenv("SYSTEM_UNDER_TEST");

// {
// System.out.println("================================================================================");
// System.out.println("System under test");
// System.out.println(BASE_URL);
// System.out.println("================================================================================");

// //
// setUp(increasingLoad().andThen(constantLoad().andThen(randomLoad()))).protocols(httpProtocol());
// setUp(increasingLoad().andThen(constantLoad())).protocols(httpProtocol());
// }

// private static HttpProtocolBuilder httpProtocol() {
// return http.baseUrl(BASE_URL)
// .acceptHeader("text/html,application/xhtml+xml,application/xml")
// .acceptEncodingHeader("gzip, deflate")
// .acceptLanguageHeader("en-US,en;q=0.5")
// .userAgentHeader(
// "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like
// Gecko) Chrome/58.0.3029.110 Safari/537.3");
// }

// /**
// * Put constant load on the system for a fixed duration by injecting a
// constant
// * number of users per second. After a user finishes its journey through the
// * application, it will disappear.
// *
// * @return
// */
// private static PopulationBuilder constantLoad() {
// Duration simDuration = Duration.ofMinutes(1);
// long timeoutSeconds = 10;

// int newUsers = 5;

// ScenarioBuilder scn = ClickFlow.getClickFlow("increasing load");

// return scn.injectOpen(
// nothingFor(timeoutSeconds),
// constantUsersPerSec(newUsers).during(simDuration));
// }

// private static PopulationBuilder increasingLoad() {
// Duration simDuration = Duration.ofMinutes(1);
// long timeoutSeconds = 10;

// int newUsers = 5;

// ScenarioBuilder scn = ClickFlow.getClickFlow("constant load");

// return scn.injectOpen(
// nothingFor(timeoutSeconds),
// rampUsers(newUsers).during(simDuration));

// }

// // private static PopulationBuilder randomLoad() {
// // Duration simDuration = Duration.ofSeconds(20);
// // long timeoutSeconds = 10;

// // ScenarioBuilder scn = ClickFlow.getClickFlow("random load");

// // return scn.injectOpen(
// // nothingFor(timeoutSeconds),
// // rampUsersPerSec(10).to(50).during(Duration.ofSeconds(5 *
// // simDuration.getSeconds())),
// // constantUsersPerSec(30).during(Duration.ofSeconds(2 *
// // simDuration.getSeconds())),
// // rampUsersPerSec(50).to(20).during(Duration.ofSeconds(3 *
// // simDuration.getSeconds())),
// // constantUsersPerSec(10).during(Duration.ofSeconds(2 *
// // simDuration.getSeconds())),
// // rampUsersPerSec(5).to(40).during(Duration.ofSeconds(4 *
// // simDuration.getSeconds())));
// // }
// }

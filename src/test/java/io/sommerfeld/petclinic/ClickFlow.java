// package io.sommerfeld.petclinic;

// import static io.gatling.javaapi.core.CoreDsl.scenario;
// import static io.gatling.javaapi.http.HttpDsl.http;

// import io.gatling.javaapi.core.ScenarioBuilder;

// import java.time.Duration;

// public class ClickFlow {

// public static ScenarioBuilder getClickFlow(final String nameSuffix) {
// return scenario("navigate app - " + nameSuffix)
// .exec(http("get homepage").get("/"))
// .pause(Duration.ofSeconds(5))
// .exec(http("get find owners page").get("/owners/find"))
// .pause(Duration.ofSeconds(5))
// .exec(http("get find owners page").get("/owners/find"))
// .pause(Duration.ofSeconds(8))
// .exec(http("get owners results page").get("/owners?lastName="))
// .pause(Duration.ofSeconds(12))
// .exec(http("get owner details page").get("/owners/7"))
// .exec(http("get veterinarians page").get("/vets"))
// .pause(Duration.ofSeconds(5))
// .exec(http("get veterinarians as json").get("/vets.json"));
// }
// }

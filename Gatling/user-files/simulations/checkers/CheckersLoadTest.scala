package checkers

import scala.concurrent.duration._
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class CheckersLoadTest extends Simulation {

  private val httpProtocol = http
    .baseUrl("http://127.0.0.1")
    .originHeader("http://127.0.0.1")
    .userAgentHeader("Gatling Performance Test")
  
  private val headers_0 = Map(
  		"Content-Type" -> "application/json",
  		"X-Requested-With" -> "XMLHttpRequest"
  )


  private val scn = scenario("Checkers Load Test")
    //Erstellen
    .exec(
      http("create")
        .post("/game/create")
        .headers(headers_0)
        .check(bodyBytes.is(RawFileBody("user-files/simulations/checkers/ressources/answer/create.json")))
    )
    .exec(
      http("move")
        .put("/game/move")
        .headers(headers_0)
        .body(RawFileBody("user-files/simulations/checkers/ressources/request/move-22-33.json"))
        .check(bodyBytes.is(RawFileBody("user-files/simulations/checkers/ressources/answer/game.json")))
    )
    //Speichern
    .exec(
      http("save")
        .post("/game/save")
        .headers(headers_0)
        .check(bodyBytes.is(RawFileBody("user-files/simulations/checkers/ressources/answer/game.json")))
    )
    //Laden
    .exec(
      http("load")
        .get("/game/load")
        .headers(headers_0)
        .check(bodyBytes.is(RawFileBody("user-files/simulations/checkers/ressources/answer/game.json")))
    )

	setUp(scn.inject(constantUsersPerSec(10).during(1.minute))).protocols(httpProtocol)
}

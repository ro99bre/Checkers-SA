import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class StorageManagerSpec extends Simulation {

  private val httpProtocol = http
    .baseUrl("http://localhost:8001")
    .originHeader("http://localhost:8001")
    .userAgentHeader("Gatling Integration Test")

  private val headers_0 = Map(
    "Content-Type" -> "application/json",
    "X-Requested-With" -> "XMLHttpRequest"
  )


  private val scn = scenario("Store and Recall Game")
    .exec(
      http("Save Json body")
        .post("/game")
        .headers(headers_0)
        .body(RawFileBody("src/it/scala/res/Game.json"))
    )
    .pause(2)
    .exec(
      http("Recall Json")
        .get("/game")
        .headers(headers_0)
        .check(bodyBytes.is(RawFileBody("src/it/scala/res/Game.json")))
    )

  setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}

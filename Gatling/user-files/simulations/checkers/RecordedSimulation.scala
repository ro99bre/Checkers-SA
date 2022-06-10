package checkers

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class RecordedSimulation extends Simulation {

  private val httpProtocol = http
    .baseUrl("http://192.52.45.214")
    .inferHtmlResources(AllowList(), DenyList(""".*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff""", """.*\.woff2""", """.*\.(t|o)tf""", """.*\.png""", """.*detectportal\.firefox\.com.*"""))
    .acceptHeader("application/json, text/javascript, */*; q=0.01")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("de,en-US;q=0.7,en;q=0.3")
    .originHeader("http://192.52.45.214")
    .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:99.0) Gecko/20100101 Firefox/99.0")
  
  private val headers_0 = Map("X-Requested-With" -> "XMLHttpRequest")
  
  private val headers_1 = Map(
  		"Content-Type" -> "application/x-www-form-urlencoded; charset=UTF-8",
  		"X-Requested-With" -> "XMLHttpRequest"
  )


  private val scn = scenario("RecordedSimulation")
    .exec(
      http("request_0")
        .post("/game/create")
        .headers(headers_0)
    )
    .pause(3)
    .exec(
      http("request_1")
        .put("/game/move")
        .headers(headers_1)
        .formParam("""{ "from": { "x": 2, "y": 2 }, "to": { "x": 3, "y": 3 } }""", "")
    )
    .pause(3)
    .exec(
      http("request_2")
        .put("/game/move")
        .headers(headers_1)
        .formParam("""{ "from": { "x": 5, "y": 5 }, "to": { "x": 4, "y": 4 } }""", "")
    )
    .pause(2)
    .exec(
      http("request_3")
        .put("/game/move")
        .headers(headers_1)
        .formParam("""{ "from": { "x": 3, "y": 3 }, "to": { "x": 5, "y": 5 } }""", "")
    )
    .pause(60)
    .exec(
      http("request_4")
        .post("/game/save")
        .headers(headers_0)
    )
    .pause(5)
    .exec(
      http("request_5")
        .delete("/game/undo")
        .headers(headers_0)
    )
    .pause(1)
    .exec(
      http("request_6")
        .delete("/game/undo")
        .headers(headers_0)
    )
    .pause(1)
    .exec(
      http("request_7")
        .put("/game/redo")
        .headers(headers_0)
        .resources(
          http("request_8")
            .put("/game/redo")
            .headers(headers_0)
        )
    )

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}

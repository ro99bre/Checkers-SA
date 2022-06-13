import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class CoreModuleSpec extends Simulation {

  private val httpProtocol = http
    .baseUrl("http://localhost:8080")
    .originHeader("http://localhost:8080")
    .userAgentHeader("Gatling Integration Test")

  private val headers_0 = Map(
    "Content-Type" -> "application/json",
    "X-Requested-With" -> "XMLHttpRequest"
  )


  private val scn = scenario("RecordedSimulation")
    .exec(
      http("Create Game")
        .post("/game/create")
        .headers(headers_0)
        .check(bodyBytes.is(RawFileBody("src/it/scala/res/answer/create.html")))
    )
    .exec(
      http("Move 2-2 to 3-3")
        .put("/game/move")
        .headers(headers_0)
        .body(RawFileBody("src/it/scala/res/request/move-22-33.json"))
        .check(bodyBytes.is(RawFileBody("src/it/scala/res/answer/move-22-33.html")))
    )
    .exec(
      http("Move 3-5 to 4-4")
        .put("/game/move")
        .headers(headers_0)
        .body(RawFileBody("src/it/scala/res/request/move-35-44.json"))
        .check(bodyBytes.is(RawFileBody("src/it/scala/res/answer/move-35-44.html")))
    )
    .exec(
      http("Undo")
        .delete("/game/undo")
        .headers(headers_0)
        .check(bodyBytes.is(RawFileBody("src/it/scala/res/answer/move-22-33.html")))
    )
    .exec(
      http("Redo")
        .put("/game/redo")
        .headers(headers_0)
        .check(bodyBytes.is(RawFileBody("src/it/scala/res/answer/move-35-44.html")))
    )
    .exec(
      http("Save current Game")
        .post("/game/save")
        .headers(headers_0)
        .check(bodyBytes.is(RawFileBody("src/it/scala/res/answer/save.html")))
    )
    .exec(
      http("Create Game")
        .post("/game/create")
        .headers(headers_0)
    )
    .exec(
      http("Reload Game")
        .get("/game/load")
        .headers(headers_0)
        .check(bodyBytes.is(RawFileBody("src/it/scala/res/answer/load.html")))
    )

  setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}

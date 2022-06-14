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


  private val scn = scenario("Create a game, move some pieces, save it and restore it")
    .exec(
      http("Create Game")
        .post("/game/create")
        .headers(headers_0)
        .check(bodyBytes.is(RawFileBody("src/it/scala/res/answer/create.json")))
    )
    .exec(
      http("Move 2-2 to 3-3")
        .put("/game/move")
        .headers(headers_0)
        .body(RawFileBody("src/it/scala/res/request/move-22-33.json"))
        .check(bodyBytes.is(RawFileBody("src/it/scala/res/answer/move-22-33.json")))
    )
    .exec(
      http("Move 3-5 to 4-4")
        .put("/game/move")
        .headers(headers_0)
        .body(RawFileBody("src/it/scala/res/request/move-35-44.json"))
        .check(bodyBytes.is(RawFileBody("src/it/scala/res/answer/move-35-44.json")))
    )
    .exec(
      http("Undo")
        .delete("/game/undo")
        .headers(headers_0)
        .check(bodyBytes.is(RawFileBody("src/it/scala/res/answer/move-22-33.json")))
    )
    .exec(
      http("Redo")
        .put("/game/redo")
        .headers(headers_0)
        .check(bodyBytes.is(RawFileBody("src/it/scala/res/answer/move-35-44.json")))
    )
    .exec(
      http("Save current Game")
        .post("/game/save")
        .headers(headers_0)
        .check(bodyBytes.is(RawFileBody("src/it/scala/res/answer/save.json")))
    )
    .pause(2)
    .exec(
      http("Create Game")
        .post("/game/create")
        .headers(headers_0)
    )
    .pause(2)
    .exec(
      http("Reload Game")
        .get("/game/load")
        .headers(headers_0)
        .check(bodyBytes.is(RawFileBody("src/it/scala/res/answer/load.json")))
    )

  setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}

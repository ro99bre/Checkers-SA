package storage.MongoStorage

import com.google.inject.Inject
import model.GameComponent.GameBaseImpl.Game
import model.GameComponent.GameTrait

import org.mongodb.scala.*
import org.mongodb.scala.model.*
import org.mongodb.scala.model.Aggregates.*
import org.mongodb.scala.model.Filters.*
import org.mongodb.scala.model.Projections.*
import org.mongodb.scala.model.Sorts.*
import org.mongodb.scala.model.Updates.*

import storage.StorageTrait
import util.JsonHandler.JsonHandler

import scala.collection.JavaConverters.*
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, blocking}

class Storage @Inject()extends StorageTrait {

  val uri: String = "mongodb://checkers:checkers123@checkers-mongodb/?retryWrites=true&w=majority"
  System.setProperty("org.mongodb.async.type", "netty")
  val client: MongoClient = MongoClient(uri)
  val db: MongoDatabase = client.getDatabase("checkers")
  val collection: MongoCollection[Document] = db.getCollection("checkers")

  val jsonHandler = new JsonHandler

  override def save(game: GameTrait): Unit = {
    println("saving")
    val doc: Document = Document("name" -> "game0", "board" -> jsonHandler.generate(game))

    collection.insertOne(doc).subscribe(new Observer[Any] {
      override def onSubscribe(subscription: Subscription): Unit = subscription.request(1)
      override def onNext(result: Any): Unit = if (result == 0) println("Not Found") else println("Found")
      override def onError(e: Throwable): Unit = println(e.toString)
      override def onComplete(): Unit = println("Completed")
    })
  }

  override def load(): GameTrait = {
    println("loading")
    val result = Await.result(collection.find(equal("name", "game0")).first().head(), Duration.Inf)
    val value = result.get("board").get

    jsonHandler.decode(value.asString().getValue)
  }
}

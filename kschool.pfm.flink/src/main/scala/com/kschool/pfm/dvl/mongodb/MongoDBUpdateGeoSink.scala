package com.kschool.pfm.dvl.mongodb

import grizzled.slf4j.Logging
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction
import reactivemongo.api.{MongoDriver, _}
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.bson.{BSONArray, BSONDocument}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

case class MongoUpdateKschool(key: String, latitude: Double, longitude: Double)

class MongoDBUpdateGeoSink[IN] extends RichSinkFunction[IN] with Logging {

  var connection: MongoConnection = _
  var collection: BSONCollection = _

  override def invoke(mongoDocumentUpdate: IN): Unit = {

    val mongoDocument = mongoDocumentUpdate.asInstanceOf[MongoUpdateKschool]

    val modifier = BSONDocument(
      "$setOnInsert" -> BSONDocument(
        "name" -> mongoDocument.key,
        "geoposition.type" -> "Feature",
        "geoposition.geometry.type" -> "MultiPoint",
        "geoposition.properties" -> getPropertiesForKey(mongoDocument.key)
      )
    ).add(
      BSONDocument(
        "$push" -> BSONDocument(
          "geoposition.geometry.coordinates" -> BSONDocument(
            "$each" -> BSONArray(BSONArray(mongoDocument.longitude, mongoDocument.latitude)),
            "$slice" -> -3
          )
        )
      )

    )

    val result = collection.update(BSONDocument("name" -> mongoDocument.key), modifier, upsert = true)


    result onComplete {
      case Success(success) => info("Update register in mongoDB: " + success)
      case Failure(failure) => info("Problem update register in mongoDB: " + failure)
    }

  }

  override def open(parameters: Configuration): Unit = {

    val driver = new MongoDriver

    connection = driver.connection(parameters.getString("mongo.url", "mongodb").split(","))

    val database = connection(parameters.getString("mongo.database", "kschool"))

    collection = database.collection(parameters.getString("mongo.collection", "test"))

  }

  override def close(): Unit = {

    connection.close()
  }

  private def getPropertiesForKey(key: String): BSONDocument = {

    key match {
      case "Mario" => BSONDocument("title" -> "Mario", "marker-color" -> "#bdcab6")
      case "Juan" => BSONDocument("title" -> "Juan", "marker-color" -> "#b445e7")
      case "Fernando" => BSONDocument("title" -> "Fernando", "marker-color" -> "#c0b32b")
      case "Beatriz" => BSONDocument("title" -> "Beatriz", "marker-color" -> "#61525c")
      case "Javier" => BSONDocument("title" -> "Javier", "marker-color" -> "#4d228e")
      case "Pablo" => BSONDocument("title" -> "Pablo", "marker-color" -> "#43943a")
      case "Edurne" => BSONDocument("title" -> "Edurne", "marker-color" -> "#012d29")
      case "Daniel" => BSONDocument("title" -> "Daniel", "marker-color" -> "#afa5a3")
      case "Patricia" => BSONDocument("title" -> "Daniel", "marker-color" -> "#aaaff1")
      case "Police" => BSONDocument("title" -> "Police", "marker-color" -> "#2be8f2")
      case "Ambulance" => BSONDocument("title" -> "Ambulance", "marker-color" -> "#e11407")
      case other => BSONDocument("title" -> "Other", "marker-color" -> "#faf")
    }
  }
}

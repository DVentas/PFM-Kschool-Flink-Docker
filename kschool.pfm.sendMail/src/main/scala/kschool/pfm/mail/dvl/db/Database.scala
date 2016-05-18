
package kschool.pfm.mail.dvl.db

import reactivemongo.api._
import reactivemongo.api.collections.bson.BSONCollection

import scala.concurrent.ExecutionContext.Implicits.global


object Database {

  private val connection = createConnection()

  private def createConnection(): MongoConnection = {
    val driver = new MongoDriver
    driver.connection( List( "mongodb" ) )
  }

  def connect(dbName :String, collection: String): BSONCollection = {
    val db = connection(dbName)
    db.collection(collection)
  }

}

package kschool.pfm.mail.dvl.db

import kschool.pfm.mail.dvl.model.Contact
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.bson.BSONDocument
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object KschoolDAO {

  private val kschoolConnection = createConnection()

  private def createConnection () : BSONCollection = {
    Database.connect("kschool", "test")
  }

  def findContact(id: String) : Future[Option[Contact]] = {
    val query = BSONDocument("name" -> id)
    val filter = BSONDocument("email" -> 1, "name" -> 1)

    kschoolConnection
      .find(query, filter)
      .one[Contact]

  }
}

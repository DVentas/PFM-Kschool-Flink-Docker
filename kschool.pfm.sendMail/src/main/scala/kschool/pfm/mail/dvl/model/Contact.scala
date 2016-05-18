package kschool.pfm.mail.dvl.model

import reactivemongo.bson.{BSONArray, Macros}

case class Contact (name: String, email: String)

object Contact {
  implicit val contactHandler = Macros.handler[Contact]
}

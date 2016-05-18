package kschool.pfm.mail.dvl.mail

import javax.mail.{Message, Transport, Session}
import javax.mail.internet.{InternetAddress, MimeMessage}

import kschool.pfm.mail.dvl.model._
import reactivemongo.bson.BSONArray


object EmailConf {
  val sender = "helper.kschool@gmail.com"
  val password = "aaaaaa"
  val host = "smtp.gmail.com"
  val port = "587"
  val protocol = "smtp"

  val properties = System.getProperties
  properties.setProperty("mail.smtp.host", host)
  properties.setProperty("mail.smtp.port", port)
  properties.setProperty("mail.smtp.starttls.enable", "true")
  properties.setProperty("mail.smtp.auth", "true")
  properties.setProperty("mail.smtp.user", "helper.kschool@gmail.com")
  properties.setProperty("mail.smtp.password", "ksch00l.")


  val session = Session.getInstance(properties)
}

object EmailSender {

  private val message : MimeMessage = createHeaderMessage()

  def send (contact: Contact, typeAlarm : String, latitude : String, longitude: String): Unit = {

    val transport = EmailConf.session.getTransport(EmailConf.protocol)
    transport.connect(EmailConf.sender, EmailConf.password)

    // Set To: header field of the header.
    message.addRecipient(Message.RecipientType.TO, new InternetAddress(contact.email))

    // Set Subject: header field
    message.setSubject(s"Hemos recibido una alerta de $typeAlarm sobre ${contact.name}")

    val img =
      s"""<img src=
        |"http://maps.googleapis.com/maps/api/staticmap?zoom=11&size=600x300&maptype=roadmap&markers=color:blue%7Clabel:S%7C
        |$latitude,$longitude"
        | width="600" height="300"
        |/>
      """.stripMargin

    val image = "<img src=\"http://maps.googleapis.com/maps/api/staticmap?size=800x600&maptype=hybrid&scale=2&format=png8&sensor=false&path=geodesic%3Atrue%7C-3.7033%2C+40.4167%7C-6.9325%2C+37.3933333333333%7C-6.93388888888889%2C+37.39333333333337C-6.93388888888889%2C+37.3916666666667%7C-6.9325%2C+37.3916666666667&zoom=10\" width=\"800\" height=\"600\"/>"

    // Now set the actual message
    message.setText(s"Por favor, contacte con ${contact.name} y compruebe que no corre ningun riesgo para su salud <br> Actualmente " +
      s"se encuentra en: <br><br>  $img", "utf-8", "html")

    // Send message
    transport.sendMessage(message, message.getAllRecipients)

    transport.close()
  }

  private def createHeaderMessage () : MimeMessage = {
    // Create a default MimeMessage object.
    val msg = new MimeMessage(EmailConf.session)

    // Set From: header field of the header.
    msg.setFrom(new InternetAddress(EmailConf.sender))

    msg

  }

}

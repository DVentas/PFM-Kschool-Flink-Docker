package kschool.pfm.mail.dvl.mail

import akka.actor.ActorLogging
import scala.util.Success
import scala.util.Failure
import akka.stream.actor.ActorSubscriberMessage._
import akka.stream.actor._
import kschool.pfm.mail.dvl.model._

import scala.concurrent._
import scala.util.Try
import scala.concurrent.ExecutionContext.Implicits.global

class SenderActor extends ActorSubscriber with ActorLogging {

  val requestStrategy = WatermarkRequestStrategy( 50 )

  override def receive = {
    case OnNext( (contact: Future[Option[Contact]], typeAlarm : String, latitude : String, longitude: String) ) =>
      log.debug("Next mail to send...")

      contact onComplete {
          case Success(Some(contact)) =>  EmailSender.send(contact, typeAlarm, latitude, longitude)
          case Success(None) => log.error("Error retrieving contact: Not found")
          case Failure(e) => log.error("Failure retrieving contact: " + e.printStackTrace)
      }

    case OnError( err: Exception ) =>
      log.error( err, "Error !!!! " + err.getMessage )
      context.stop( self )

    case OnComplete =>
      log.info( "bye bye!" )
      context.stop( self )

    case _ =>

  }

}


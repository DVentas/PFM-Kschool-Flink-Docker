package kschool.pfm.mail.dvl

import akka.actor.{Props, ActorSystem}
import akka.stream.ActorMaterializer
import akka.stream.actor.ActorSubscriber
import akka.stream.scaladsl.{Sink, Source}
import com.softwaremill.react.kafka.KafkaMessages._
import kschool.pfm.mail.dvl.mail._
import kschool.pfm.mail.dvl.model._
import org.apache.kafka.common.serialization.StringDeserializer
import com.softwaremill.react.kafka.{ConsumerProperties, ReactiveKafka}
import org.reactivestreams.{ Publisher, Subscriber }
import scala.concurrent.Future

object App {

  def main(args: Array[String]): Unit = {

    implicit val actorSystem = ActorSystem( "ReactiveKafka" )
    implicit val materializer = ActorMaterializer( )

    val kafka = new ReactiveKafka( )

    val publisher: Publisher[StringConsumerRecord] = kafka.consume( ConsumerProperties(
      bootstrapServers = "kafka:9092",//kafka
      topic = "alert_mail",
      groupId = "sendMailKschool",
      valueDeserializer = new StringDeserializer( )
    ) )

    val senderActor = actorSystem.actorOf( Props[SenderActor] )
    val actorSenderMailSubscrtiber: Subscriber[(Future[Option[Contact]], String, String, String)] =
            ActorSubscriber[(Future[Option[Contact]], String, String, String)](senderActor)

    Source.fromPublisher( publisher )
      .map( retrieveContactFromMongo )
      .to( Sink.fromSubscriber( actorSenderMailSubscrtiber ) ).run( )

  }

  def retrieveContactFromMongo(record: StringConsumerRecord): (Future[Option[Contact]], String, String, String) = {

    val splittedRecord = record.value.split(",")
    val user = splittedRecord(0)
    val typeAlarm = splittedRecord(1)
    val latitude = splittedRecord(2)
    val longitude = splittedRecord(3)

    (db.KschoolDAO.findContact( user ), typeAlarm, latitude, longitude)

  }

}

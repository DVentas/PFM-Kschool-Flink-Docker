package com.kschool.pfm.dvl

import com.github.marklister.collections.io._
import com.kschool.pfm.dvl.mongodb.{MongoDBUpdateGeoSink, MongoUpdateKschool}
import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.connectors.kafka.{FlinkKafkaConsumer09, FlinkKafkaProducer09}
import org.apache.flink.streaming.util.serialization.SimpleStringSchema

case class Event(Creation_Time: Double, x: Double, y: Double, z: Double, latitude: Double, longitude: Double, user: String)

object App {

  def main(args: Array[String]): Unit = {

    val WARNING_ACCELERATION: Double = 3D
    val MAX_ACCELERATION: Double = 5D

    // create execution environment
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    env.setStreamTimeCharacteristic(TimeCharacteristic.IngestionTime)

    // parameters
    val parameterTool = ParameterTool.fromArgs(//args)
      Array[String](

        "--topic", "kafka-raw",
        "--group.id", "kschool",
        "--bootstrap.servers", "kafka:9092" //"kafka:9092"
        , "--auto.offset.reset", "latest"

      )
    )

    val streamToProcess = env.addSource(new FlinkKafkaConsumer09[String](
      parameterTool.getRequired("topic"),
      new SimpleStringSchema(),
      parameterTool.getProperties
    )
    ).map(line => CsvParser(Event).parse(new java.io.StringReader(line)).head)

    //Save to mongo
    streamToProcess.map(event => MongoUpdateKschool(event.user, event.latitude, event.longitude))
      .addSink(new MongoDBUpdateGeoSink[MongoUpdateKschool])

    val eventsPersonsInDanger = streamToProcess
      .filter(checkWarning(_, WARNING_ACCELERATION))
      .map(event => formatKafkaEvent("danger", event.user, event.latitude, event.longitude))
      .addSink(new FlinkKafkaProducer09[String]("kafka:9092", "alert_mail", new SimpleStringSchema()))


    val eventsPersonWarn = streamToProcess
      .map(event => (event.user, event.x, event.y, event.z, event.latitude, event.longitude, 1))
      .filter(ev => !checkWarning(ev._2, ev._3, ev._4, MAX_ACCELERATION) && checkWarning(ev._2, ev._3, ev._4, WARNING_ACCELERATION))
      .keyBy(ev => ev._1)
      .timeWindow(Time.seconds(2), Time.seconds(1))
      .sum(6)
      .filter(_._5 >= 3)
      .map(event => formatKafkaEvent("warning", event._1, event._5, event._6))
      .addSink(new FlinkKafkaProducer09[String]("kafka:9092", "alert_mail", new SimpleStringSchema()))


    env.execute("Kschool pfm DVL")

  }

  def formatKafkaEvent(typeOfAlarm: String, user: String, latitude: Double, longitude: Double): String = {
    s"$user,$typeOfAlarm,$latitude,$longitude"

  }

  def checkWarning(event: Event, umbral : Double): Boolean = {
    checkWarning(event.x, event.y, event.z, umbral)
  }

  def checkWarning(x: Double, y: Double, z: Double, umbral: Double): Boolean = {

    val absX = Math.abs(x)
    val absY = Math.abs(y)
    val absZ = Math.abs(z)


    (absX > umbral)
      .||(absY > umbral)
      .||(absZ > umbral)
  }

}

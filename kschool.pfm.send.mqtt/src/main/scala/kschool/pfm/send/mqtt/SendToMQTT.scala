package kschool.pfm.send.mqtt

import org.eclipse.paho.client.mqttv3.{MqttClient, MqttMessage}

import scala.io.Source
import scala.util.control.NonFatal

object SendToMQTT extends App{

  override def main(args: Array[String]) {

    val client : MqttClient = new MqttClient("tcp://mqtt:1883", "kschool-clientID")

    var sleepMillis : Int = 500

    try {

      client.connect()

      args.sliding(2, 1).toList.collect {
        case Array("--sleep", sleep: String) => sleepMillis = sleep.toInt
      }

      val message : MqttMessage = new MqttMessage()

      val file = Source.fromFile("/misc/parser_phones.csv")

      for(line <- file.getLines()) {

        message.setPayload(line.getBytes())

        client.publish("mqtt_raw", message)

        Thread.sleep(sleepMillis)

      }

    } catch {
      case NonFatal(e) => println(e.printStackTrace())
    } finally {
      client.disconnect()
    }
  }
}

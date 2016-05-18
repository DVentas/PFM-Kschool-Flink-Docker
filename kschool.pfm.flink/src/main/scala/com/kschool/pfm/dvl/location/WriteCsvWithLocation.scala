package com.kschool.pfm.dvl.location

import java.io.{File, PrintWriter}
import org.apache.flink.api.common.operators.Order
import org.apache.flink.api.scala.{ExecutionEnvironment, _}

case class ev(Index: Integer, Arrival_Time: Long, Creation_Time: Long, x: Double, y: Double, z: Double, User: String, Model: String, Device: String, gt: String)


case class Person(name: String, var location: Location) {

  def generateLocation: Location = {
    location = LocationFactory.getNextLocationByPointWithArc(location, 10, Math.PI / 2)
    location
  }

}

object WriteCsvWithLocation {

  def main(args: Array[String]) {
    val env = ExecutionEnvironment.getExecutionEnvironment

    val mapPersons: Map[String, Person] = Map(
      // sol
      "a" -> Person("Patricia", Location(40.4167, -3.7033, LocationFactory.randomInitDirection)),
      // el pardo
      "b" -> Person("Mario", Location(40.4984, -3.7314, LocationFactory.randomInitDirection)),
      // alcobendas
      "c" -> Person("Juan", Location(40.5366, -3.6332, LocationFactory.randomInitDirection)),
      // torrejo
      "d" -> Person("Fernando", Location(40.4553500, -3.4697300, LocationFactory.randomInitDirection)),
      // rozas
      "e" -> Person("Beatriz", Location(40.4935329, -3.8757916, LocationFactory.randomInitDirection)),
      // vallecas
      "f" -> Person("Javier", Location(40.36695, -3.60146, LocationFactory.randomInitDirection)),
      //alcorcon
      "g" -> Person("Pablo", Location(40.34582, -3.82487, LocationFactory.randomInitDirection)),
      // getafe
      "h" -> Person("Edurne", Location(40.30571, -3.73295, LocationFactory.randomInitDirection)),
      // rivas
      "i" -> Person("Daniel", Location(40.326824, -3.517985, LocationFactory.randomInitDirection))

    )

    val consG: Double = 9.81

    val csv = env.readCsvFile[ev](
      "/Users/Daniel/Downloads/Activity recognition exp/Phones_accelerometer.csv",
      fieldDelimiter = ",",
      ignoreFirstLine = true
    )

    val pw = new PrintWriter(new File("/Users/Daniel/Downloads/Activity recognition exp/parser_phones.csv"))

    pw.write("Creation_Time,x,y,z,latitude,longitude,User\n")

    csv
      .sortPartition("Arrival_Time", Order.ASCENDING)
      .map[Tuple7[Double, Double, Double, Double, Double, Double, String]]((ev: ev) =>
      new Tuple7(
        ev.Arrival_Time.asInstanceOf[Double],
        ev.x / consG,
        ev.y / consG,
        ev.z / consG,
        mapPersons(ev.User).generateLocation.latitude,
        mapPersons(ev.User).location.longitude,
        mapPersons(ev.User).name.toString
      )
    )
      .first(4000000)
      .collect()

      .foreach(ev => pw.write(s"${ev._1},${ev._2},${ev._3},${ev._4},${ev._5},${ev._6},${ev._7}\n"))


    pw.close()
  }

}

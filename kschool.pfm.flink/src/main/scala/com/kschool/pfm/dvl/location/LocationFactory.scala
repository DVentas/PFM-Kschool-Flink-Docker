package com.kschool.pfm.dvl.location

import scala.util.Random

case class Location(latitude: Double, longitude: Double, direction: Double)

object LocationFactory {

  val rand = new Random()

  val maxLatitude: Double = 41.17911
  val minLatitude: Double = 39.96367
  val maxLongitude: Double = -3.05420
  val minLongitude: Double = -4.58679


  def getNextLocationByPoint(oldLocation: Location, maxDistanceInMetres: Integer): Location = {

    lazy val maxRadiusInDegrees: Double = maxDistanceInMetres / 111000D

    lazy val quantityOfMovement: Double = maxRadiusInDegrees * Math.sqrt(rand.nextDouble)

    lazy val displacement_Direction: Double = 2 * Math.PI * rand.nextDouble

    lazy val displacement_X: Double = quantityOfMovement * Math.cos(displacement_Direction)
    lazy val displacement_Y: Double = quantityOfMovement * Math.sin(displacement_Direction)

    val (newLatitude: Double, newLongitude: Double, newDirection: Double) =
      correctLatitudeLongitudeDirection(oldLocation, displacement_X, displacement_Y, displacement_Direction)

    Location(newLatitude, newLongitude, newDirection)
  }

  def getNextLocationByPointWithArc(oldLocation: Location, maxDistanceInMetres: Integer, maxArc: Double): Location = {

    val maxRadiusInDegrees: Double = maxDistanceInMetres / 111000D

    val quantityOfMovement: Double = maxRadiusInDegrees * Math.sqrt(rand.nextDouble)

    val displacement_Direction: Double = (maxArc * rand.nextDouble) + (oldLocation.direction - (maxArc / 2))

    val displacement_X: Double = quantityOfMovement * Math.cos(displacement_Direction)
    val displacement_Y: Double = quantityOfMovement * Math.sin(displacement_Direction)

    val (newLatitude: Double, newLongitude: Double, newDirection: Double) =
      correctLatitudeLongitudeDirection(oldLocation, displacement_X, displacement_Y, displacement_Direction)

    Location(newLatitude, newLongitude, newDirection)
  }

  //If the limits are exceeded turns around
  def correctLatitudeLongitudeDirection(oldLocation: Location, displacement_X: Double, displacement_Y: Double,
                                        displacement_Direction: Double): (Double, Double, Double) = {

    val latitudeWithDisplacement: Double = oldLocation.latitude + displacement_X
    val longitudeWithDisplacement: Double = oldLocation.longitude + displacement_Y

    if (latitudeWithDisplacement > maxLatitude || latitudeWithDisplacement < minLatitude ||
      longitudeWithDisplacement > maxLongitude || longitudeWithDisplacement < minLongitude) {

      (oldLocation.latitude, oldLocation.longitude, oldLocation.direction + Math.PI)

    } else {
      (latitudeWithDisplacement, longitudeWithDisplacement, displacement_Direction)
    }

  }

  def randomInitDirection: Double = {
    2 * Math.PI * rand.nextDouble
  }

}
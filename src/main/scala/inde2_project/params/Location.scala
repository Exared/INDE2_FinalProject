package params

import play.api.libs.json.{Json, OFormat}

final case class Location(latitude: Double, longitude: Double)

object Location {
    implicit val locationFormat : OFormat[Location] = Json.format[Location]
}
package params

import java.time.LocalDateTime
import play.api.libs.json.{Json, OFormat}

final case class Alert(
    drone_id : Int,
    location: Location,
    person_name: String
)

object Alert {
  implicit val AlertFormatter: OFormat[Alert] = Json.format[Alert]
}
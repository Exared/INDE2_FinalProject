package alertHandler

import java.time.LocalDateTime
import play.api.libs.json.{Json, OFormat}

final case class Alert(
    peacewatcher_id : Int,
    timestamp: LocalDateTime,
    location: Coords,
    words: List[String],
    persons: List[Person],
    battery: Int,
    temperature: Int,
)

object Alert {
  implicit val AlertFormatter: OFormat[Alert] = Json.format[Alert]
}
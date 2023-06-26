package data

import java.time.LocalDateTime
import play.api.libs.json.{Json, OFormat}

final case class Report(
                        peacewatcher_id : Int, 
                        location: Location,
                        persons: List[Person],
                        words: List[String],
                        timestamp: LocalDateTime
                    )
object Report {
    implicit val EventFormatter : OFormat[Report] = Json.format[Report]
}
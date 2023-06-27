package params

import java.time.LocalDateTime
import play.api.libs.json.{Json, OFormat}

final case class Report(
                        drone_id : Int, 
                        location: Location,
                        persons: List[Person],
                        words: List[String],
                        timestamp: LocalDateTime
                    )
object Report {
    implicit val ReportFormatter : OFormat[Report] = Json.format[Report]
}
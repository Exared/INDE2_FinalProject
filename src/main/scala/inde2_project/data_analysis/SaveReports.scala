package dataAnalysis

import params._

import java.time.LocalDateTime
import play.api.libs.json.{Json, OFormat}

final case class SaveReport(
                        drone_id : Int, 
                        location: Location,
                        persons: List[Person],
                        words: List[String],
                        timestamp: LocalDateTime
                    )
object SaveReport {
    implicit val ReportFormatter : OFormat[SaveReport] = Json.format[SaveReport]
}
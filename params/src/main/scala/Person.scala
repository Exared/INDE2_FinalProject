package params

import play.api.libs.json.{Json, OFormat}

final case class Person(name: String, harmonyScore: Double)

object Person {
    implicit val personFormat : OFormat[Person] = Json.format[Person]
}
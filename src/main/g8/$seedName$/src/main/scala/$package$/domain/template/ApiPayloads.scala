package $package$.domain.template

import io.circe.generic.auto._

final case class Tweet(text: String) {

  def transform: String = text.trim.toUpperCase

}

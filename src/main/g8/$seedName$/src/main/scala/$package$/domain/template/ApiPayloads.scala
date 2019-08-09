package $package$.domain.template

final case class Tweet(text: String) {

  def transform: String = text.trim.toUpperCase

}

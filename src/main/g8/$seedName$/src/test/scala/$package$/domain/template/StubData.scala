package $package$
package domain
package template

import scala.util.Random

trait StubData {

  def randomString(): String = Random.nextString(25)

  val userName = randomString()

  def tweets(limit: Int): Seq[Tweet] = {
    (0  until limit).foldLeft(Seq.empty[Tweet]){ case (seq, _) => seq :+ Tweet(randomString()) }
  }
}

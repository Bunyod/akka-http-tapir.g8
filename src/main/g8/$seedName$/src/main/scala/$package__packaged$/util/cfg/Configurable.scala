package $package$
package util
package cfg

import com.typesafe.scalalogging.LazyLogging
import pureconfig._

trait Configurable {
  val config: Config = Configurable.config
}

object Configurable extends AnyRef with LazyLogging {

  import pureconfig.generic.auto._
  val config: Config = loadConfigOrThrow[Config]

}

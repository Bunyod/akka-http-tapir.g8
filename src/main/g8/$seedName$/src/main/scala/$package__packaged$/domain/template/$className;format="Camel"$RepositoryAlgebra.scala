package $package$.domain.template

import scala.concurrent.Future

trait $className;format="Camel"$RepositoryAlgebra {
  def searchByUserName(username: String, limit: Int): Future[Seq[Tweet]]
}

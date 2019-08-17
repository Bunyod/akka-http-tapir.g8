Tamplate
=============================

## Goal
The goal for this project is to provide a multi-sbt project with Generated OpenApi documentation. This project has two main sub-modules:
 - `seedName` - akka-http server
 - `common` - common things could be defined for other sub-modules 


## Stack technologies

- [JDK 11](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
- [SBT](http://www.scala-sbt.org/release/docs/Getting-Started/Setup.html)
- [akka-http](https://doc.akka.io/docs/akka-http/current/) as the web server. I could have gone with finch, twitter server, or http4s here as well.
- [Circe](https://circe.github.io/circe/) for json serialization
- [Cats](https://typelevel.org/cats/) for FP awesomeness
- [ScalaTest](http://www.scalatest.org/) for test
- [Tapir](https://github.com/softwaremill/tapir) for automatically generating an api documentation

## Resources

- For setting up the initial `template` structure, we utilized
  [Scala Pet Store](https://github.com/pauljamescleary/scala-pet-store).

- For STAGE/ PROD configuration, we utilized [Production
  Configuration](https://www.playframework.com/documentation/2.6.x/ProductionConfiguration#overriding-configuration-with-system-properties)

## Architecture
### Domain Driven Design (DDD)
Domain driven design is all about developing a _ubiquitous language_, which is a language that you can use to discuss your software with business folks (who presumably do not know programming).

DDD is all about making your code expressive, making sure that how you _talk_ about your software materializes in your code.  One of the best ways to do this is to keep you _domain_ pure.  That is, allow the business concepts and entities to be real things, and keep all the other cruft out.  However, HTTP, JDBC, SQL are not essential to domain, so we want to _decouple_ those as much as possible.

### Onion (or Hexagonal) Architecture
In concert with DDD, the [Onion Architecture](https://jeffreypalermo.com/2008/08/the-onion-architecture-part-3/) and [Hexagonal Architecture from Cockburn](https://java-design-patterns.com/patterns/hexagonal/) give us patterns on how to separate our domain from the ugliness of implementation.

We fit DDD an Onion together via the following mechanisms:

**The domain package**
The domain package constitutes the things inside our domain.  It is deliberately free of the ugliness of JDBC, JSON, HTTP, and the rest.
We use `Services` as coarse-grained interfaces to our domain.  These typically represent real-world use cases. Often times, you see a 1-to-1 mapping of `Services` to `R` or HTTP API calls your application surfaces.

Inside of the **domain**, we see a few concepts:

1. `Service` - the coarse grained use cases that work with other domain concepts to realize your use-cases
1. `Repository` - ways to get data into and out of persistent storage.  **Important: Repositories do not have any business logic in them, they should not know about the context in which they are used, and should not leak details of their implementations into the world**.
1. `payloads` or `models` - things like `Tweet`, etc are all domain objects.  We keep these lean (i.e. free of behavior).

**The repository package**
The repository package is where the ugliness lives.  It has JDBC things, and the like.
it contains implementations of our `Repositories`.  We may have 2 different implementations, an in-memory version as well as a **doobie** version.

**The http package**
It contains the HTTP endpoints that we surface via **akka-http**.  You will also typically see JSON things in here via **circe**

**The util package**
The util package could be considered infrastructure, as it has nothing to do with the domain.

**NOTE**
All business logic is located in `domain` package, every package inside is
related to some domain.

Service classes contains high level logic that relate to data manipulation,
that means that services MUST NOT implement storage.

For storage there are dedicated classes.

## Command line

In order to run locally on a developer machine via the command line, go to
source folder and execute

```
~/your-project-name/> sbt runServer
```

And also
```
~/your-project-name/> sbt ";project seed-api; ~reStart"
```

## Exmaple
Try from commandline
```
curl -X GET "http://localhost:9000/api/v1/tweet/funnytweets?limit=10" -H "accept: application/json"
```

Try from swagger-ui  
Open `http://localhost:9000/docs/` in your browser

## Pre-Commit Hook

[Pre Commit](https://github.com/pre-commit/pre-commit) is a project on github to setup and maintain
git commit hooks. The default hooks are defined in `.pre-commit-config.yaml`

For installation on osx run
```
brew install pre-commit
```

To setup the hooks with pre-commit run:
```
pre-commit install -f --install-hooks
```

After that scalafmt checks your changed files for codestyle:

_Note:_ Conflicts should be resolved

## Deployment

This section describes how to deploy `template` to either `STAGE` or `PROD`.

## Dependency Udpates
```bash
sbt dependencyUpdates
```

Lists newer versions of integrated dependencies from Maven/Ivy

sbt dependencyUpdates

### Pre-Commit Hook
This is for manage and configure Git hooks. We setup scalastyle and scalafmt for this project.
pre-commit-hook is a Git hook manager that runs scalafmt on CHANGED .scala and .sbt files each time you commit them.
It doesn’t allow you to commit if in your code something is not satisfactory with your configuration file.
```
pre-commit install -f --install-hooks
```
#### pre-commit

We use [`pre-commit`](https://github.com/pre-commit/pre-commit) to setup and maintain shared commit hooks.
Its configured in `.pre-commit-config.yaml` with scalastyle and scalafmt
- .scalafmt.conf (see pre-commit-hook.yaml)
- scalastyle-config.xml - (see pre-commit-hook.yaml)

Install `pre-commit` either via `pip` OS independently:
```bash
pip install pre-commit
```

or via [homebrew](https://brew.sh/) if you are on Mac OS:
```bash
brew install pre-commit
```

#### Register the hooks
```bash
pre-commit install -f --install-hooks
```

NOTE: make sure that you have above tools locally installed. For that, follow the instruction on:
- [scalafmt installation page](https://scalameta.org/scalafmt/docs/installation.html).
- [scalastyle installation page](http://www.scalastyle.org/command-line.html).

You can also run `scalafmt`, `scalastyle` through `sbt scalafmt`/`sbt scalastyle` yet we use it for the pre-commit hook.

Conflicts MUST BE resolved. The pre-commit will only show the error, not automatically fix them.
To fix some issues automatically, run `scalafmt` and/or `scalastyle` yourself.

More about Git hook for [scalstyle and scalafrm](https://gist.github.com/Bunyod/9f4ba570b9ce7c13d94025c070a499b8)

## Known issues
Please change in SwaggerRoutes
```
getFromResourceDirectory("META-INF/resources/webjars/swagger-ui/swaggerVersion/")
```
to
```
getFromResourceDirectory(s"META-INF/resources/webjars/swagger-ui/$swaggerVersion/")
```

### Configuration via Pureconfig

Pureconfig's config file does not support uppercase and underscores. [Read here](https://github.com/pureconfig/pureconfig/issues/394)

## Integration test
We use sbt suggested structure for integration test.
- sbt integration test structure [explained here](https://www.scala-sbt.org/1.x/docs/Testing.html#Integration+Tests)

```
src
  >it
  >main
  >test
```

## How to use sbt-coverage

Run the tests with enabled coverage:
```
$ sbt clean test
```

To generate the coverage reports run
```
$ sbt coverageReport
```

Coverage reports will be in target/scala-2.12/scoverage-report/index.html.

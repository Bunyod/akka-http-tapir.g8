# Connectivity Scala Seed Project

The seed project.

## Goal
The goal for this project is to demonstrate how to start easily a new scala project (using FP techniques? :) ).

## Stack technologies

- [JDK 8](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
- [SBT](http://www.scala-sbt.org/release/docs/Getting-Started/Setup.html)
- [akka-http](https://doc.akka.io/docs/akka-http/current/) as the web server. I could have gone with finch, twitter server, or http4s here as well.
- [Circe](https://circe.github.io/circe/) for json serialization
- [Cats](https://typelevel.org/cats/) for FP awesomeness
- [Macwire](https://github.com/adamw/macwire) for dependency injection
- [Scapegoat](https://github.com/sksamuel/scapegoat) for linting
- [ScalaTest](http://www.scalatest.org/) for test
- [Swagger](https://github.com/swagger-akka-http/swagger-akka-http) for automatically generating an api documentation
- [Prometheus](https://prometheus.io/docs/introduction/overview/) for metrics

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

## Conventions

## Versioning

## Database

## Configuration
1.`application.conf`
1.`stage.application.conf`
1.`prod.application.conf`
We use **Pure Config** to load configuration objects when the application starts up.  **pure config** Provides a neat mapping of config file to case classes for us, so we really do not have to do any code.

## Run

To run project locally and load the corresponding secretes, it is recommended to
choose one of the following alternatives.

## Command line

In order to run locally on a developer machine via the command line, go to
source folder and execute

```
~/scala-seed-con1/> sbt runServer
```

For `<command>` use one of `~compile | test | run`

## IntelliJ
FIXME
1. Open settings (`File -> Settings or CTRL+ALT+S`)
1. Choose Plugins
1. Open "browse repositories" window and type `BashSupport`
1. Click "Install" button and wait until the installation completes
1. Restart Intellij IDEA
1. Open Run/Debug Configuration (`Run -> Edit Configurations`)
1. Click "+" button and select "Bash" item from drop down
1. Give a name and select `sbt` file from source code
1. Enter `run` into "Program arguments" field
1. Click "Apply" and "OK"
1. Click green button on the right side at the top of the window (or `Shift+F10`)

## Testing

Because *route unit tests* (not to be confused with end-to-end tests (aka.
integration tests)) must not depend on business service logic (e.g.
`domain.template.Service`), we mock those calls by
`svc.tweets _)when(*, *).returns(mock)`.

## Deployment

This section describes how to deploy `SEED` to either `STAGE` or `PROD`.

## Dependency Udpates

```
sbt dependencyUpdates
```

Lists newer versions of integrated dependencies from Maven/Ivy

sbt dependencyUpdates

## Linting

Linting is done via scapegoat. It is a static code analyzer inspecting the code with a set of 117 rules.
Certain files can be excluded, rules can be changed. Configuration is described [here](https://github.com/sksamuel/scapegoat).

The report can be picked up by Jenkins and should be part of the deployment pipeline.

## Publish

Not to be confused with Sbt's "publishing"!.

```
haha FIXME
```

## Release

```
haha FIXME
```

## Resources

- For setting up the initial `seed` structure, we utilized
  [Scala Pet Store](https://github.com/pauljamescleary/scala-pet-store).

- For STAGE/ PROD configuration, we utilized [Production
  Configuration](https://www.playframework.com/documentation/2.6.x/ProductionConfiguration#overriding-configuration-with-system-properties)

## Known issues

### Configuration via Pureconfig

Pureconfig's config file does not support uppercase and underscores. [Read here](https://github.com/pureconfig/pureconfig/issues/394)

### sbt-partial-unification
In order to use cats...

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

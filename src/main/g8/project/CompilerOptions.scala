object CompilerOptions {

  lazy val cOptions = Seq(
    "-encoding", "utf8",// Specify character encoding used by source files.
    "-Xfatal-warnings",  // New lines for each options
    "-deprecation", // Emit warning and location for usages of deprecated APIs.
    "-unchecked", // Enable additional warnings where generated code depends on assumptions.
    "-language:implicitConversions", // Allow definition of implicit functions called views
    "-language:higherKinds", // Allow higher-kinded types
    "-language:existentials", // Existential types (besides wildcard types) can be written and inferred
    "-Ypartial-unification", // Enable partial unification in type constructor inference

  )

}

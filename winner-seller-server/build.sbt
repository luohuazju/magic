import AssemblyKeys._
import sbt._
import Keys._
import com.googlecode.flyway.sbt.FlywayPlugin._

name := "winner-seller-server" 

organization := "com.sillycat" 

version := "1.0" 

scalaVersion := "2.10.3"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8") 

resolvers ++= Seq(
  "sonatype snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/",
  "flyway repo"        at "http://flywaydb.org/repo/",
  "maven repo"         at "http://repo1.maven.org/maven2/",
  "sonatype releases"  at "https://oss.sonatype.org/content/repositories/releases/",
  "typesafe repo"      at "http://repo.typesafe.com/typesafe/releases/",
  "spray repo"         at "http://repo.spray.io/"
)

libraryDependencies ++= Seq(
	"io.spray"            %   "spray-io"                  % "1.2-M8",
	"io.spray"            %   "spray-can"                 % "1.2-M8",
	"io.spray"            %   "spray-routing"             % "1.2-M8",
	"io.spray"            %   "spray-caching"             % "1.2-M8",
	"io.spray"            %   "spray-http"                % "1.2-M8",
	"io.spray" 	          %   "spray-testkit" 	          % "1.2-M8",
	"io.spray"            %   "spray-util"                % "1.2-M8",
    "com.typesafe.akka"   %%  "akka-actor"                % "2.2.0-RC1",
    "com.typesafe.akka"   %%  "akka-testkit"              % "2.2.0-RC1",
    "com.typesafe.akka"   %%  "akka-transactor"           % "2.2.0-RC1",
  	"com.typesafe"        %   "config"                    % "1.0.0",
  	"com.typesafe"        %   "slick_2.10.0-RC2"          % "0.11.2",
  	"com.h2database"      %   "h2" 			              % "1.3.170",
  	"io.spray"            %   "spray-json_2.10"           % "1.2.3",
  	"org.xerial" 	      %   "sqlite-jdbc" 	          % "3.6.20",
  	"joda-time"			  %   "joda-time"				  % "2.2",
  	"org.joda" 	 	  	  %   "joda-convert" 			  % "1.3.1",
  	"org.scalatest"       %   "scalatest_2.10"            % "1.9.1"   % "test",
    "org.specs2"          %%  "specs2"                    % "1.13"    % "test",
    "c3p0"                %   "c3p0"                      % "0.9.1.2",
    "com.typesafe"		  %%  "scalalogging-slf4j"	      % "1.0.1",
    "ch.qos.logback"      %   "logback-classic"           % "1.0.3"
)

seq(Revolver.settings: _*)

publishArtifact in Test := true

assemblySettings

mainClass in assembly := Some("com.sillycat.winnersellerserver.Boot")

artifact in (Compile, assembly) ~= { art =>
  art.copy(`classifier` = Some("assembly"))
}

excludedJars in assembly <<= (fullClasspath in assembly) map { cp =>
  cp filter {_.data.getName == "parboiled-scala_2.10.0-RC1-1.1.3.jar"}
}

addArtifact(artifact in (Compile, assembly), assembly)

scalariformSettings

//flyway start
seq(flywaySettings: _*)

flywayUrl := "jdbc:h2:~/demo_test;DB_CLOSE_DELAY=-1"

flywayUser := "sa"

flywayPassword := "password"

//flyway end


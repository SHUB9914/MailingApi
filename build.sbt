name := "MailingApiUsingAmazonSES"

version := "1.0"

scalaVersion := "2.12.2"

libraryDependencies ++= Seq(
  "com.amazonaws" % "aws-java-sdk" %  "1.11.152",
  "javax.mail" % "mail"  % "1.4" ,
  "com.typesafe" % "config" % "1.2.1" ,
  "org.slf4j" % "slf4j-api" % "1.7.25"
)

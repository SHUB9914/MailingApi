
import java.util.Properties
import javax.mail._
import javax.mail.internet._
import com.typesafe.config.ConfigFactory


trait MailingApi extends LoggerUtil {

  val conf = ConfigFactory.load
  val NO_SPACE = ""
  val senderEmail: String = try {
    conf.getString("fromEmail")
  } catch {
    case ex: Exception =>
      logger.error("Email key not set.\nPlease export the key.")
      NO_SPACE
  }

  val toEmail = try {
    conf.getString("toEmail")
  } catch {
    case ex: Exception =>
      logger.error("Email key not set.\nPlease add email variable using -> export email=your_email@domain.com")
      NO_SPACE
  }

  val password = try {
    conf.getString("fromPassword")
  } catch {
    case ex: Exception =>
      logger.error("Password key not set.\nPlease add password variable using -> export password=your_password")
      NO_SPACE
  }

  val smtpUsername = try {
    conf.getString("smtpUsername")
  } catch {
    case ex: Exception =>
      logger.error("smtpUsername key not set.\nPlease add smtpUsername variable")
      NO_SPACE
  }
  val smtpPassword = try {
    conf.getString("smtpPassword")
  } catch {
    case ex: Exception =>
      logger.error("smtpPassword key not set.\nPlease add smtpPassword variable")
      NO_SPACE
  }

  val hostName = "email-smtp.eu-west-1.amazonaws.com"
  val port = "25"
  val properties = new Properties
  properties.put("mail.smtp.port", port)
  properties.setProperty("mail.transport.protocol", "smtps")
  properties.setProperty("mail.smtp.starttls.enable", "true")
  properties.setProperty("mail.host", hostName)
  properties.setProperty("mail.user", senderEmail)
  properties.setProperty("mail.password", senderEmail)
  properties.setProperty("mail.password", password)
  properties.setProperty("mail.smtp.auth", "true")

  val session = Session.getDefaultInstance(properties)

  def sendMail(subject: String, content: String, nickname: String) = {
    try {
      val messageBody ="Message whatever you wants to send!!!!!!!!!"
      val message = new MimeMessage(session)
      message.setFrom(new InternetAddress(senderEmail))
      message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail))
      message.setSubject(subject)
      message.setHeader("Content-Type", "text/plain;")
      message.setContent(messageBody, "text/plain")
      val transport = session.getTransport("smtp")
      transport.connect(hostName, smtpUsername, smtpPassword)
      transport.sendMessage(message, message.getAllRecipients)
      logger.info("Email Sent!!")
    } catch {
      case exception: Exception =>
        logger.info("Mail delivery failed. " + exception)
    }
  }
}

object MailingApi extends MailingApi

object Test extends App {
  MailingApi.sendMail("XYZ" , "This is Smaple Mail" , "ShubhamAgrawal")
}


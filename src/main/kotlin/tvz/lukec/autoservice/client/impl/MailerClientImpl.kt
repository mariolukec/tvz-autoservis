package tvz.lukec.autoservice.client.impl

import org.slf4j.LoggerFactory
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.InputStreamSource
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Component
import tvz.lukec.autoservice.client.MailerClient
import tvz.lukec.autoservice.model.Car
import tvz.lukec.autoservice.model.Service
import tvz.lukec.autoservice.model.User
import javax.mail.MessagingException
import javax.mail.internet.MimeMessage

@Component
class MailerClientImpl(
        private val javaMailSender: JavaMailSender
) : MailerClient {

    private val LOG = LoggerFactory.getLogger(MailerClientImpl::class.java)

    override fun sendEmail(service: Service): Boolean {
        LOG.info("SENDING EMAIL FOR SERVICE WITH ID = " + service.id)
        val msg: MimeMessage = javaMailSender.createMimeMessage()

        val client: User = service.car!!.user!!
        val mechanics: List<User> = service.mechanics ?: listOf()
        val car: Car = service.car!!

        try {
            // true = multipart message
            val helper = MimeMessageHelper(msg, true)
            helper.setTo(client.email!!)
            helper.setSubject("Izvješće o servisu")

            // true = text/html
            helper.setText(generateHtmlTemplate(service, client, car, mechanics)!!, true)
            var i = 1
            for (image in service.images!!) {
                val imageSource: InputStreamSource = ByteArrayResource(image.data)
                helper.addAttachment("slika$i", imageSource, "image/png")
                i++
            }
        } catch (e: MessagingException) {
            e.printStackTrace()
            LOG.info("ERROR WHILE SENDING EMAIL")
            return false
        }

        javaMailSender.send(msg)
        LOG.info("EMAIL FOR DONE SERVICE SENT TO CLIENT WITH EMAIL = " + client.email)

        return true
    }

    private fun generateHtmlTemplate(service: Service, client: User, car: Car, mechanics: List<User>): String? {
        val dateFinished: String = service.dateFinished?.dayOfMonth.toString() + "." + service.dateFinished?.monthValue + "." + service.dateFinished?.year + "."
        var mechanicsInfoString = ""
        for (mechanic in mechanics) {
            mechanicsInfoString += mechanic.firstName
            mechanicsInfoString += " "
            mechanicsInfoString += mechanic.lastName
            mechanicsInfoString += ", "
            mechanicsInfoString += mechanic.email
            mechanicsInfoString += "\n<br />"
        }
        return """<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link href="https://fonts.googleapis.com/css?family=Poppins&display=swap" rel="stylesheet">
</head><body style="margin: 0 auto;font-family: Poppins; background-color: #01080e">

<div style="; z-index: 3; background-image: url(https://i.ibb.co/ncQdx0Z/autoserivs.jpg);  background-size: cover; width: 100%; height: 500px; margin:0 auto;background-position: 30% 30%; border-top-left-radius: 20px;border-top-right-radius: 20px;">
</div>
<div style="width: 80%; margin: 0 auto; color: #bebebe; z-index: 3;font-size: 20px">
    <h1 style="font-size: 40px; text-align: center; color:#bebebe ">Vaš ${car.make} ${car.model} je spreman!</h1>
</div>

<div style="width: 100%; display: inline-block; background-color: #05101c; padding-bottom: 15px;">
    <div style="width: 32%; display: inline-block; text-align: center; text-overflow: ellipsis;color: #bebebe">
        <p>Servis na</p>
        <div style="font-size: 35px">${service.mileage} km</div>
    </div>
    <div style="width: 32%; display: inline-block;text-align: center; text-overflow: ellipsis;">
        <p style="color: #bebebe">Cijena</p>
        <div><span style="color:#968107;font-weight: 600; font-size:35px">${java.lang.String.format("%.2f", service.price)} kn</span></div>
    </div>
    <div style="width: 32%; display: inline-block;text-align: center; text-overflow: ellipsis;color: #bebebe">
        <p>Datum završetka</p>
        <div style="font-size: 35px;text-overflow: ellipsis;">$dateFinished</div>
    </div>
</div>

<div style="width: 80%; margin: 0 auto; color: #bebebe; z-index: 3;font-size: 20px; padding-top: 10px;">
    <p style="color: #bebebe; font-weight: bold; text-align: center; padding-top: 50px">Pozdrav, ${client.firstName}! Javljamo ti da je servis na tvojem automobilu uspješno završen!</p>
    <p style="color: #bebebe">Registracijske tablice</p>
    <p style="color: #968107">${car.registrationPlate}</p>
    <p style="color: #bebebe">Naziv servisa</p>
    <p style="color: #968107">${service.name}</p>
    <p style="color: #bebebe">Opis</p>
    <p style="color: #888888">
        ${service.description}        </p>
    <p style="color: #bebebe">Zaduženi mehaničar/i</p>
    <p style="color: #888888">$mechanicsInfoString</p>

    <p style="font-weight: bold; text-align: center;color: #bebebe">Po auto možeš doći u radnom vremenu, pon-pet 07:00 - 19:00, sub: 07:00 - 13:00<p>

</div>


<footer style="padding: 10px; color: #bebebe; background-color: #05101c; text-align: center;border-bottom-left-radius: 20px;border-bottom-right-radius: 20px;">
    <p>Autoservis TVZ</p>
    <p>Kontakti:<br/>
        <a href="mailto:tvz.autoservisa@gmail.com">
            tvz.autoservis@gmail.com</a>
    <p>(+385) 01 2445 927</p>
    </p>
</footer>
</body>
</html>"""
    }

}
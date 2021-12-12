package tvz.lukec.autoservice.client

import tvz.lukec.autoservice.model.Service

interface MailerClient {
    fun sendEmail(service: Service): Boolean
}
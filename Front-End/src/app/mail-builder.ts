import { Mail } from "./mail"

export class MailBuilder {

    mail: any

    build_mail(from: any, to: any, subject: any, body: any, priority: any, attachment: any, date: any) {
        this.mail = new Mail()
        this.mail.from = from
        this.mail.to = to
        this.mail.subject = subject
        this.mail.body = body
        this.mail.priority = priority
        this.mail.attachment = attachment
        this.mail.date = date
        return this.mail;
    }
    build_mail_by_mail(email: Mail) {
        let mail = new Mail()
        mail.from = email.from
        mail.to = email.to
        mail.subject = email.subject
        mail.body = email.body
        mail.priority = email.priority
        mail.date = email.date
        mail.attachment = email.attachment
        return mail

    }
}
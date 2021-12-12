package tvz.lukec.autoservice.rest.domain.form

class UpdatePasswordForm {
    var id: Long? = null
    var newPassword: String? = null
    var newRepeatedPassword: String? = null
    var oldPassword: String? = null
}
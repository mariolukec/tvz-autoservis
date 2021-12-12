package tvz.lukec.autoservice.controller

import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.web.util.UrlUtils
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.util.UriComponentsBuilder
import tvz.lukec.autoservice.client.MailerClient
import tvz.lukec.autoservice.model.Image
import tvz.lukec.autoservice.model.Service
import tvz.lukec.autoservice.repository.ServiceRepository
import tvz.lukec.autoservice.rest.domain.dto.UserDto
import tvz.lukec.autoservice.rest.domain.form.EditUserForm
import tvz.lukec.autoservice.rest.domain.form.UpdatePasswordForm
import tvz.lukec.autoservice.rest.domain.form.UserForm
import tvz.lukec.autoservice.service.ImageService
import tvz.lukec.autoservice.service.UserService
import java.security.Principal
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping(value = ["/users"])
class UserController(
        private val userService: UserService,
        private val serviceRepository: ServiceRepository,
        private val imageService: ImageService,
        private val mailerClient: MailerClient
) {

    private val LOG = LoggerFactory.getLogger(UserController::class.java)


    @GetMapping("/clients")
    fun getAllClients(): ResponseEntity<List<UserDto>> {
        LOG.info("FETCHING ALL CLIENTS")
        return ResponseEntity(userService.getAllClients(), HttpStatus.OK)
    }

    @GetMapping("/clients/pageable")
    fun getAllClientsPageable(pageable: Pageable): ResponseEntity<Page<UserDto>> {
        LOG.info("FETCHING ALL CLIENTS")
        return ResponseEntity(userService.getAllClientsPageable(pageable), HttpStatus.OK)
    }


    @GetMapping("/mechanics")
    fun getAllMechanics(): ResponseEntity<List<UserDto>> {
        LOG.info("FETCHING ALL MECHANICS")
        return ResponseEntity(userService.getAllMechanics(), HttpStatus.OK)
    }

    @GetMapping("/me")
    fun getMe(principal: Principal, request: HttpServletRequest
    ): ResponseEntity<UserDto> {
        LOG.info("RemoteAddr " + request.remoteAddr)
        LOG.info("RemoteHost " + request.remoteHost)
        LOG.info("Local addr " + request.localAddr)
        LOG.info("Local NAME " + request.localName)
        LOG.info("Host HEADER " + request.getHeader("host"))
        LOG.info("FETCHING MY PROFILE for user with email = " + principal.name)
        val userDto: UserDto = userService.getDtoByEmail(principal.name)
        val baseUrl = UriComponentsBuilder.fromHttpUrl(UrlUtils.buildFullRequestUrl(request))
                .replacePath(request.contextPath)
                .replaceQuery(null)
                .fragment(null)
                .build()
                .toUriString()
        LOG.info("BASE URI$baseUrl")
        return ResponseEntity<UserDto>(userDto, HttpStatus.OK)
    }

    @PostMapping("/image")
    fun saveImage(@RequestPart imageData: MultipartFile, principal: Principal): ResponseEntity<Image> {
        val userEmail = principal.name
        LOG.info("SAVING USER IMAGE for user with email = $userEmail")
        val image: Image = imageService.saveUserImage(userEmail, imageData)
                ?: return ResponseEntity<Image>(HttpStatus.BAD_REQUEST)
        return ResponseEntity<Image>(image, HttpStatus.ACCEPTED)
    }

    @PostMapping("/new")
    fun createClient(@RequestBody userForm: UserForm): ResponseEntity<UserDto> {
        LOG.info("SAVING USER for user with email = " + userForm.email)
        val savedUser: UserDto = userService.save(userForm) ?: return ResponseEntity<UserDto>(HttpStatus.BAD_REQUEST)
        return ResponseEntity<UserDto>(savedUser, HttpStatus.OK)
    }

    @PutMapping("/update")
    fun updateUser(@RequestBody editUserForm: EditUserForm): ResponseEntity<UserDto> {
        LOG.info("EDITING USER for user with ID = " + editUserForm.id)
        val updatedUser: UserDto = userService.update(editUserForm)
                ?: return ResponseEntity<UserDto>(HttpStatus.BAD_REQUEST)
        return ResponseEntity<UserDto>(updatedUser, HttpStatus.OK)
    }

    @PutMapping("/update/password")
    fun updatePassword(@RequestBody updatePasswordForm: UpdatePasswordForm): ResponseEntity<Void> {
        LOG.info("UPDATING PASSWORD for user with ID = " + updatePasswordForm.id)
        userService.updatePassword(updatePasswordForm)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }

    //TODO: delete this
    @GetMapping("/mail/test")
    fun sendTestMail(): ResponseEntity<Void> {
        LOG.info("sending test mail")
        val service: Service = serviceRepository.getOne(1L)
        val sent: Boolean = mailerClient.sendEmail(service)
        return if (sent) {
            ResponseEntity(HttpStatus.OK)
        } else ResponseEntity(HttpStatus.BAD_REQUEST)
    }

}
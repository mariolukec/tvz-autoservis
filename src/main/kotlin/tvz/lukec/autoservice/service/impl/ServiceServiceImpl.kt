package tvz.lukec.autoservice.service.impl

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Predicate
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tvz.lukec.autoservice.client.MailerClient
import tvz.lukec.autoservice.mapper.dto.ServiceMapper
import tvz.lukec.autoservice.mapper.form.ServiceFormMapper
import tvz.lukec.autoservice.model.QService
import tvz.lukec.autoservice.model.Service
import tvz.lukec.autoservice.model.ServiceStatus
import tvz.lukec.autoservice.model.User
import tvz.lukec.autoservice.repository.ServiceRepository
import tvz.lukec.autoservice.repository.UserRepository
import tvz.lukec.autoservice.rest.domain.dto.ServiceDto
import tvz.lukec.autoservice.rest.domain.filter.ServiceFilter
import tvz.lukec.autoservice.rest.domain.form.EditServiceForm
import tvz.lukec.autoservice.rest.domain.form.ServiceForm
import tvz.lukec.autoservice.service.ServiceService
import java.time.LocalDateTime
import java.util.stream.Collectors

@org.springframework.stereotype.Service
class ServiceServiceImpl(
        private val serviceRepository: ServiceRepository,
        private val userRepository: UserRepository,
        private val serviceMapper: ServiceMapper,
        private val serviceFormMapper: ServiceFormMapper,
        private val mailerClient: MailerClient
) : ServiceService {
    override fun save(serviceForm: ServiceForm): ServiceDto {
        val service = serviceFormMapper.toEntity(serviceForm)
        return serviceMapper.toDto(serviceRepository.save(service))
    }

    override fun getCarServices(carId: Long): List<ServiceDto> {
        val services: List<Service> = serviceRepository.getAllByCarId(carId)
        return serviceMapper.toDtos(services)
    }

    override fun delete(id: Long): Boolean {
        serviceRepository.deleteById(id)
        return !serviceRepository.existsById(id)
    }

    override fun update(editServiceForm: EditServiceForm): ServiceDto {
        val service = serviceRepository.getById(editServiceForm.id!!)
        var sendEmail = false

        service.name = editServiceForm.name
        service.price = editServiceForm.price
        service.description = editServiceForm.description
        service.mileage = editServiceForm.mileage
        if (service.status !== editServiceForm.status && editServiceForm.status == ServiceStatus.DONE) {
            service.dateFinished = LocalDateTime.now()
            sendEmail = true
        } else if (editServiceForm.status != ServiceStatus.DONE) {
            service.dateFinished = null
        }
        service.status = editServiceForm.status

        val updatedService = serviceRepository.save(service)

        if(sendEmail){
            mailerClient.sendEmail(updatedService);
        }

        return serviceMapper.toDto(updatedService)
    }

    override fun updateServiceStatus(id: Long, serviceStatus: ServiceStatus): ServiceDto {
        val service = serviceRepository.getById(id)

        if (serviceStatus != ServiceStatus.DONE && service.status == ServiceStatus.DONE) {
            service.dateFinished = null
        }
        service.status = serviceStatus

        val updated = serviceRepository.save(service)

        return serviceMapper.toDto(updated)
    }

    override fun setPickedUpAt(id: Long, time: LocalDateTime): ServiceDto {
        val service = serviceRepository.getById(id)
        service.pickedUpAt = time
        return serviceMapper.toDto(serviceRepository.save(service))
    }

    override fun saveMechanics(id: Long, mechanicIdsList: List<Long>): ServiceDto {
        val service = serviceRepository.getById(id)

        val mechanics = mechanicIdsList.map {
            userRepository.getById(it)
        }

        service.mechanics = mechanics

        return serviceMapper.toDto(serviceRepository.save(service))
    }

    override fun removeMechanics(id: Long, mechanicIdsList: List<Long>): ServiceDto {
        TODO("Not yet implemented")

        //same as saveMechanics
    }

    override fun getUserServices(userId: Long): List<ServiceDto> {
        val services = serviceRepository.getAllByCarUserId(userId)
        return serviceMapper.toDtos(services)
    }

    override fun getMyServices(email: String): List<ServiceDto> {
        val services = serviceRepository.getAllByCarUserEmailOrderByDateFinishedDesc(email)
        return serviceMapper.toDtos(services)
    }

    override fun getAllServices(serviceFilter: ServiceFilter, pageable: Pageable): Page<ServiceDto> {
        val services = serviceRepository.findAll(buildPredicate(serviceFilter), pageable)
        return services.map { service: Service? -> serviceMapper.toDto(service!!) }
    }

    override fun getAllActiveServices(): MutableList<ServiceDto> {
        return serviceRepository.findAllByPickedUpAtIsNull()
                ?.stream()?.map { service -> serviceMapper.toDto(service!!) }
                ?.collect(Collectors.toList()) ?: mutableListOf()
    }

    override fun getById(id: Long): ServiceDto {
        val service = serviceRepository.getById(id)
        return serviceMapper.toDto(service)
    }

    private fun buildPredicate(serviceFilter: ServiceFilter): Predicate {
        val builder = BooleanBuilder()
        val qService: QService = QService.service
        if (serviceFilter.mechanicId != null) {
            val mechanic: User = userRepository.getById(serviceFilter.mechanicId!!)
            builder.and(qService.mechanics.contains(mechanic))
        }
        if (serviceFilter.serviceStatus != null) {
            builder.and(qService.status.eq(serviceFilter.serviceStatus))
        }
        if (serviceFilter.from != null) {
            builder.and(qService.dateReceived.after(serviceFilter.from))
        }
        if (serviceFilter.to != null) {
            builder.and(qService.dateFinished.before(serviceFilter.to))
        }
        return builder
    }

}
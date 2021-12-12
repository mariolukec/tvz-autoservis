package tvz.lukec.autoservice.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tvz.lukec.autoservice.model.ServiceStatus
import tvz.lukec.autoservice.rest.domain.dto.ServiceDto
import tvz.lukec.autoservice.rest.domain.filter.ServiceFilter
import tvz.lukec.autoservice.rest.domain.form.EditServiceForm
import tvz.lukec.autoservice.rest.domain.form.ServiceForm
import java.time.LocalDateTime

interface ServiceService {
    fun save(serviceForm: ServiceForm): ServiceDto
    fun getCarServices(carId: Long): List<ServiceDto>
    fun delete(id: Long): Boolean
    fun update(editServiceForm: EditServiceForm): ServiceDto
    fun updateServiceStatus(id: Long, serviceStatus: ServiceStatus): ServiceDto
    fun setPickedUpAt(id: Long, time: LocalDateTime): ServiceDto
    fun saveMechanics(id: Long, mechanicIdsList: List<Long>): ServiceDto
    fun removeMechanics(id: Long, mechanicIdsList: List<Long>): ServiceDto
    fun getUserServices(userId: Long): List<ServiceDto>
    fun getMyServices(email: String): List<ServiceDto>
    fun getAllServices(serviceFilter: ServiceFilter, pageable: Pageable): Page<ServiceDto>
    fun getAllActiveServices(): MutableList<ServiceDto>
    fun getById(id: Long): ServiceDto
}
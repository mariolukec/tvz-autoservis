package tvz.lukec.autoservice.service.impl

import org.springframework.web.multipart.MultipartFile
import tvz.lukec.autoservice.model.Car
import tvz.lukec.autoservice.model.Image
import tvz.lukec.autoservice.model.Service
import tvz.lukec.autoservice.model.User
import tvz.lukec.autoservice.repository.CarRepository
import tvz.lukec.autoservice.repository.ImageRepository
import tvz.lukec.autoservice.repository.ServiceRepository
import tvz.lukec.autoservice.repository.UserRepository
import tvz.lukec.autoservice.service.ImageService
import java.io.IOException
import java.util.function.Consumer

@org.springframework.stereotype.Service
class ImageServiceImpl(
        private val userRepository: UserRepository,
        private val imageRepository: ImageRepository,
        private val carRepository: CarRepository,
        private val serviceRepository: ServiceRepository
) : ImageService {
    override fun saveUserImage(userEmail: String, multipartFile: MultipartFile): Image? {
        if (userRepository.existsByEmail(userEmail)) {
            try {
                val image = Image()
                image.data = multipartFile.bytes
                val savedImage: Image = imageRepository.save(image)
                val user: User = userRepository.findByEmail(userEmail)!!
                user.image = savedImage
                userRepository.save(user)
                return savedImage
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        return null
    }

    override fun saveCarImage(carId: Long, multipartFile: MultipartFile): Image? {
        if (carRepository.existsById(carId)) {
            try {
                val image = Image()
                image.data = multipartFile.bytes
                val savedImage = imageRepository.save(image)
                val car: Car = carRepository.getById(carId)
                car.image = savedImage
                carRepository.save(car)
                return savedImage
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        return null
    }

    override fun saveServiceImages(serviceId: Long, multipartFiles: List<MultipartFile>): List<Image> {
        val images: MutableList<Image> = ArrayList()

        if (serviceRepository.existsById(serviceId)) {
            multipartFiles.forEach(Consumer { multipartFile: MultipartFile ->
                try {
                    val image = Image()
                    image.data = multipartFile.bytes
                    val savedImage = imageRepository.save(image)
                    images.add(savedImage)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            })
            val service: Service = serviceRepository.getById(serviceId)
            service.images = images
            serviceRepository.save(service)
        }

        return images
    }

}
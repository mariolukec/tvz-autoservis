package tvz.lukec.autoservice.service

import org.springframework.web.multipart.MultipartFile
import tvz.lukec.autoservice.model.Image

interface ImageService {
    fun saveUserImage(userEmail: String, multipartFile: MultipartFile): Image?
    fun saveCarImage(carId: Long, multipartFile: MultipartFile): Image?
    fun saveServiceImages(serviceId: Long, multipartFiles: List<MultipartFile>): List<Image>
}
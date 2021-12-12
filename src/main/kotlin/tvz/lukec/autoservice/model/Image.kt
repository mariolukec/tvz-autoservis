package tvz.lukec.autoservice.model

import javax.persistence.*


@Entity
@Table(name = "IMAGE")
class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    var id: Long? = null

    @Column(name = "DATA")
    lateinit var data: ByteArray
}

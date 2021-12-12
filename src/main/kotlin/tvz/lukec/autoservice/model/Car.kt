package tvz.lukec.autoservice.model

import javax.persistence.*

@Entity
@Table(name = "CAR")
class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    var id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    var user: User? = null

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IMAGE_ID", referencedColumnName = "ID")
    var image: Image? = null

    @Column(name = "MAKE")
    var make: String? = null

    @Column(name = "MODEL")
    var model: String? = null

    @Column(name = "YEAR")
    var year: Long? = null

    @Column(name = "REGISTRATION_PLATE")
    var registrationPlate: String? = null
}

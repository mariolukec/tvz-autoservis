package tvz.lukec.autoservice.model

import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*


@Entity
@Table(name = "SERVICE")
class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    var id: Long? = null

    @Column(name = "SERVICE_STATUS")
    @Enumerated(EnumType.STRING)
    var status: ServiceStatus? = null

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CAR_ID", referencedColumnName = "ID")
    var car: Car? = null

    @ManyToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    @JoinTable(name = "SERVICE_MECHANIC", joinColumns = [JoinColumn(name = "SERVICE_ID", referencedColumnName = "ID")], inverseJoinColumns = [JoinColumn(name = "MECHANIC_ID", referencedColumnName = "ID")])
    var mechanics: List<User>? = null

    @OneToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    @JoinTable(name = "SERVICE_IMAGE", joinColumns = [JoinColumn(name = "SERVICE_ID", referencedColumnName = "ID")], inverseJoinColumns = [JoinColumn(name = "IMAGE_ID", referencedColumnName = "ID")])
    var images: List<Image>? = null

    @Column(name = "NAME")
    var name: String? = null

    @Column(name = "PRICE")
    var price: BigDecimal? = null

    @Column(name = "DESCRIPTION")
    var description: String? = null

    @Column(name = "MILEAGE")
    var mileage: Int? = null

    @Column(name = "DATE_RECEIVED")
    var dateReceived: LocalDateTime? = null

    @Column(name = "DATE_FINISHED")
    var dateFinished: LocalDateTime? = null

    @Column(name = "PICKED_UP_AT")
    var pickedUpAt: LocalDateTime? = null

}
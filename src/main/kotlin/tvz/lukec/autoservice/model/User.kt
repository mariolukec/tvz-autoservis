package tvz.lukec.autoservice.model

import javax.persistence.*


@Entity
@Table(name = "USERS")
class User {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(name = "FIRST_NAME")
    var firstName: String? = null

    @Column(name = "LAST_NAME")
    var lastName: String? = null

    @Column(name = "EMAIL")
    var email: String? = null

    @Column(name = "PASSWORD")
    var password: String? = null

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")
    var role: Role? = null

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IMAGE_ID", referencedColumnName = "ID")
    var image: Image? = null

    fun isMechanic(): Boolean {
        return role?.name?.uppercase() == "ROLE_MECHANIC"
    }
}

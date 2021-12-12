package tvz.lukec.autoservice.model

import javax.persistence.*

@Entity
@Table(name = "MAKE")
class CarMake {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    var id: Long? = null

    @Column(name = "CODE")
    var code: String? = null

    @Column(name = "TITLE")
    var title: String? = null
}
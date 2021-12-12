package tvz.lukec.autoservice.model

import javax.persistence.*


@Entity
@Table(name = "MODEL")
class CarModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    var id: Long? = null

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MAKE_ID")
    var make: CarMake? = null

    @Column(name = "CODE")
    var code: String? = null

    @Column(name = "TITLE")
    var title: String? = null
}

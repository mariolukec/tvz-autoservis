package tvz.lukec.autoservice.model

import org.springframework.security.core.GrantedAuthority
import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "ROLE")
class Role : GrantedAuthority, Serializable {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @Column(name = "NAME")
    var name: String? = null

    override fun getAuthority(): String? {
        return name
    }

    override fun toString(): String {
        return super.toString()
    }
}
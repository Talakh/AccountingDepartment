package com.university.entities

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.*

@Entity
@Table(name = "users")
class User: UserDetails {
        @Id
        @Column(name = "id", nullable = false)
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Int? = null

        @Column(name = "first_name", nullable = false)
        var firstName: String = ""

        @Column(name = "patronymic", nullable = false)
        var patronymic: String = ""

        @Column(name = "surname", nullable = false)
        var surname: String = ""

        @Column(name = "password", nullable = false)
        private var password: String = ""

        @Column(name = "email", nullable = false)
        var email: String = ""

        @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
        var travelAllowances: List<TravelAllowance> = mutableListOf()

        @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
        var cashOrders: List<CashOrder> = mutableListOf()

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "department_Id", nullable = false)
        var department: Department = Department()

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "position_Id", nullable = false)
        var position: Position = Position()

        @Column(name = "image")
        var image: ByteArray = byteArrayOf()

        @Column(name = "enabled")
        private var enabled: Boolean = false

        @Column(name = "user_role")
        var role: String = ""

    fun setPassword(password: String) {
        this.password = password
    }

    fun setEnabled(enabled: Boolean) {
        this.enabled = enabled
    }

    override fun getAuthorities(): Collection<GrantedAuthority?> {
        return listOf(SimpleGrantedAuthority(role))
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return email
    }

    override fun isAccountNonExpired(): Boolean {
        return enabled
    }

    override fun isAccountNonLocked(): Boolean {
        return enabled
    }

    override fun isCredentialsNonExpired(): Boolean {
        return enabled
    }

    override fun isEnabled(): Boolean {
        return enabled
    }
}

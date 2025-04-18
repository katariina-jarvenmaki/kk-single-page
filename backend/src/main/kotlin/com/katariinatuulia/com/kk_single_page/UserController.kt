package com.katariinatuulia.com.kk_single_page

//******************** IMPORTS ********************//

import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

//******************** ENTITIES ********************//

@Entity
@Table(name = "users")
data class UserEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(unique = true, nullable = false)
    val username: String = "",

    @Column(nullable = false)
    val password: String = ""
)

@Repository
interface UserRepository : JpaRepository<UserEntity, Long> {
    fun findByUsername(username: String): UserEntity?
}

//******************** USERS ********************//

@Configuration
class DataSeeder {

    @Bean
    fun initUsers(userRepo: UserRepository) = CommandLineRunner {
        val defaultUsers = listOf(
            "katariina" to "devpass",
            "user" to "pass",
            "admin" to "admin123"
        )

        for ((username, password) in defaultUsers) {
            if (userRepo.findByUsername(username) == null) {
                userRepo.save(UserEntity(username = username, password = password))
            }
        }
    }
}











package com.itis.timetable.security

import com.itis.timetable.data.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
/*
@Service
open class JwtUserDetailsService : UserDetailsService {
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        return if ("senex" == username) {
            User(
                "senex",
                "$2a$10\$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6",
                emptyList()
            )
        } else {
            throw UsernameNotFoundException("User not found with username: $username")
        }
    }
}
*/

@Service
open class JwtUserDetailsService : UserDetailsService {
    @Autowired
    lateinit var repository: UserRepository

    override fun loadUserByUsername(username: String) =
        repository.findByUsername(username)?.run {
            User(
                username,
                password,
                emptyList(),
            )
        } ?: throw UsernameNotFoundException("User not found with username: $username")
}
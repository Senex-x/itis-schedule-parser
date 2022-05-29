package com.itis.timetable.data.entity.security;

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class UserCredentials(
    private val username: String,
    private val password: String,
    val tokens: List<String>,
): UserDetails {
    override fun getUsername() = username

    override fun getPassword() = password

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        TODO("Not yet implemented")
    }

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = true
}

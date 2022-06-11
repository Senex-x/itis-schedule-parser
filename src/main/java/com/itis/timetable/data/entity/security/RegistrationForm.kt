package com.itis.timetable.data.entity.security

import org.springframework.security.crypto.password.PasswordEncoder

data class RegistrationForm(
    var username: String,
    var password: String,
) {
    fun toUserInfo(passwordEncoder: PasswordEncoder) = UserInfo(
        null, username, passwordEncoder.encode(password)
    )
}

package com.itis.timetable.controllers

import com.itis.timetable.data.entity.security.RegistrationForm
import com.itis.timetable.data.repositories.UserRepository
import com.itis.timetable.security.JwtTokenUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

@Controller
class RegisterController {

    @Autowired
    lateinit var repository: UserRepository

    @Autowired
    lateinit var jwtTokenUtil: JwtTokenUtil

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    @GetMapping("/register")
    fun get(): String {
        return "register"
    }

    @PostMapping("/register", consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun createAuthenticationToken(
        registrationForm: RegistrationForm,
        response: HttpServletResponse,
    ): ResponseEntity<Any> {

        repository.save(registrationForm.toUserInfo(passwordEncoder))

        val token = jwtTokenUtil.generateToken(registrationForm.username)
        val cookie = Cookie("token", token)
        response.addCookie(cookie)
        response.sendRedirect("/")
        return ResponseEntity.ok().build()
    }
}
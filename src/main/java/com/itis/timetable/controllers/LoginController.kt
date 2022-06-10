package com.itis.timetable.controllers

import com.itis.timetable.data.entity.security.JwtRequest
import com.itis.timetable.data.entity.security.UserInfo
import com.itis.timetable.data.repositories.UserRepository
import com.itis.timetable.security.JwtTokenUtil
import com.itis.timetable.security.JwtUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Lazy
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse


@Controller
@CrossOrigin
class LoginController {

    @Lazy
    @Autowired
    lateinit var authenticationManager: AuthenticationManager

    @Autowired
    lateinit var jwtTokenUtil: JwtTokenUtil

    @Autowired
    lateinit var userDetailsService: JwtUserDetailsService

    @Autowired
    lateinit var repository: UserRepository

    @GetMapping("/login")
    fun get(): String {
        return "login"
    }

    @PostMapping("/login", consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun createAuthenticationToken(authenticationRequest: JwtRequest, response: HttpServletResponse): ResponseEntity<Any> {
        authenticate(authenticationRequest.username, authenticationRequest.password)
        val userDetails = userDetailsService.loadUserByUsername(authenticationRequest.username)
        println("Password: " + userDetails.password)
        val token = jwtTokenUtil.generateToken(userDetails.username)
        val cookie = Cookie("token", token)
        response.addCookie(cookie)
        response.sendRedirect("/")
        return ResponseEntity.ok().build()
    }

    fun createAuthenticationTokenNew(authenticationRequest: JwtRequest, response: HttpServletResponse): ResponseEntity<Any> {
        println(authenticationRequest.username + authenticationRequest.password)

        authenticate(authenticationRequest.username, authenticationRequest.password)

        println(authenticationRequest.username + authenticationRequest.password)

        repository.save(UserInfo(1, authenticationRequest.username, authenticationRequest.password))

        val token = jwtTokenUtil.generateToken(authenticationRequest.username)
        val cookie = Cookie("token", token)
        response.addCookie(cookie)
        response.sendRedirect("/")
        return ResponseEntity.ok().build()
    }

    private fun authenticate(username: String, password: String) {
        try {
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(username, password))
        } catch (e: DisabledException) {
            throw Exception("USER_DISABLED", e)
        } catch (e: BadCredentialsException) {
            throw Exception("INVALID_CREDENTIALS", e)
        }
    }
}
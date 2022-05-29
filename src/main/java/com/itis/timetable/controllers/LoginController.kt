package com.itis.timetable.controllers

import com.itis.timetable.data.entity.security.JwtRequest
import com.itis.timetable.data.entity.security.JwtResponse
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
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.*


@Controller
class LoginController {

    @GetMapping("/authenticate")
    fun get(): String {
        return "login"
    }
/*
    @PostMapping("/login")
    fun post(
        @RequestParam("nickname") nickname: String,
        @RequestParam("password") password: String,
    ) {

    }*/
}

@RestController
@CrossOrigin
class JwtAuthenticationController {
    @Lazy
    @Autowired
    private val authenticationManager: AuthenticationManager? = null

    @Autowired
    private val jwtTokenUtil: JwtTokenUtil? = null

    @Autowired
    lateinit var userDetailsService: JwtUserDetailsService

    @RequestMapping(value = ["/authenticate"], method = [RequestMethod.POST], consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    @Throws(Exception::class)
    fun createAuthenticationToken(authenticationRequest: JwtRequest): ResponseEntity<*> {
        print("MAP: $authenticationRequest")
        authenticate(authenticationRequest.username, authenticationRequest.password)
        val userDetails = userDetailsService.loadUserByUsername(authenticationRequest.username)
        val token = jwtTokenUtil!!.generateToken(userDetails)
        return ResponseEntity.ok<Any>(JwtResponse(token))
    }

    @Throws(Exception::class)
    private fun authenticate(username: String, password: String) {
        try {
            authenticationManager!!.authenticate(UsernamePasswordAuthenticationToken(username, password))
        } catch (e: DisabledException) {
            throw Exception("USER_DISABLED", e)
        } catch (e: BadCredentialsException) {
            throw Exception("INVALID_CREDENTIALS", e)
        }
    }
}
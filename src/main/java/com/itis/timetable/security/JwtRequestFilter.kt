package com.itis.timetable.security

import io.jsonwebtoken.ExpiredJwtException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
open class JwtRequestFilter : OncePerRequestFilter() {
    @Autowired
    private val jwtUserDetailsService: JwtUserDetailsService? = null

    @Autowired
    lateinit var jwtTokenUtil: JwtTokenUtil

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val jwtToken = request.cookies
            .filter { cookie -> cookie.name == "token" }
            .map(Cookie::getValue)
            .firstOrNull()

        var username: String? = null

        try {
            username = jwtTokenUtil.getUsernameFromToken(jwtToken)
        } catch (e: IllegalArgumentException) {
            println("Unable to get JWT Token")
        } catch (e: ExpiredJwtException) {
            println("JWT Token has expired")
        }

        // Once we get the token validate it.
        if (username != null && SecurityContextHolder.getContext().authentication == null) {
            val userDetails = jwtUserDetailsService!!.loadUserByUsername(username)

            // if token is valid configure Spring Security to manually set
            // authentication
            if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
                val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.authorities
                )
                usernamePasswordAuthenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                // After setting the Authentication in the context, we specify
                // that the current user is authenticated. So it passes the
                // Spring Security Configurations successfully.
                SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
            }
        }
        chain.doFilter(request, response)
    }
}
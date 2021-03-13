package com.churchqr.controller

import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.Exception


open class BaseController {

    data class ErrorMsgDto(
        val code: String,
        val msg: String
    )

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(*[EmptyResultDataAccessException::class])
    open fun handleBusinessRule(e: Exception): ResponseEntity<*>? {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorMsgDto("EMPTY_RESULT", "No objects found."))
    }

}
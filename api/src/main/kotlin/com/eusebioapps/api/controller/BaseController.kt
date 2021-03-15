package com.eusebioapps.api.controller

import com.eusebioapps.api.model.exception.BusinessRuleException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import java.util.HashMap
import java.util.function.Consumer

open class BaseController {

    private val logger = LoggerFactory.getLogger(javaClass)

    data class ErrorMessageDto(
        val message: String?,
        val errors: MutableMap<String, String?>?
    )

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ErrorMessageDto {
        val errors: MutableMap<String, String?> = HashMap()
        ex.bindingResult.allErrors.forEach(Consumer { error: ObjectError ->
            val fieldName = (error as FieldError).field
            val errorMessage = error.getDefaultMessage()
            errors[fieldName] = errorMessage
        })

        val msg = ErrorMessageDto("There are still errors in your given data", errors)
        logger.error("ValidationException [{}]", msg)
        return msg
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(BusinessRuleException::class)
    fun handleBusinessRuleExceptions(ex: BusinessRuleException) : ErrorMessageDto {
        logger.error("BusinessRuleException [message={}]", ex.message)
        return ErrorMessageDto(ex.message, null)
    }

}
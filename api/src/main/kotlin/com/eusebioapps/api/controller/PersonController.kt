package com.eusebioapps.api.controller

import com.eusebioapps.api.model.Person
import com.eusebioapps.api.service.person.PersonService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Pattern

@CrossOrigin
@RestController
@RequestMapping("/api/v1/person")
class PersonController(private val personService: PersonService) : BaseController() {

    private val logger = LoggerFactory.getLogger(javaClass)

    @GetMapping
    fun findByMobileNo(@RequestParam @Valid @NotEmpty @Pattern(regexp = "^(09)\\d{9}") mobileNo: String) : ResponseEntity<Person> {
        logger.debug("GET /api/v1/person [mobileNo={}]", mobileNo)
        return ResponseEntity.status(HttpStatus.OK).body(personService.findByMobileNo(mobileNo))
    }

}
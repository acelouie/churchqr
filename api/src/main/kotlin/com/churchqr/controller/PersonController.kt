package com.churchqr.controller

import com.churchqr.model.Person
import com.churchqr.service.PersonService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController("/v1/person")
class PersonController(private val personService: PersonService) {

    @GetMapping
    fun get(mobileNo: String) : ResponseEntity<Person> {
        return ResponseEntity.status(HttpStatus.OK).body(personService.findByMobileNo(mobileNo))
    }

}
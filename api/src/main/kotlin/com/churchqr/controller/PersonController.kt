package com.churchqr.controller

import com.churchqr.model.Person
import com.churchqr.service.PersonService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

@CrossOrigin
@RestController
class PersonController(private val personService: PersonService) {

    @GetMapping("/v1/person")
    fun get(@RequestParam @Valid @NotNull @Pattern(regexp = "^(09)\\d{9}") mobileNo: String) : ResponseEntity<Person> {
        return ResponseEntity.status(HttpStatus.OK).body(personService.findByMobileNo(mobileNo))
    }

}
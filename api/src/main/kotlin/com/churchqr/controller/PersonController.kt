package com.churchqr.controller

import com.churchqr.model.Person
import com.churchqr.service.PersonService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

@CrossOrigin
@RestController
class PersonController(private val personService: PersonService) : BaseController() {

    @GetMapping("/v1/person")
    fun get(@RequestParam mobileNo: String) : ResponseEntity<Person> {
        return ResponseEntity.status(HttpStatus.OK).body(personService.findByMobileNo(mobileNo))
    }

}
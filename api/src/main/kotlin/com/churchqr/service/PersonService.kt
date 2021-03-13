package com.churchqr.service

import com.churchqr.repository.PersonRepository
import com.churchqr.model.Person
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service

@Service
class PersonService(private val personRepository: PersonRepository) {

    fun findByMobileNo(mobileNo : String) : Person? {
        return personRepository.findByMobileNo(mobileNo)
    }

}
package com.eusebioapps.api.service.person

import com.eusebioapps.api.repository.PersonRepository
import com.eusebioapps.api.model.Person
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service

@Service
class PersonServiceImpl(private val personRepository: PersonRepository) : PersonService {

    override fun findByMobileNo(mobileNo : String) : Person? {
        // TODO Null handling
        return personRepository.findByMobileNo(mobileNo)
    }

}
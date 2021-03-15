package com.eusebioapps.api.service.person

import com.eusebioapps.api.repository.PersonRepository
import com.eusebioapps.api.model.Person
import com.eusebioapps.api.model.exception.BusinessRuleException
import org.springframework.stereotype.Service

@Service
class PersonServiceImpl(private val personRepository: PersonRepository) : PersonService {

    override fun findByMobileNo(mobileNo: String): Person? {
        return personRepository.findByMobileNo(mobileNo)
            ?: throw BusinessRuleException("Person with mobile number $mobileNo does not exist.")
    }

}
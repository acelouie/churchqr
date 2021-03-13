package com.churchqr.service

import com.churchqr.datasource.PersonDataSource
import com.churchqr.model.Person
import org.springframework.stereotype.Service

@Service
class PersonService(private val dataSource: PersonDataSource) {

    fun add(newEntity : Person) : Person {
        return dataSource.save(newEntity)
    }

    fun findByMobileNo(mobileNo : String) : Person {
        return dataSource.findByMobileNo(mobileNo)
    }


}
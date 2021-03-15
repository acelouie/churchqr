package com.eusebioapps.api.repository

import com.eusebioapps.api.model.Person
import org.springframework.data.jpa.repository.JpaRepository

interface PersonRepository : JpaRepository<Person, String> {

    fun findByMobileNo(mobileNo : String) : Person?

}
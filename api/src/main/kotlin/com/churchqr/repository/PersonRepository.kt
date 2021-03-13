package com.churchqr.repository

import com.churchqr.model.Person
import org.springframework.data.jpa.repository.JpaRepository

interface PersonRepository : JpaRepository<Person, String> {

    fun findByMobileNo(mobileNo : String) : Person?

}
package com.churchqr.repository

import com.churchqr.model.Person
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface PersonRepository : JpaRepository<Person, UUID> {

    fun findByMobileNo(mobileNo : String) : Person

}
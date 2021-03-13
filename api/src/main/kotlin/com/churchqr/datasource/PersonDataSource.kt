package com.churchqr.datasource

import com.churchqr.model.Person
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface PersonDataSource : JpaRepository<Person, UUID> {

    fun findByMobileNo(mobileNo : String) : Person

}
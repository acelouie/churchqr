package com.eusebioapps.api.service.person

import com.eusebioapps.api.model.Person

interface PersonService {

    fun findByMobileNo(mobileNo : String) : Person?

}
package com.churchqr.model

import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Person() {

    @get:Id @get:GeneratedValue private var id : UUID
        get() = id
        set(id) { this.id = id }

    private var mobileNo : String
        get() = mobileNo
        set(mobileNo) { this.mobileNo = mobileNo }

    private var name : String
        get() = name
        set(name) { this.name = name }

    constructor(mobileNo : String, name : String) : this() {
        this.mobileNo = mobileNo
        this.name = name
    }

    // TODO equals
    // TODO hashCode

}
package com.churchqr.model

import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Person() {

    @get:Id @get:GeneratedValue private var id : UUID
        get() = id
        set(id) { this.id = id }

    @get:Column(length = 20)
    private var mobileNo : String
        get() = mobileNo
        set(mobileNo) { this.mobileNo = mobileNo }

    @get:Column(length = 50)
    private var name : String
        get() = name
        set(name) { this.name = name }

    constructor(mobileNo : String, name : String) : this() {
        this.mobileNo = mobileNo
        this.name = name
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Person

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }


}
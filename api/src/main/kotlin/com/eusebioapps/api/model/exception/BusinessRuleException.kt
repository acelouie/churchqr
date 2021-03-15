package com.eusebioapps.api.model.exception

import java.lang.Exception

open class BusinessRuleException(override val message: String?) : Exception() {
}
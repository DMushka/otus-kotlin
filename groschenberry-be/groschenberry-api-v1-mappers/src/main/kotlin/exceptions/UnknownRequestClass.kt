package com.otus.otuskotlin.groschenberry.mappers.v1.exceptions

class UnknownRequestClass(clazz: Class<*>) : RuntimeException("Class $clazz cannot be mapped to GrschbrContext")

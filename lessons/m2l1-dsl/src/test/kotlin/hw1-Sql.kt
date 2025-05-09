@file:Suppress("unused")

package ru.otus.otuskotlin.m2l1

import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@DslMarker
annotation class SQLDsl

@SQLDsl
fun query(block: SqlSelectBuilder.() -> Unit): SqlSelectBuilder {
    return SqlSelectBuilder().apply(block)
}

@SQLDsl
class SqlSelectBuilder {

    private var from : String = ""
    private var select : String = ""
    private var where : String = ""
    private var orDsl = OrDsl()

    @SQLDsl
    fun from(from : String) {
        require(from.isNotEmpty())
        this.from = from
    }

    @SQLDsl
    fun select (vararg select : String) {
        select.forEachIndexed { index, s ->
            this.select += "$s${if (index == select.size - 1) "" else ", "}"
        }
    }

    @SQLDsl
    class Condition(
        val first : String,
        val second: Any?,
        val isEquals : Boolean
    )

    @SQLDsl
    class OrDsl{

        var orList = mutableListOf<Condition>()

        @SQLDsl
        infix fun String.eq(arg: Any?) {
            orList.add( Condition(this, arg, true) )
        }

        @SQLDsl
        infix fun String.nonEq(arg: Any?) {
            orList.add( Condition(this, arg, false) )
        }
    }

    @SQLDsl
    class WhereDsl{

        var where = ""

        var andList = mutableListOf<Condition>()
        @SQLDsl
        infix fun String.eq(arg: Any?) {
            andList.add( Condition(this, arg, true) )
        }

        @SQLDsl
        infix fun String.nonEq(arg: Any?) {
            andList.add( Condition(this, arg, false) )
        }


        private var orDsl = OrDsl()
        fun or(block: OrDsl.() -> Unit) {
            orDsl.apply { block() }
            if (orDsl.orList.isNotEmpty()) {
                where = "("
                orDsl.orList.forEachIndexed { index, item ->
                    where += (if (index == 0) "" else " or ") + eq(item.first, item.second, item.isEquals)
                }
                where += ")"
            }
        }
    }

    @SQLDsl
    fun where (block: WhereDsl.() -> Unit) {

        val whereDsl = WhereDsl()
        whereDsl.apply { block() }

        if (whereDsl.where.isNotEmpty() || whereDsl.andList.isNotEmpty()) {
            where = " where "
            whereDsl.andList.forEachIndexed { index, item ->
                where += eq(item.first, item.second, item.isEquals) +
                        (if (index != whereDsl.andList.size - 1 || whereDsl.where.isNotEmpty() ) " and " else "")
            }
            where += whereDsl.where
        }
    }

    fun build(): String {
        require(from.isNotEmpty())
        return "select ${select.ifEmpty { "*" }} from $from$where"
    }
}

fun eq (arg1: String, arg2: Any?, isEquals: Boolean) : String {
    val exclamation = if (isEquals) "" else "!"
    return when (arg2) {
        null -> "$arg1 ${exclamation}is null"
        is Number -> "$arg1 ${exclamation}= $arg2"
        is String -> "$arg1 ${exclamation}= '$arg2'"
        else -> throw RuntimeException("Illegal type of argument")
    }
}

// Реализуйте dsl для составления sql запроса, чтобы все тесты стали зелеными.
class Hw1Sql {

    private fun checkSQL(expected: String, sql: SqlSelectBuilder) {
        assertEquals(expected, sql.build())
    }

    @Test
    fun `simple select all from table`() {
        val expected = "select * from table"

        val real = query {
            from("table")
        }

        checkSQL(expected, real)
    }

    @Test
    fun `check that select can't be used without table`() {
        assertFailsWith<Exception> {
            query {
                select("col_a")
            }.build()
        }
    }

    @Test
    fun `select certain columns from table`() {
        val expected = "select col_a, col_b from table"

        val real = query {
            select("col_a", "col_b")
            from("table")
        }

        checkSQL(expected, real)
    }

    @Test
    fun `select certain columns from table 1`() {
//        val expected = "select col_a, col_b from table"
//
//        val real = query {
//            select("col_a", "col_b")
//            from("table")
//        }
//
//        checkSQL(expected, real)
    }

    /**
     * __eq__ is "equals" function. Must be one of char:
     *  - for strings - "="
     *  - for numbers - "="
     *  - for null - "is"
     */
    @Test
    fun `select with complex where condition with one condition`() {
        val expected = "select * from table where col_a = 'id'"

        val real = query {
            from("table")
            where { "col_a" eq "id" }
        }

        checkSQL(expected, real)
    }

    /**
     * __nonEq__ is "non equals" function. Must be one of chars:
     *  - for strings - "!="
     *  - for numbers - "!="
     *  - for null - "!is"
     */
    @Test
    fun `select with complex where condition with two conditions`() {
        val expected = "select * from table where col_a != 0"

        val real = query {
            from("table")
            where {
                "col_a" nonEq 0
            }
        }

        checkSQL(expected, real)
    }

    @Test
    fun `when 'or' conditions are specified then they are respected`() {
        val expected = "select * from table where (col_a = 4 or col_b !is null)"

        val real = query {
            from("table")
            where {
                or {
                    "col_a" eq 4
                    "col_b" nonEq null
                }
            }
        }

        checkSQL(expected, real)
    }

    @Test
    fun `when 'and' and 'or' conditions are specified then they are respected`() {
        val expected = "select * from table where col_a = 1 and (col_b = 2 or col_c !is null)"

        val real = query {
            from("table")
            where {
                "col_a" eq 1
                or {
                    "col_b" eq 2
                    "col_c" nonEq null
                }
            }
        }

        checkSQL(expected, real)
    }

}

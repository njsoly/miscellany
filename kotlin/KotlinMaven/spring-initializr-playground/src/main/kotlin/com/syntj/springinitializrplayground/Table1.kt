package com.syntj.springinitializrplayground


import javax.persistence.*

/**
 * An attempt at creating my first simple bean.
 * This is an object from the table1 table.
 */
@Entity
@Table(name="Table1")
data class Table1 (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,
    val name: String
)

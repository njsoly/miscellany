package com.syntj.springinitializrplayground

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface Table1Repository : CrudRepository<Table1?, Long?>

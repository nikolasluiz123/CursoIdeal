package br.com.cursoideal.model

import br.com.cursoideal.transferobject.TOCourse

class Course(
    var name: String = "",
    var value: String = ""
) {
    constructor(toCourse: TOCourse) : this(toCourse.name, toCourse.value)
}

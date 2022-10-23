package br.com.cursoideal.model

import br.com.cursoideal.transferobject.TOCourse

class Course(
    var name: String = "",
    var value: Double = 0.0,
    var modality: String = "",
    var duration: Int = 0
) {
    constructor(toCourse: TOCourse) : this(toCourse.name, toCourse.value, toCourse.modality, toCourse.duration)
}

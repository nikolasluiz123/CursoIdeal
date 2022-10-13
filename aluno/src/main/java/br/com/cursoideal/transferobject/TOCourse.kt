package br.com.cursoideal.transferobject

import br.com.cursoideal.model.Course

class TOCourse(
    id: String? = null,
    var name: String = "",
    var value: String = ""
) : BaseTO(id) {

    constructor(id: String, course: Course) : this(id, course.name, course.value)

}

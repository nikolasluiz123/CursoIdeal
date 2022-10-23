package br.com.cursoideal.transferobject

import br.com.cursoideal.model.Course

class TOCourse(
    id: String? = null,
    var name: String = "",
    var value: Double = 0.0,
    var modality: String = "",
    var duration: Int = 0,
    var rating: Int = 0
) : BaseTO(id) {

    constructor(id: String, course: Course) : this(id, course.name, course.value, course.modality, course.duration)

}

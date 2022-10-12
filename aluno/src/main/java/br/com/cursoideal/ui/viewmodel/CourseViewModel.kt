package br.com.cursoideal.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.cursoideal.repository.CourseRepository
import br.com.cursoideal.transferobject.TOCourse

class CourseViewModel(
    private val courseRepository: CourseRepository,
    var toCourse: MutableLiveData<TOCourse> = MutableLiveData(TOCourse())
) : ViewModel() {
}
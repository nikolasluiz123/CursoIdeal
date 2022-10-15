package br.com.cursoideal.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.cursoideal.repository.CourseRepository
import br.com.cursoideal.repository.Response
import br.com.cursoideal.repository.ResponseVoid
import br.com.cursoideal.transferobject.TOCourse

class CourseViewModel(
    private val courseRepository: CourseRepository,
    var toCourse: MutableLiveData<TOCourse> = MutableLiveData(TOCourse())
) : ViewModel() {

    fun save(toCourse: TOCourse, institutionId: String): LiveData<Response<TOCourse>> = courseRepository.save(toCourse, institutionId)

    fun findBy(institutionId: String): LiveData<Response<List<TOCourse>>> = courseRepository.findBy(institutionId)

    fun delete(institutionId: String, courseId: String): LiveData<ResponseVoid> = courseRepository.delete(institutionId, courseId)
}
package br.com.cursoideal.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.cursoideal.model.Course
import br.com.cursoideal.model.Institution
import br.com.cursoideal.repository.enumerations.FirebaseCollections
import br.com.cursoideal.transferobject.TOCourse
import br.com.cursoideal.transferobject.TOCourseComplete
import br.com.cursoideal.transferobject.TOInstitution
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

class CourseRepository(private val firebaseFirestore: FirebaseFirestore) {

    fun save(toCourse: TOCourse, institutionId: String): LiveData<Response<TOCourse>> = MutableLiveData<Response<TOCourse>>().apply {
        val institutionsCollection = firebaseFirestore.collection(FirebaseCollections.INSTITUTIONS.value)
        val coursesCollection = institutionsCollection.document(institutionId).collection(FirebaseCollections.COURSES.value)
        val document = toCourse.id?.let(coursesCollection::document) ?: coursesCollection.document()

        document.set(Course(toCourse)).addOnCompleteListener { task ->
            toCourse.id = document.id
            value = Response(task.isSuccessful, toCourse, task.exception?.message)
        }
    }

    fun findBy(institutionId: String): LiveData<Response<List<TOCourse>>> = MutableLiveData<Response<List<TOCourse>>>().apply {
        val list: MutableList<TOCourse> = mutableListOf()
        val institutionsCollection = firebaseFirestore.collection(FirebaseCollections.INSTITUTIONS.value)
        val coursesCollection = institutionsCollection.document(institutionId).collection(FirebaseCollections.COURSES.value)

        coursesCollection.get().addOnSuccessListener { querySnapshot ->
            querySnapshot.documents.forEach { document ->
                document.toObject<Course>()?.let { course ->
                    list.add(TOCourse(document.id, course))
                }
            }

            value = Response(true, list)
        }.addOnFailureListener { value = Response(false, error = it.message) }
    }

    fun findBy(institutionId: String, courseId: String): LiveData<Response<TOCourseComplete>> = MutableLiveData<Response<TOCourseComplete>>().apply {
        val institutionDocument = firebaseFirestore.collection(FirebaseCollections.INSTITUTIONS.value).document(institutionId)

        institutionDocument.get().addOnCompleteListener { instDoc ->

            instDoc.result.toObject<Institution>()?.let { institution ->
                val toInstitution = TOInstitution(instDoc.result.id, institution)

                institutionDocument.collection(FirebaseCollections.COURSES.value).document(courseId).get().addOnCompleteListener { courseDoc ->
                    courseDoc.result.toObject<Course>()?.let { course ->
                        val toCourse = TOCourse(courseDoc.result.id, course)
                        value = Response(courseDoc.isSuccessful, TOCourseComplete(toInstitution, toCourse), courseDoc.exception?.message)
                    }
                }
            }
        }
    }

    fun delete(institutionId: String, courseId: String): LiveData<ResponseVoid> = MutableLiveData<ResponseVoid>().apply {
        val institutionsCollection = firebaseFirestore.collection(FirebaseCollections.INSTITUTIONS.value)
        val coursesCollection = institutionsCollection.document(institutionId).collection(FirebaseCollections.COURSES.value)
        val document = coursesCollection.document(courseId)

        document.delete().addOnCompleteListener { task ->
            value = ResponseVoid(task.isSuccessful, task.exception?.message)
        }
    }

    fun findAll(): LiveData<Response<List<TOCourseComplete>>> = MutableLiveData<Response<List<TOCourseComplete>>>().apply {
        val courses = mutableListOf<TOCourseComplete>()

        firebaseFirestore.collection(FirebaseCollections.INSTITUTIONS.value).get().addOnCompleteListener { institutionQuerySnapshot ->
            institutionQuerySnapshot.result.forEach { institutionDocument ->
                val institution = institutionDocument.toObject<Institution>()
                val toInstitution = TOInstitution(institutionDocument.id, institution)

                value = Response(institutionQuerySnapshot.isSuccessful, courses, institutionQuerySnapshot.exception?.message)

                if (institutionQuerySnapshot.isSuccessful) {
                    institutionDocument.reference.collection(FirebaseCollections.COURSES.value).get()
                        .addOnCompleteListener { courseQuerySnapshot ->

                            value = Response(courseQuerySnapshot.isSuccessful, courses, courseQuerySnapshot.exception?.message)

                            if (courseQuerySnapshot.isSuccessful) {
                                courseQuerySnapshot.result.forEach { courseDocument ->
                                    val course = courseDocument.toObject<Course>()
                                    val toCourse = TOCourse(courseDocument.id, course)
                                    val toCourseComplete = TOCourseComplete(toInstitution, toCourse)
                                    courses.add(toCourseComplete)
                                }

                                value = Response(true, courses)
                            }
                        }
                }
            }
        }
    }
}

package br.com.cursoideal.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.cursoideal.model.Course
import br.com.cursoideal.repository.enumerations.FirebaseCollections
import br.com.cursoideal.transferobject.TOCourse
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

}

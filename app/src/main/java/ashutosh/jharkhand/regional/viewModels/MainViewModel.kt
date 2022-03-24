package ashutosh.jharkhand.regional.viewModels

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ashutosh.jharkhand.regional.models.Category
import ashutosh.jharkhand.regional.models.Question
import ashutosh.jharkhand.regional.models.Set
import ashutosh.jharkhand.regional.models.Topic
import ashutosh.jharkhand.regional.utils.Constants
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val db = Firebase.firestore

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>>
        get() = _categories

    private val _currentTopics = MutableLiveData<List<Topic>>()
    val currentTopics: LiveData<List<Topic>>
        get() = _currentTopics

    private val _currentSets = MutableLiveData<List<Set>>()
    val currentSets: LiveData<List<Set>>
        get() = _currentSets

    private val _currentQuestion = MutableLiveData<List<Question>>()
    val currentQuestion: LiveData<List<Question>>
        get() = _currentQuestion

    init {
        getDataFromFirebase()
    }

    private fun getDataFromFirebase() {
        db.collection(Constants.CATEGORIES_COLLECTION).orderBy(Constants.CATEGORIES_TIME_FIELD).get()
            .addOnSuccessListener { documents ->
                if (documents != null) {
                    val newCategories = ArrayList<Category>()
                    for (document in documents) {
                        newCategories.add(
                            Category(
                                document.id,
                                document.getString(Constants.CATEGORIES_NAME_FIELD) ?: "",
                                document.getString(Constants.CATEGORIES_IMAGE_FIELD) ?: "",
                                document.getLong(Constants.CATEGORIES_TIME_FIELD) ?: 0
                            )
                        )
                    }
                    _categories.value = newCategories
                }
            }
            .addOnFailureListener {
                Toast.makeText(getApplication(), it.message, Toast.LENGTH_SHORT).show()
            }

    }

    private fun getTopicsFromFirebase(categoryID: String) {
        db.collection(Constants.CATEGORIES_COLLECTION).document(categoryID)
            .collection(Constants.TOPIC_COLLECTION).orderBy(Constants.TOPIC_TIME_FIELD).get()
            .addOnSuccessListener { documents ->
                if (documents != null) {
                    val newTopics = ArrayList<Topic>()
                    for (document in documents) {
                        newTopics.add(
                            Topic(
                                document.id,
                                document.getString(Constants.TOPIC_NAME_FIELD) ?: "",
                                document.getLong(Constants.TOPIC_TIME_FIELD) ?: 0
                            )
                        )
                    }
                    _currentTopics.value = newTopics
                }
            }
            .addOnFailureListener {
                Toast.makeText(getApplication(), it.message, Toast.LENGTH_SHORT).show()
            }
    }

    private fun getSetsFromFirebase(categoryId: String, topicId: String) {
        db.collection(Constants.CATEGORIES_COLLECTION).document(categoryId)
            .collection(Constants.TOPIC_COLLECTION).document(topicId)
            .collection(Constants.SET_COLLECTION).orderBy(Constants.SET_NUMBER_FIELD).get()
            .addOnSuccessListener { documents ->
                if (documents != null) {
                    val newSets = ArrayList<Set>()
                    for (document in documents) {
                        newSets.add(
                            Set(
                                document.id,
                                (document.get(Constants.SET_NUMBER_FIELD) as Long?)?.toInt() ?: 0
                            )
                        )
                    }
                    _currentSets.value = newSets
                }
            }
            .addOnFailureListener {
                Toast.makeText(getApplication(), it.message, Toast.LENGTH_SHORT).show()
            }
    }

    private fun getQuestionsFromFirebase(categoryId: String, topicId: String, setId: String) {
        db.collection(Constants.CATEGORIES_COLLECTION).document(categoryId)
            .collection(Constants.TOPIC_COLLECTION).document(topicId)
            .collection(Constants.SET_COLLECTION).document(setId)
            .collection(Constants.QUESTION_COLLECTION).orderBy(Constants.QUESTION_TIME_FIELD).get()
            .addOnSuccessListener { documents ->
                if (documents != null) {
                    val newQuestions = ArrayList<Question>()
                    for (document in documents) {
                        newQuestions.add(
                            Question(
                                document.id,
                                document.getString(Constants.QUESTION_QUESTION_FIELD) ?: "",
                                document.getString(Constants.QUESTION_OPTION_1_FIELD) ?: "",
                                document.getString(Constants.QUESTION_OPTION_2_FIELD) ?: "",
                                document.getString(Constants.QUESTION_OPTION_3_FIELD) ?: "",
                                document.getString(Constants.QUESTION_OPTION_4_FIELD) ?: "",
                                (document.get(Constants.QUESTION_CORRECT_ANSWER_FIELD) as Long?)?.toInt() ?: 0,
                                document.getLong(Constants.QUESTION_TIME_FIELD) ?: 0
                            )
                        )
                    }
                    _currentQuestion.value = newQuestions
                }
            }
            .addOnFailureListener {
                Toast.makeText(getApplication(), it.message, Toast.LENGTH_SHORT).show()
            }
    }

    fun updateTopics(categoryId: String) {
        getTopicsFromFirebase(categoryId)
    }

    fun updateSets(categoryId: String, topicId: String) {
        getSetsFromFirebase(categoryId, topicId)
    }


}
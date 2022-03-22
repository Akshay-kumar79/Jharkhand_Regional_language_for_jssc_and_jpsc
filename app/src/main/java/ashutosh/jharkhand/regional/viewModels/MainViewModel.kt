package ashutosh.jharkhand.regional.viewModels

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ashutosh.jharkhand.regional.models.Category
import ashutosh.jharkhand.regional.models.Question
import ashutosh.jharkhand.regional.models.Set
import ashutosh.jharkhand.regional.models.Topic
import ashutosh.jharkhand.regional.utils.Constants
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val db = Firebase.firestore

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>>
        get() = _categories

    private var allCategories = ArrayList<Category>()
    private var allTopics = ArrayList<Topic>()
    private var allSets = ArrayList<Set>()
    private var allQuestions = ArrayList<Question>()

    init {
        getDataFromFirebase()
    }

    private fun getDataFromFirebase() {
        db.collection(Constants.CATEGORIES_COLLECTION).get()
            .addOnSuccessListener { documents ->
                if (documents != null) {
                    for (document in documents) {
                        allCategories.add(
                            Category(
                                document.id,
                                document.getString(Constants.CATEGORIES_NAME_FIELD) ?: "",
                                document.getString(Constants.CATEGORIES_IMAGE_FIELD) ?: "",
                                document.getLong(Constants.CATEGORIES_TIME_FIELD) ?: 0
                            )
                        )
                        getTopicsFromFirebase(document.id)
                    }
                }
            }
            .addOnFailureListener {
                Toast.makeText(getApplication(), it.message, Toast.LENGTH_SHORT).show()
            }
    }

    private fun getTopicsFromFirebase(categoryID: String) {
        db.collection(Constants.CATEGORIES_COLLECTION).document(categoryID)
            .collection(Constants.TOPIC_COLLECTION).get()
            .addOnSuccessListener { documents ->
                if (documents != null) {
                    for (document in documents) {
                        allTopics.add(
                            Topic(
                                document.id,
                                categoryID,
                                document.getString(Constants.TOPIC_NAME_FIELD) ?: "",
                                document.getLong(Constants.TOPIC_TIME_FIELD) ?: 0
                            )
                        )
                        getSetsFromFirebase(categoryID, document.id)
                    }
                }
            }
            .addOnFailureListener {
                Toast.makeText(getApplication(), it.message, Toast.LENGTH_SHORT).show()
            }
    }

    private fun getSetsFromFirebase(categoryId: String, topicId: String) {
        db.collection(Constants.CATEGORIES_COLLECTION).document(categoryId)
            .collection(Constants.TOPIC_COLLECTION).document(topicId)
            .collection(Constants.SET_COLLECTION).get()
            .addOnSuccessListener { documents ->
                if (documents != null) {
                    for (document in documents) {
                        allSets.add(
                            Set(
                                document.id,
                                categoryId,
                                topicId,
                                (document.get(Constants.SET_NUMBER_FIELD) as Long?)?.toInt() ?: 0
                            )
                        )
                        getQuestionsFromFirebase(categoryId, topicId, document.id)
                    }
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
            .collection(Constants.QUESTION_COLLECTION).get()
            .addOnSuccessListener { documents ->
                if (documents != null){
                    for (document in documents) {
                        allQuestions.add(
                            Question(
                                document.id,
                                categoryId,
                                topicId,
                                setId,
                                document.getString(Constants.QUESTION_QUESTION_FIELD)?: "",
                                document.getString(Constants.QUESTION_OPTION_1_FIELD)?: "",
                                document.getString(Constants.QUESTION_OPTION_2_FIELD)?: "",
                                document.getString(Constants.QUESTION_OPTION_3_FIELD)?: "",
                                document.getString(Constants.QUESTION_OPTION_4_FIELD)?: "",
                                (document.get(Constants.QUESTION_CORRECT_ANSWER_FIELD) as Long?)?.toInt() ?: 0,
                                document.getLong(Constants.QUESTION_TIME_FIELD)?: 0
                            )
                        )
                    }
                }
            }
            .addOnFailureListener {
                Toast.makeText(getApplication(), it.message, Toast.LENGTH_SHORT).show()
            }
    }

}
package ashutosh.jharkhand.regional.viewModels

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ashutosh.jharkhand.regional.models.Topic
import ashutosh.jharkhand.regional.utils.Constants
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class TopicViewModel(application: Application) : AndroidViewModel(application) {

    private val db = Firebase.firestore

    private val _currentTopics = MutableLiveData<List<Topic>>()
    val currentTopics: LiveData<List<Topic>>
        get() = _currentTopics

    //utils
    private val _topicLoadingStatus = MutableLiveData<String>()
    val topicLoadingStatus: LiveData<String>
        get() = _topicLoadingStatus

    init {
        _topicLoadingStatus.value = MainViewModel.LOADING_STATUS_LOADING
    }

    fun getTopicsFromFirebase(categoryID: String) {
        db.collection(Constants.CATEGORIES_COLLECTION).document(categoryID)
            .collection(Constants.TOPIC_COLLECTION).orderBy(Constants.TOPIC_TIME_FIELD).get()
            .addOnSuccessListener { documents ->
                if (documents != null && documents.size() > 0) {
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
                    _topicLoadingStatus.value = MainViewModel.LOADING_STATUS_COMPLETE
                }else{
                    _topicLoadingStatus.value = MainViewModel.LOADING_STATUS_NO_FOUND
                }
            }
            .addOnFailureListener {
                Toast.makeText(getApplication(), it.message, Toast.LENGTH_SHORT).show()
                _topicLoadingStatus.value = MainViewModel.LOADING_STATUS_NO_FOUND
            }
    }
}
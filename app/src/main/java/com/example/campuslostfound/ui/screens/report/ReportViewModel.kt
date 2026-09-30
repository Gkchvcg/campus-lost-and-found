package com.example.campuslostfound.ui.screens.report

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.campuslostfound.data.local.entity.ItemEntity
import com.example.campuslostfound.domain.repository.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val repository: ItemRepository
) : ViewModel() {

    private val _title = MutableStateFlow("")
    val title = _title.asStateFlow()

    private val _description = MutableStateFlow("")
    val description = _description.asStateFlow()

    private val _category = MutableStateFlow("Electronics")
    val category = _category.asStateFlow()

    private val _location = MutableStateFlow("")
    val location = _location.asStateFlow()

    private val _date = MutableStateFlow("")
    val date = _date.asStateFlow()

    private val _time = MutableStateFlow("")
    val time = _time.asStateFlow()

    private val _contactInfo = MutableStateFlow("")
    val contactInfo = _contactInfo.asStateFlow()

    private val _imageUri = MutableStateFlow<String?>(null)
    val imageUri = _imageUri.asStateFlow()
    
    private val _isSubmitting = MutableStateFlow(false)
    val isSubmitting = _isSubmitting.asStateFlow()
    
    private val _submitSuccess = MutableStateFlow(false)
    val submitSuccess = _submitSuccess.asStateFlow()

    val categories = listOf("Electronics", "Documents", "Books", "Clothing", "Accessories", "Keys", "ID Cards", "Bags", "Other")

    fun onTitleChange(value: String) { _title.value = value }
    fun onDescriptionChange(value: String) { _description.value = value }
    fun onCategoryChange(value: String) { _category.value = value }
    fun onLocationChange(value: String) { _location.value = value }
    fun onDateChange(value: String) { _date.value = value }
    fun onTimeChange(value: String) { _time.value = value }
    fun onContactInfoChange(value: String) { _contactInfo.value = value }
    fun onImageSelected(uri: String?) { _imageUri.value = uri }

    fun submitReport(type: String) {
        if (_title.value.isBlank() || _location.value.isBlank() || _contactInfo.value.isBlank()) {
            return
        }
        
        _isSubmitting.value = true
        
        viewModelScope.launch {
            val item = ItemEntity(
                title = _title.value,
                description = _description.value,
                category = _category.value,
                type = type.uppercase(),
                location = _location.value,
                date = _date.value.ifBlank { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()) },
                time = _time.value,
                imageUri = _imageUri.value,
                contactInfo = _contactInfo.value,
                postedDate = System.currentTimeMillis()
            )
            
            repository.insertItem(item)
            _isSubmitting.value = false
            _submitSuccess.value = true
        }
    }
}

package com.example.myapplication.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.myapplication.models.Template
import com.example.myapplication.repositories.TemplateRepository
import java.util.concurrent.ExecutionException

open class TemplateViewModel(application: Application) : AndroidViewModel(application) {
    val repository: TemplateRepository = TemplateRepository(application)
    val alltemplates: LiveData<List<Template?>?>?
    fun insert(template: Template?) {
        repository.insert(template)
    }

    fun update(template: Template?) {
        repository.update(template)
    }

    fun delete(template: Template?) {
        repository.delete(template)
    }

    @Throws(ExecutionException::class, InterruptedException::class)
    fun getTemplateById(id: Int): Template {
        return repository.getTemplateById(id)
    }

    init {
        alltemplates = repository.allTemplates
    }
}
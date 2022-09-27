package com.web.testtask.presentation.viewmodel

import android.graphics.drawable.Drawable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.web.testtask.AppDelegate.Companion.GIF
import com.web.testtask.data.model.DataModel
import com.web.testtask.data.repository.GifRepository
import com.web.testtask.util.putSharedPref

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import java.io.File
import java.io.FileOutputStream
import java.nio.ByteBuffer

class GifViewModel (
    private val repository: GifRepository,
) : ViewModel() {

    var listCreated: MutableLiveData<PagingData<DataModel>> = MutableLiveData()
    var searchable = ""
    var isOnline = MutableLiveData<Boolean>()

    init {
        init ()
    }

    fun init(isOnline: Boolean = true) {
        viewModelScope.launch {
            repository.getAllGifs(viewModelScope, isOnline) {
                this@GifViewModel.isOnline.value = it
            }.flowOn(Dispatchers.IO).collectLatest {
                listCreated.postValue(it)
            }
        }
    }

    private fun insertGif(data: DataModel) {
        data.isDownloaded = true
        viewModelScope.launch {
            repository.insertGif(data)
        }
    }

    val textChange = debounce<String>(scope = viewModelScope) {
        viewModelScope.launch {
            if (searchable != it) {
                if (it == "")
                    init()
                else
                    repository.getSearched(viewModelScope, it).collectLatest {
                        listCreated.postValue(it)
                    }
            }
        }
        searchable = it
    }

    fun deletenOne(dir: String, data: DataModel) {
        val name = data.id + GIF
        val file = File(dir, name)
        viewModelScope.launch(Dispatchers.IO) {
            if (file.exists())
                file.delete()
            repository.deleteGif(data)
        }
        putSharedPref(data)
        init (false)
    }

    fun downloadGif(path: String, res: Drawable?, model: DataModel) {
        viewModelScope.launch(Dispatchers.IO) {
            val name = model.id + GIF
            val gifFile = File(path, name)
            if (!gifFile.exists()) {
                val byteBuffer = (res as GifDrawable).buffer
                val output = FileOutputStream(gifFile)
                val bytes = ByteArray(byteBuffer.capacity())
                (byteBuffer.duplicate().clear() as ByteBuffer).get(bytes)
                output.write(bytes, 0, bytes.size)
                output.close()
            }
        }
        insertGif(model)
    }

    private fun <T> debounce(
        waitMs: Long = 500L,
        scope: CoroutineScope,
        destinationFunction: (T) -> Unit
    ): (T) -> Unit {
        var debounceJob: Job? = null
        return { param: T ->
            debounceJob?.cancel()
            debounceJob = scope.launch {
                delay(waitMs)
                destinationFunction(param)
            }
        }
    }
}
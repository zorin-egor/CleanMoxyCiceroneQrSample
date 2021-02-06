package com.sample.qr.presentation.ui.screens.participant.movement

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.sample.qr.domain.interactors.participant.ParticipantInteractor
import com.sample.qr.domain.models.Empty
import com.sample.qr.domain.models.Error
import com.sample.qr.domain.models.Success
import com.sample.qr.presentation.R
import com.sample.qr.presentation.extensions.scale
import com.sample.qr.presentation.ui.screens.base.BasePresenter
import com.sample.qr.presentation.ui.screens.common.adapters.models.BaseAboutItem
import com.sample.qr.presentation.ui.screens.common.adapters.models.HeaderAboutItem
import com.sample.qr.presentation.ui.screens.common.adapters.models.ImageAboutItem
import com.sample.qr.presentation.ui.screens.common.adapters.models.TextAboutItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.presenterScope
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class ParticipantAboutMovementPresenter @Inject constructor(
        app: Application,
        router: Router,
        private val interactor: ParticipantInteractor
) : BasePresenter<ParticipantAboutMovementView>(app, router) {

    @Volatile
    private var mBitmap: Bitmap? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        setTestData()
    }
    private fun setTestData() {
        presenterScope.launch {

            val bitmapDeferred = async(Dispatchers.IO) {
                mBitmap ?: BitmapFactory.decodeResource(app.resources, R.drawable.image_background).let { bitmap ->
                    bitmap.scale(0.5f).also { mBitmap = it }
                }
            }

            interactor.getEvent().let {
                when (it) {
                    is Error -> handlerError(it)
                    is Empty -> handlerError()
                    is Success -> {
                        val items = arrayListOf<BaseAboutItem>(
                                HeaderAboutItem(it.value.header),
                                TextAboutItem(it.value.title)
                        )

                        it.value.descriptions.forEach { item ->
                            item.imageUrl?.let { items.add(ImageAboutItem(bitmapDeferred.await()))}
                            item.description?.let { items.add(TextAboutItem(it))}
                        }

                        viewState.onAddItems(items)
                    }
                }
            }
        }
    }
}
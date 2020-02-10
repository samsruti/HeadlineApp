/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.samsruti.headlineapp

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.samsruti.headlineapp.api.NewsApiService
import com.samsruti.headlineapp.database.HeadlinesAppDataBase
import com.samsruti.headlineapp.repository.HeadlineLocalCache
import com.samsruti.headlineapp.repository.HeadlineRepository
import com.samsruti.headlineapp.ui.headlines.ViewModelFactory
import java.util.concurrent.Executors

/**
Dependecy Injection CUSTOM
 */
object DependencyInjection {

    private fun provideCache(context: Context): HeadlineLocalCache {
        val database = HeadlinesAppDataBase.getInstance(context)
        return HeadlineLocalCache(database.getDao(), Executors.newSingleThreadExecutor())
    }


    private fun provideGithubRepository(context: Context): HeadlineRepository {
        return HeadlineRepository(NewsApiService.create(), provideCache(context))
    }


    fun provideViewModelFactory(context: Context): ViewModelProvider.Factory {
        return ViewModelFactory(provideGithubRepository(context))
    }
}

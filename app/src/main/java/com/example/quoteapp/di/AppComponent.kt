package com.example.quoteapp.di

import android.app.Application
import androidx.work.ListenableWorker
import com.example.quoteapp.App
import com.example.quoteapp.workers.ChildWorkerFactory
import com.example.quoteapp.workers.DownloadWorker
import com.example.quoteapp.workers.NotificationWorker
import dagger.*
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.multibindings.IntoMap
import javax.inject.Singleton
import kotlin.reflect.KClass

@Singleton
@Component(
    modules = [
        AppModule::class,
        AndroidSupportInjectionModule::class,
        FragmentInjectorsModule::class,
        QuoteRepositoryModule::class,
        ActivityInjectorsModule::class,
        AppComponent.WorkerBindingModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    override fun inject(application: App)

    @MapKey
    @Target(AnnotationTarget.FUNCTION)
    @Retention(AnnotationRetention.RUNTIME)
    annotation class WorkerKey(val value: KClass<out ListenableWorker>)

    @Module
    interface WorkerBindingModule {
        @Binds
        @IntoMap
        @WorkerKey(DownloadWorker::class)
        fun bindDownloadWorker(factory: DownloadWorker.Factory): ChildWorkerFactory

        @Binds
        @IntoMap
        @WorkerKey(NotificationWorker::class)
        fun bindNotificationWorker(factory: NotificationWorker.Factory): ChildWorkerFactory
    }
}
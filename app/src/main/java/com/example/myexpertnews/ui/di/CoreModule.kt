package com.example.myexpertnews.ui.di

import net.sqlcipher.database.SQLiteDatabase
import androidx.room.Room
import com.example.core.BuildConfig
import com.example.core.data.source.local.FavoriteAgentDatabase
import com.example.core.data.source.remote.AgentsApiService
import com.example.core.data.AgentsRepository
import com.example.core.domain.repository.IAgentsRepository
import com.example.myexpertnews.util.SSLCertificateConfigurator
import net.sqlcipher.database.SupportFactory
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Arrays
import javax.net.ssl.X509TrustManager

val databaseModule = module {
    factory {
        get<FavoriteAgentDatabase>().favoriteAgentsDao()
    }
    single {

        val passphrase : ByteArray = SQLiteDatabase.getBytes("password".toCharArray())
        val factory = SupportFactory(passphrase)

        Room.databaseBuilder(
            androidContext(),
            FavoriteAgentDatabase::class.java,
            "favorite_agents.db"
        )
            .openHelperFactory(factory)
            .build()
    }
}

val networkModule = module {
    single {

        val trustManagerFactory = SSLCertificateConfigurator.getTrustManager(androidContext())
        val trustManagers = trustManagerFactory.trustManagers
        if(trustManagers.size != 1 || trustManagers[0] !is X509TrustManager){
            throw IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers))
        }
        val trustManager = trustManagers[0] as X509TrustManager

        OkHttpClient.Builder()
            .sslSocketFactory(SSLCertificateConfigurator.getSSLConfiguration(androidContext()).socketFactory, trustManager)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_API)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(AgentsApiService::class.java)
    }
}

val repositoryModule = module {
    single<IAgentsRepository> { AgentsRepository(get(), get()) }
}
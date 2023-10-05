package com.example.myexpertnews.util

import android.content.Context
import com.example.myexpertnews.R
import java.io.IOException
import java.security.KeyManagementException
import java.security.KeyStore
import java.security.KeyStoreException
import java.security.NoSuchAlgorithmException
import java.security.cert.CertificateException
import java.security.cert.CertificateFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.security.cert.Certificate
import kotlin.jvm.Throws

object SSLCertificateConfigurator {

    @Throws(
        CertificateException::class,
        IOException::class,
        KeyStoreException::class,
        NoSuchAlgorithmException::class,
        KeyManagementException::class
    )

    fun getSSLConfiguration(context: Context): SSLContext {
        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, getTrustManager(context).trustManagers, null)
        return sslContext
    }

    private fun getKeystore(context: Context): KeyStore {
        val keyStoreType = KeyStore.getDefaultType()
        val keyStore = KeyStore.getInstance(keyStoreType)
        keyStore.load(null, null)
        keyStore.setCertificateEntry("ca", getCertificate(context))
        return keyStore
    }

    fun getTrustManager(context: Context): TrustManagerFactory {
        val trustManagerFactoryAlgorithm = TrustManagerFactory.getDefaultAlgorithm()
        val trustManagerFactory = TrustManagerFactory.getInstance(trustManagerFactoryAlgorithm)
        trustManagerFactory.init(getKeystore(context))
        return trustManagerFactory
    }

    private fun getCertificate(context: Context): java.security.cert.Certificate? {
        val certificateFactory: CertificateFactory? = CertificateFactory.getInstance("X.509")
        return context.resources.openRawResource(R.raw.valowebcert)
            .use { cert -> certificateFactory?.generateCertificate(cert) }
    }
}
package com.jger.util

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.request.RequestHeaders
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient

class ApolloUtil {

    companion object{
        var apolloClient  =                     ApolloClient.builder().okHttpClient(
            OkHttpClient.Builder().addNetworkInterceptor(StethoInterceptor()).build()
        ).serverUrl("https://api.smash.gg/gql/alpha").build()

        var clientHeader = RequestHeaders.builder().addHeader("Authorization","Bearer 9d5df78f984b78368db14b114045f9d1").build()
    }
}
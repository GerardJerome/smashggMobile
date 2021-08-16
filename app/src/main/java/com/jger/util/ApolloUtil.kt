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

        var clientHeader = RequestHeaders.builder().addHeader("Authorization","Bearer ac7d17373569e42d716d7b98d94170ff").build()
    }
}

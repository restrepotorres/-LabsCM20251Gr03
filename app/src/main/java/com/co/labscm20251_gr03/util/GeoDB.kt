package com.co.labscm20251_gr03.util

import android.content.Context
import com.android.volley.NetworkResponse
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.util.Locale

fun obtenerPaises(context: Context, query: String, callback: (List<String>) -> Unit) {
    val lang = Locale.getDefault().language
    val url = "https://wft-geo-db.p.rapidapi.com/v1/geo/countries?namePrefix=$query&limit=5&languageCode=$lang"

    val queue = Volley.newRequestQueue(context)
    val request = object : StringRequest(Method.GET, url, { response ->
        val json = JSONObject(response)
        val data = json.getJSONArray("data")
        val countries = List(data.length()) {
            data.getJSONObject(it).getString("name")
        }
        callback(countries)
    }, { error ->
        callback(emptyList())
    }) {
        override fun getHeaders() = mapOf(
            "X-RapidAPI-Key" to "6e259679f8msheb61e5105e917eep1bbe4djsn2ba3e4db0587",
            "X-RapidAPI-Host" to "wft-geo-db.p.rapidapi.com"
        )

        override fun parseNetworkResponse(response: NetworkResponse): Response<String> {
            val parsed = String(response.data, charset("UTF-8"))
            return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response))
        }
    }

    queue.add(request)
}

fun obtenerCiudades(context: Context, country: String, query: String, callback: (List<String>) -> Unit) {
    val lang = Locale.getDefault().language
    val encodedCountry = country.take(2).uppercase()
    val url = "https://wft-geo-db.p.rapidapi.com/v1/geo/cities?namePrefix=$query&countryIds=$encodedCountry&limit=5&languageCode=$lang"

    val queue = Volley.newRequestQueue(context)
    val request = object : StringRequest(Method.GET, url, { response ->
        val json = JSONObject(response)
        val data = json.getJSONArray("data")
        val cities = List(data.length()) {
            data.getJSONObject(it).getString("name")
        }
        callback(cities)
    }, { error ->
        callback(emptyList())
    }) {
        override fun getHeaders() = mapOf(
            "X-RapidAPI-Key" to "6e259679f8msheb61e5105e917eep1bbe4djsn2ba3e4db0587",
            "X-RapidAPI-Host" to "wft-geo-db.p.rapidapi.com"
        )

        override fun parseNetworkResponse(response: NetworkResponse): Response<String> {
            val parsed = String(response.data, charset("UTF-8"))
            return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response))
        }
    }

    queue.add(request)
}
package com.airmap.airmapsdk

import com.airmap.airmapsdk.clients.AdvisoryClient
import com.airmap.airmapsdk.clients.AircraftClient
import com.airmap.airmapsdk.clients.AirspaceClient
import com.airmap.airmapsdk.clients.EvaluationRequest
import com.airmap.airmapsdk.clients.FlightClient
import com.airmap.airmapsdk.clients.PilotClient
import com.airmap.airmapsdk.clients.RulesClient
import com.airmap.airmapsdk.models.Config
import com.airmap.airmapsdk.models.UpdatePilotRequest
import com.airmap.airmapsdk.models.VerificationRequest
import com.aungkyawpaing.geoshi.adapter.GeoshiJsonAdapterFactory
import com.aungkyawpaing.geoshi.model.Feature
import com.aungkyawpaing.geoshi.model.FeatureCollection
import com.aungkyawpaing.geoshi.model.Geometry
import com.aungkyawpaing.geoshi.model.GeometryCollection
import com.aungkyawpaing.geoshi.model.GeometryType
import com.aungkyawpaing.geoshi.model.LineString
import com.aungkyawpaing.geoshi.model.MultiLineString
import com.aungkyawpaing.geoshi.model.MultiPoint
import com.aungkyawpaing.geoshi.model.MultiPolygon
import com.aungkyawpaing.geoshi.model.Point
import com.aungkyawpaing.geoshi.model.Polygon
import com.serjltt.moshi.adapters.DeserializeOnly
import com.serjltt.moshi.adapters.Wrapped
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import okhttp3.CertificatePinner
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.lang.reflect.Type
import java.util.Date
import java.util.concurrent.TimeUnit

object AirMap {
    lateinit var client: AirMapClient
    private lateinit var config: Config
    // TODO: Remove
    var userId: String? = "auth0|5761a4279732f5844b1db844"
    private var authToken: String =
        "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICI2aWl1cHRkOUM3Z250NnF2SDhpYzFSQzJaWTROYnFLdF9fR3RjSU1pYzZJIn0.eyJqdGkiOiI3MzJmNWEwZS0yYTMxLTQ3ZmUtOTdlNC02MmJkODczNmI5MDAiLCJleHAiOjE1ODQ0MjU0MTgsIm5iZiI6MCwiaWF0IjoxNTg0NDA3NDE4LCJpc3MiOiJodHRwczovL3N0YWdlLmF1dGguYWlybWFwLmNvbS9yZWFsbXMvYWlybWFwIiwiYXVkIjoiaXpOYXhwM2ZKRzFNNFBGODU2VGlBRWk1NEFJT3Eyd0ciLCJzdWIiOiJhdXRoMHw1NzYxYTQyNzk3MzJmNTg0NGIxZGI4NDQiLCJ0eXAiOiJJRCIsImF6cCI6Iml6TmF4cDNmSkcxTTRQRjg1NlRpQUVpNTRBSU9xMndHIiwiYXV0aF90aW1lIjoxNTg0NDA3NDE3LCJzZXNzaW9uX3N0YXRlIjoiZjlmYjUzMTMtZmUxYy00YWQ4LWIxYWItOWI5NDUxYzM4YTQ1IiwiYWNyIjoiMSIsImtjX2lkIjoiYWEzYmY5MTktNGY5Ni00ZTljLThmMDktYzg4ZGZmMzc2MjE1IiwiZW1haWxfdmVyaWZpZWQiOnRydWUsInByZWZlcnJlZF91c2VybmFtZSI6ImF1dGgwfDU3NjFhNDI3OTczMmY1ODQ0YjFkYjg0NCIsImVtYWlsIjoidmFuc2hAYWlybWFwLmNvbSIsInBpY3R1cmUiOiJodHRwczovL3MuZ3JhdmF0YXIuY29tL2F2YXRhci82M2IwOWI0MGFjNGEwN2ExMjdlNzY2MzVhMzIyYTg3Mz9zPTQ4MCZyPXBnJmQ9aHR0cHMlM0ElMkYlMkZjZG4uYXV0aDAuY29tJTJGYXZhdGFycyUyRnZhLnBuZyJ9.UQMlR51QvfeKf8gAjiGq39hJdJ7FsSe3A9W_mwiAxPXtmh6c9lrhA_jlTHPi07A_mORkYzT2ciXZWB-gb83RI_t_ooplTe95LvMg3X8tFAEwqY0MNctSsIlnl8TEAvY9_ndTZ3eNY-Z95UC6Cawuw9wurT3U7kYDoiowwF01h0JM3efIE5ZYRFxRe7zwPOgJycAMfQ2GBRzo1GIzUHYXcfQRxqXtjt1LY8nFZVIV_BBpBxVjzFTbVitoeWOALuSVoQgqqksBvl0kxpjrYsvEw6vJZOQqErpFRRLwyludoLvNuMNJOCwNIF1Aex0sCr590ptdqSE78W5eD40pwSdEbg"
    private val certificatePinner: CertificatePinner
        get() {
            val host = "api.airmap.com"
            val hostJP = "api.airmap.jp"
            // TODO: Add other hosts?
            return CertificatePinner.Builder()
                .add(host, "sha256/47DEQpj8HBSa+/TImW+5JCeuQeRkm5NMpJWZG3hSuFU=")
                .add(host, "sha256/CJlvFGiErgX6zPm0H+oO/TRbKOERdQOAYOs2nUlvIQ0=")
                .add(host, "sha256/8Rw90Ej3Ttt8RRkrg+WYDS9n7IS03bk5bjP/UXPtaY8=")
                .add(host, "sha256/Ko8tivDrEjiY90yGasP6ZpBU4jwXvHqVvQI0GS3GNdA=")
                .add(hostJP, "sha256/W5rhIQ2ZbJKFkRvsGDwQVS/H/NSixP33+Z/fpJ0O25Q=")
                .build()
        }

    fun init(
        config: Config,
        enableCertificatePinning: Boolean = false,
        okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
    ) {
        this.config = config
        val moshi = Moshi.Builder()
            .add(Wrapped.ADAPTER_FACTORY) // This needs to be the first adapter added to Moshi
            .add(DeserializeOnly.ADAPTER_FACTORY)
            .add(GeoshiJsonAdapterFactory())
            .add(GeometryJsonAdapterFactory())
            // .add(AdvisoryJsonAdapterFactory())
            .add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe())
            .build()

        val okHttpClient = okHttpClientBuilder.apply {
            if (enableCertificatePinning) certificatePinner(certificatePinner)
            connectTimeout(15, TimeUnit.SECONDS)
            readTimeout(15, TimeUnit.SECONDS)
            // API Key Interceptor
            addInterceptor { it.addHeaderToRequest("X-API-Key", config.airmap.apiKey) }

            // Auth Token Interceptor - We always add the interceptor regardless of if the token has a value yet. We
            // check inside the Interceptor for a token value since the Interceptor itself is persistent
            addInterceptor {
                when {
                    // TODO: Login logic (Add a listener when auth token is blank for apps to be able to consume?)
                    authToken.isNullOrBlank() -> it.proceed(it.request())
                    else -> it.addHeaderToRequest("Authorization", "Bearer $authToken")
                }
            }
        }.build()

        client = AirMapClient(
            getClient("aircraft", 2, okHttpClient, moshi),
            getClient("pilot", 2, okHttpClient, moshi),
            getClient("flight", 2, okHttpClient, moshi),
            getClient("airspace", 2, okHttpClient, moshi),
            getClient("rules", 1, okHttpClient, moshi),
            getClient("advisory", 1, okHttpClient, moshi)
        )
    }

    /**
     * Convenience method to simplify the verbose syntax of using an Interceptor to add a Header
     */
    private fun Interceptor.Chain.addHeaderToRequest(name: String, value: String): Response {
        return this.proceed(this.request().newBuilder().addHeader(name, value).build())
    }

    /**
     * Instantiate a client for the given service name and version
     *
     * @param T The Client abstract class to instantiate
     * @param serviceName The service name as used in the API URL
     * @param v The API version to access
     * @param client OkHttpClient instance used to create the client
     * @param moshi Moshi instance used for JSON serialization and deserialization
     * @return
     */
    private inline fun <reified T> getClient(
        serviceName: String,
        v: Int,
        client: OkHttpClient,
        moshi: Moshi
    ): T {
        val prefix =
            if (config.airmap.environment.isNullOrBlank()) "" else "${config.airmap.environment}."
        val baseUrl = HttpUrl.Builder()
            .scheme("https")
            .host("${prefix}api.${config.airmap.domain}")
            .addPathSegments("$serviceName/v$v/")
            .toString()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(AirMapResponseCallAdapterFactory())
            .client(client)
            .build()
            .create()
    }
}

// The clients represent the raw REST API. Any extra methods defined in AirMapClient are convenience
class AirMapClient(
    private val aircraftClient: AircraftClient,
    private val pilotClient: PilotClient,
    private val flightClient: FlightClient,
    private val airspaceClient: AirspaceClient,
    private val rulesClient: RulesClient,
    private val advisoryClient: AdvisoryClient
) : AircraftClient by aircraftClient,
    PilotClient by pilotClient,
    FlightClient by flightClient,
    AirspaceClient by airspaceClient,
    RulesClient by rulesClient,
    AdvisoryClient by advisoryClient {
    // TODO: look into implications of moving this into the respective clients themselves
    //  Answer: Need to use @JvmDefault annotation and compile with Java 8
    //  This is specific Java 8 supported by all Android versions
    //  (https://developer.android.com/studio/write/java8-support)
    //  We should look into doing this (it will required some build parameter modifications)
    //  The benefit of this is that the helpers are closer to their actual definition

    // TODO: Some form of automatic translation when the real method is annotated with some
    //  specially defined annoation (e.g. @CommaSeparated). Track the following issue:
    //  https://github.com/square/retrofit/issues/626

    // AirspaceClient
    fun getAirspaces(ids: List<String>) = getAirspaces(ids.joinToString(","))

    // PilotClient
    fun verifySMS(token: Int) = verifySMS(VerificationRequest(token))
    fun updatePilot(
        firstName: String? = null,
        lastName: String? = null,
        username: String? = null,
        email: String? = null,
        phone: String? = null,
        appMetadata: Map<String, Any>? = null,
        userMetadata: Map<String, Any>? = null
    ) = updatePilot(
        UpdatePilotRequest(
            firstName, lastName, username, email, phone, appMetadata, userMetadata
        )
    )

    // FlightClient
    fun getAuthorizations(flightPlanIds: List<String>) =
        getAuthorizations(flightPlanIds.joinToString(","))
    // TODO: fun getPublicFlights() (make use of getFlights with custom parameters)

    // RulesClient
    fun getRulesets(rulesetIds: List<String>) = getRulesets(rulesetIds.joinToString(","))
    fun getEvaluation(geometry: Geometry, rulesetIds: List<String>) =
        getEvaluation(EvaluationRequest(geometry, rulesetIds.joinToString(",")))
}

class GeometryJsonAdapterFactory : JsonAdapter.Factory {
    override fun create(
        type: Type,
        annotations: MutableSet<out Annotation>,
        moshi: Moshi
    ): JsonAdapter<*>? =
        when (type) {
            Geometry::class.java -> GeometryJsonAdapter(
                moshi.adapter(Point::class.java),
                moshi.adapter(LineString::class.java),
                moshi.adapter(Polygon::class.java),
                moshi.adapter(MultiPoint::class.java),
                moshi.adapter(MultiLineString::class.java),
                moshi.adapter(MultiPolygon::class.java),
                moshi.adapter(GeometryCollection::class.java),
                moshi.adapter(Feature::class.java),
                moshi.adapter(FeatureCollection::class.java)
            )
            else -> null
        }
}

class GeometryJsonAdapter(
    private val pointJsonAdapter: JsonAdapter<Point>,
    private val lineStringJsonAdapter: JsonAdapter<LineString>,
    private val polygonJsonAdapter: JsonAdapter<Polygon>,
    private val multiPointJsonAdapter: JsonAdapter<MultiPoint>,
    private val multiLineStringJsonAdapter: JsonAdapter<MultiLineString>,
    private val multiPolygonJsonAdapter: JsonAdapter<MultiPolygon>,
    private val geometryCollectionJsonAdapter: JsonAdapter<GeometryCollection>,
    private val featureJsonAdapter: JsonAdapter<Feature>,
    private val featureCollectionJsonAdapter: JsonAdapter<FeatureCollection>
) : JsonAdapter<Geometry>() {
    companion object {
        private const val KEY_TYPE = "type"
    }

    @FromJson
    override fun fromJson(reader: JsonReader): Geometry? {
        val jsonValue = reader.readJsonValue()
        if (jsonValue is Map<*, *> && jsonValue.containsKey(KEY_TYPE)) {
            return when (GeometryType.convertFromString(jsonValue[KEY_TYPE].toString())) {
                GeometryType.POINT -> pointJsonAdapter.fromJsonValue(jsonValue)
                GeometryType.LINESTRING -> lineStringJsonAdapter.fromJsonValue(jsonValue)
                GeometryType.POLYGON -> polygonJsonAdapter.fromJsonValue(jsonValue)
                GeometryType.MULIT_POINT -> multiPointJsonAdapter.fromJsonValue(jsonValue)
                GeometryType.MULTI_LINE_STRING -> multiLineStringJsonAdapter.fromJsonValue(jsonValue)
                GeometryType.MULTI_POLYGON -> multiPolygonJsonAdapter.fromJsonValue(jsonValue)
                GeometryType.GEOMETRY_COLLECTION -> geometryCollectionJsonAdapter.fromJsonValue(
                    jsonValue
                )
                GeometryType.FEATURE -> featureJsonAdapter.fromJsonValue(jsonValue)
                GeometryType.FEATURE_COLLECTION -> featureCollectionJsonAdapter.fromJsonValue(
                    jsonValue
                )
            }
        }

        throw JsonDataException("Something went wrong at ${reader.path}")
    }

    @ToJson
    override fun toJson(writer: JsonWriter, value: Geometry?) {
        when (value) {
            is Point -> pointJsonAdapter.toJson(writer, value)
            is MultiPoint -> multiPointJsonAdapter.toJson(writer, value)
            is LineString -> lineStringJsonAdapter.toJson(writer, value)
            is MultiLineString -> multiLineStringJsonAdapter.toJson(writer, value)
            is Polygon -> polygonJsonAdapter.toJson(writer, value)
            is MultiPolygon -> multiPolygonJsonAdapter.toJson(writer, value)
            is Feature -> featureJsonAdapter.toJson(writer, value)
            is FeatureCollection -> featureCollectionJsonAdapter.toJson(writer, value)
            else -> writer.nullValue()
        }
    }
}

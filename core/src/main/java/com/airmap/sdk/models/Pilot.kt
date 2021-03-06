package com.airmap.sdk.models

import com.serjltt.moshi.adapters.DeserializeOnly
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Date

@JsonClass(generateAdapter = true)
data class VerificationStatus(
    @Json(name = "phone") val phone: Boolean,
    @Json(name = "email") val email: Boolean,
)

@JsonClass(generateAdapter = true)
data class FlightStatistics(
    @Json(name = "total") val total: Int,
    @Json(name = "last_flight_time") val lastFlightTime: Date?,
)

@JsonClass(generateAdapter = true)
data class AircraftStatistics(
    @Json(name = "total") val total: Int,
)

@JsonClass(generateAdapter = true)
data class Statistics(
    @Json(name = "flight") val flight: FlightStatistics,
    @Json(name = "aircraft") val aircraft: AircraftStatistics,
)

@JsonClass(generateAdapter = true)
data class Pilot(
    @Json(name = "id") @DeserializeOnly val id: PilotId?,
    @Json(name = "email") @DeserializeOnly val email: String?,
    @Json(name = "first_name") val firstName: String?,
    @Json(name = "last_name") val lastName: String?,
    @Json(name = "username") val username: String?,
    @Json(name = "picture_url") @DeserializeOnly val pictureUrl: String?,
    @Json(name = "phone") val phone: String?,
    @Json(name = "created_at") @DeserializeOnly val createdAt: Date?,
    @Json(name = "anonymized_id") @DeserializeOnly val anonymizedId: PilotId?,
    @Json(name = "verification_status") @DeserializeOnly
    val verificationStatus: VerificationStatus?,
    @Json(name = "statistics") @DeserializeOnly val statistics: Statistics?,
    @Json(name = "user_metadata") val userMetadata: Map<String, Any?>?,
    @Json(name = "app_metadata") val appMetadata: Map<String, Any?>?,
)

@JsonClass(generateAdapter = true)
data class VerificationResult(
    @Json(name = "verified") val verified: Boolean,
)

/// Network Request Models

@JsonClass(generateAdapter = true)
data class VerificationRequest(
    @Json(name = "token") val token: Int,
)

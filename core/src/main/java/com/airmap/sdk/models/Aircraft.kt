package com.airmap.sdk.models

import com.serjltt.moshi.adapters.DeserializeOnly
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Date

@JsonClass(generateAdapter = true)
data class Manufacturer(
    @Json(name = "id") val id: ManufacturerId,
    @Json(name = "name") val name: String,
    @Json(name = "url") val url: String?,
)

@JsonClass(generateAdapter = true)
data class Model(
    @Json(name = "id") val id: ModelId,
    @Json(name = "name") val name: String,
    @Json(name = "manufacturer") val manufacturer: Manufacturer,
    @Json(name = "metadata") val metadata: Map<String, Any?>?,
)

@JsonClass(generateAdapter = true)
data class Aircraft(
    @Json(name = "id") val id: AircraftId?,
    @Json(name = "nickname") val nickname: String?,
    @Json(name = "model") val model: Model?,
    @Json(name = "serial_number") val serialNumber: String?,
    @Json(name = "created_at") @DeserializeOnly val createdAt: Date?,
)

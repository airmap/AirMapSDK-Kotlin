package com.airmap.sdk.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import dev.zacsweers.moshisealed.annotations.DefaultObject
import dev.zacsweers.moshisealed.annotations.TypeLabel
import java.util.Date

@JsonClass(generateAdapter = true, generator = "sealed:type")
sealed class AirspaceProperties {
    @DefaultObject object Unknown
}

@TypeLabel("airport")
@JsonClass(generateAdapter = true)
data class AirportProperties(
    @Json(name = "url") val url: String?,
    @Json(name = "description") val description: String?,
    @Json(name = "icao") val icao: String?,
    @Json(name = "phone") val phone: String?,
    @Json(name = "ownership") val ownership: String?,
    @Json(name = "ifr") val ifr: Boolean?,
    @Json(name = "iata") val iata: String?,
    @Json(name = "paved") val paved: Boolean?,
    @Json(name = "tower") val tower: Boolean?,
    @Json(name = "runways") val runways: List<Runway>?,
    @Json(name = "elevation") val elevation: Feet?,
    @Json(name = "icao_country") val icaoCountry: String?,
    @Json(name = "longest_runway") val longestRunway: Meters?,
) : AirspaceProperties() {
    @JsonClass(generateAdapter = true)
    data class Runway(
        @Json(name = "name") val name: String,
        @Json(name = "length") val length: Meters,
        @Json(name = "bearing") val bearing: Degrees,
    )
}

@TypeLabel("ama_field")
@JsonClass(generateAdapter = true)
data class AMAFieldProperties(
    @Json(name = "authorization") val authorization: Boolean,
    @Json(name = "laanc") val laanc: Boolean,
    @Json(name = "url") val url: String?,
    @Json(name = "contact_name") val contactName: String?,
    @Json(name = "contact_phone") val contactPhone: String?,
    @Json(name = "contact_email") val contactEmail: String?,
) : AirspaceProperties()

@TypeLabel("controlled_airspace")
@JsonClass(generateAdapter = true)
data class ControlledAirspaceProperties(
    @Json(name = "url") val url: String?,
    @Json(name = "icao") val icao: String?,
    @Json(name = "laanc") val laanc: Boolean?,
    @Json(name = "ceiling") val ceiling: Feet?,
    @Json(name = "ceiling_ref") val ceilingRef: String?,
    @Json(name = "ceiling_unit") val ceilingUnit: String?,
    @Json(name = "floor") val floor: Feet?,
    @Json(name = "floor_ref") val floorRef: String?,
    @Json(name = "floor_unit") val floorUnit: String?,
    @Json(name = "lowest_limit") val lowestLimit: Feet?,
    @Json(name = "airport_id") val airportId: String?,
    @Json(name = "airport_name") val airportName: String?,
    @Json(name = "authorization") val authorization: Boolean?,
    @Json(name = "last_edit_date") val lastEditDate: String?,
    @Json(name = "description") val description: String?,
    @Json(name = "airspace_classification") val airspaceClassification: String?,
    @Json(name = "facility_id") val facilityId: String?,
) : AirspaceProperties()

@TypeLabel("emergency")
@JsonClass(generateAdapter = true)
data class EmergencyProperties(
    @Json(name = "url") val url: String?,
    @Json(name = "description") val description: String?,
    @Json(name = "agency_id") val agencyId: String?,
    @Json(name = "date_effective") val dateEffective: Date?,
) : AirspaceProperties()

@TypeLabel("fire")
@JsonClass(generateAdapter = true)
data class FireProperties(
    @Json(name = "url") val url: String?,
    @Json(name = "description") val description: String?,
    @Json(name = "size") val size: Hectares?,
    @Json(name = "date_effective") val dateEffective: Date?,
) : AirspaceProperties()

@TypeLabel("heliport")
@JsonClass(generateAdapter = true)
data class HeliportProperties(
    @Json(name = "url") val url: String?,
    @Json(name = "description") val description: String?,
    @Json(name = "phone") val phone: String?,
    @Json(name = "use") val use: String?,
) : AirspaceProperties()

@TypeLabel("notam")
@JsonClass(generateAdapter = true)
data class NOTAMProperties(
    @Json(name = "url") val url: String?,
    @Json(name = "description") val description: String?,
    @Json(name = "effective_start") val effectiveStart: Date?,
    @Json(name = "effective_end") val effectiveEnd: Date?,
) : AirspaceProperties()

@TypeLabel("notification")
@JsonClass(generateAdapter = true)
data class NotificationProperties(
    @Json(name = "url") val url: String?,
    @Json(name = "description") val description: String?,
) : AirspaceProperties()

@TypeLabel("park")
@JsonClass(generateAdapter = true)
data class ParkProperties(
    @Json(name = "url") val url: String?,
    @Json(name = "description") val description: String?,
    @Json(name = "size") val size: Double?,
    @Json(name = "type") val type: String?,
) : AirspaceProperties()

@TypeLabel("power_plant")
@JsonClass(generateAdapter = true)
data class PowerPlantProperties(
    @Json(name = "url") val url: String?,
    @Json(name = "description") val description: String?,
    @Json(name = "tech") val tech: String?,
    @Json(name = "generator_type") val generatorType: String?,
    @Json(name = "plant_code") val plantCode: Int?,
) : AirspaceProperties()

@TypeLabel("school")
@JsonClass(generateAdapter = true)
data class SchoolProperties(
    @Json(name = "url") val url: String?,
    @Json(name = "description") val description: String?,
    @Json(name = "type") val type: String?,
    @Json(name = "building") val building: Boolean?,
    @Json(name = "way_area") val wayArea: Double?,
    @Json(name = "students") val numStudents: Int?,
) : AirspaceProperties()

@TypeLabel("special_use_airspace")
@JsonClass(generateAdapter = true)
data class SpecialUseProperties(
    @Json(name = "url") val url: String?,
    @Json(name = "description") val description: String?,
    @Json(name = "currently_active") val currentlyActive: Boolean?,
    @Json(name = "ceiling") val ceiling: Feet?,
    @Json(name = "ceiling_ref") val ceilingRef: String?,
    @Json(name = "ceiling_unit") val ceilingUnit: String?,
    @Json(name = "floor") val floor: Feet?,
    @Json(name = "floor_ref") val floorRef: String?,
    @Json(name = "floor_unit") val floorUnit: String?,
    @Json(name = "lowest_limit") val lowestLimit: Feet?,
    @Json(name = "type") val type: String?,
) : AirspaceProperties()

@TypeLabel("tfr")
@JsonClass(generateAdapter = true)
data class TFRProperties(
    @Json(name = "url") val url: String?,
    @Json(name = "body") val body: String?,
    @Json(name = "description") val description: String?,
    @Json(name = "effective_start") val effectiveStart: Date?,
    @Json(name = "effective_end") val effectiveEnd: Date?,
    @Json(name = "sport") val sport: String?,
    @Json(name = "venue") val venue: String?,
    @Json(name = "type") val type: String?,
) : AirspaceProperties()

@TypeLabel("wildfire")
@JsonClass(generateAdapter = true)
data class WildFireProperties(
    @Json(name = "url") val url: String?,
    @Json(name = "description") val description: String?,
    @Json(name = "size") val size: Hectares?,
    @Json(name = "date_effective") val dateEffective: Date?,
) : AirspaceProperties()

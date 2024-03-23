package com.example.data.sampleDBdata

import com.example.model.details.DetailedResult
import com.example.model.network.CurrentOpeningHours
import com.example.model.network.Geometry
import com.example.model.network.Location
import com.example.model.network.Northeast
import com.example.model.network.OpeningHours
import com.example.model.network.PlusCode
import com.example.model.network.Southwest
import com.example.model.network.Viewport


val testSampleDetailPlace = DetailedResult(
    address_components = listOf(),
    adr_address = "xx시 xx구",
    business_status = "영업 중",
    current_opening_hours = CurrentOpeningHours(
        open_now = true,
        periods = listOf(),
        weekday_text = listOf()
    ),
    delivery = true,
    dine_in = false,
    formatted_address = "xx시 xx구",
    formatted_phone_number = "xxxx-xxxx-xxxx",
    geometry = Geometry(
        location = Location(lat = 0.0, lng = 0.0),
        viewport = Viewport(
            northeast = Northeast(lat = 0.0, lng = 0.0),
            southwest = Southwest(lat = 0.0, lng = 0.0)
        )
    ),
    icon = "",
    icon_background_color = "",
    icon_mask_base_uri = "",
    international_phone_number = "",
    name = "xxx_xxxx",
    opening_hours = OpeningHours(
        open_now = true,
        periods = listOf(),
        weekday_text = listOf()
    ),
    photos = listOf(),
    place_id = "",
    plus_code = PlusCode("", ""),
    rating = 5.0,
    reference = "",
    reservable = true,
    reviews = listOf(),
    serves_beer = true,
    serves_dinner = true,
    serves_lunch = true,
    serves_wine = true,
    types = listOf(),
    url = "",
    user_ratings_total = 100,
    utc_offset = 0,
    vicinity = "",
    website = ""
)
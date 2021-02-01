package exporting

import com.github.filosganga.geogson.gson.GeometryAdapterFactory
import com.github.filosganga.geogson.model.Feature
import com.github.filosganga.geogson.model.FeatureCollection
import com.github.filosganga.geogson.model.Point
import com.google.gson.GsonBuilder
import pipeline.Coordinate
import pipeline.Export

class JsonExport : Export {

    val gson =  GsonBuilder()
        .registerTypeAdapterFactory(GeometryAdapterFactory())
        .create()

    fun toGeoJson(coordinates: List<Coordinate>): String {
        val points = coordinates.map { Point.from(it.longitude,it.latitude) }
            .map { Feature.of(it) }
        val collection = FeatureCollection(points)
        return gson.toJson(collection)
    }

    override fun export(coordinates: List<Coordinate>): String {
        return toGeoJson(coordinates)
    }
}





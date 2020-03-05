package QuizApp.com.Model

import org.json.JSONArray
import org.json.JSONObject

class ParsPlantUtility{
    fun parsePlantObjectFromJSONData(search: String?) : List<Plant>? {
        var allPlantObjects: ArrayList<Plant> = ArrayList()
        var downloadingObject = DownloadingObject()
        var topLevelPlantJSONData = downloadingObject.downloadJSONDataFrom("http://plantplaces.com/perl/mobile/flashcard.pl")
        var topLevelJSONObject: JSONObject = JSONObject(topLevelPlantJSONData)
        var plantObjectsArray: JSONArray = topLevelJSONObject.getJSONArray("values")

        var index: Int = 0
        while (index < plantObjectsArray.length()) {
            var plantObject: Plant = Plant()
            var jsonObject = plantObjectsArray.getJSONObject(index)

            with(jsonObject){
                plantObject.genus = getString("")
                plantObject.species = getString("")
                plantObject.cultivar = getString("")
                plantObject.common = getString("common")
                plantObject.pictureName = getString("picture_name")
                plantObject.description = getString("description")
                plantObject.difficulty = getInt("difficulty")
                plantObject.id = getInt("id")
            }
            allPlantObjects.add(plantObject)
            4

            index++
        }
        return allPlantObjects
    }
}
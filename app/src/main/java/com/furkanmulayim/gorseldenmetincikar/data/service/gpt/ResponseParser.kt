package com.furkanmulayim.gorseldenmetincikar.data.service.gpt

import org.json.JSONObject

class ResponseParser {
    fun parse(responseBody: String): String? {
        val jsoObject = JSONObject(responseBody)
        val choices = jsoObject.getJSONArray("choices")
        val firstChoice = choices.getJSONObject(0)
        return firstChoice.getString("text")
    }
}
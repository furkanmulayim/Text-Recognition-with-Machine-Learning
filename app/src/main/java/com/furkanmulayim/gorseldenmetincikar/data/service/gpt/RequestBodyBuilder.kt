package com.furkanmulayim.gorseldenmetincikar.data.service.gpt

class RequestBodyBuilder {
    fun build(gptQuery: String): String {
        return """
            {
            "prompt": "\"$gptQuery\". Tırnak içinde verilen bu metni değiştirmeden sadece imla kurallarına göre yeniden yazar mısın?",
            "max_tokens": 200,
            "temperature": 0.2,
            "n": 1}
            """.trimIndent()
    }
}
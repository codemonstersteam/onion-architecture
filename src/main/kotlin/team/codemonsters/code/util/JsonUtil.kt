package team.codemonsters.code.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.core.env.Environment
import org.springframework.core.io.Resource
import org.springframework.stereotype.Component

@Component
class JsonUtil(private val mapper: ObjectMapper, private val env: Environment) {

    companion object {

        private val mapper: ObjectMapper = ObjectMapper()
            .registerModule(KotlinModule())
            .registerModule(JavaTimeModule())

        fun toJson(o: Any): String = mapper.writeValueAsString(o)

        fun <T> fromString(jsonString: String, clazz: Class<T>)
                : T = mapper.readValue(jsonString, clazz)

        fun <T> resourceTo(
            resource: Resource, clazz: Class<T>
        ): T = mapper.readValue(resource.inputStream, clazz)
    }

}
package team.codemonsters.code

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import java.io.File

class TestUtil {

    companion object {
        private val MAPPER = ObjectMapper()
            .registerModule(KotlinModule())
            .registerModule(JavaTimeModule())

        fun <T> readResourceFilePathTo(filePath: String, clazz: Class<T>): T {
            return MAPPER.readValue(File(this::class.java.getResource(filePath)!!.toURI()), clazz)
        }

    }
}
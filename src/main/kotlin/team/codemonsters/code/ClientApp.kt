package team.codemonsters.code

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.jms.annotation.EnableJms

@EnableJms
@SpringBootApplication
class ClientApp {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<ClientApp>(*args)
        }
    }

}
package team.codemonsters.code.walletRegistration.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/**
 * Изолированны
 *
 */
class ClientIdTest {

    @Test
    fun validClientId() {
        val clientId = ClientId.emerge("96104464-54ea-44e6-876b-8d45428776e3")
        assertThat(clientId.isSuccess).isTrue
    }

    @Test
    fun invalidClientId() {
        val clientId = ClientId.emerge("anana")
        assertThat(clientId.isFailure).isTrue
    }

}
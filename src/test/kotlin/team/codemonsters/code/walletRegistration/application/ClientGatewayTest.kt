package team.codemonsters.code.walletRegistration.application

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import team.codemonsters.code.walletRegistration.domain.ClientId
import team.codemonsters.code.walletRegistration.infrastructure.ClientRepository

class ClientGatewayTest {
    @Test
    fun findClient() {
        // Arrange
        val clientGateway = ClientGateway(ClientRepository())

        // Act
        val result = clientGateway.findClient(ClientId.emerge("96104464-54ea-44e6-876b-8d45428776e3").getOrThrow())
        // Assert
        assertThat(result.isSuccess).isTrue
    }
}
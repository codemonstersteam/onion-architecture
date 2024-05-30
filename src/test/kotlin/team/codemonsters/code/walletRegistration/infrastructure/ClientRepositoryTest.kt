package team.codemonsters.code.walletRegistration.infrastructure

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ClientRepositoryTest {

    @Test
    fun `should find client`() {
        val repository = ClientRepository()
        val result = repository.find("96104464-54ea-44e6-876b-8d45428776e3")
        assertThatClientValid(result)
    }

    private fun assertThatClientValid(result: Result<ClientEntity>) {
        assertThat(result.isSuccess).isTrue
        assertThat(result.getOrThrow().clientName).isEqualTo("Рома")
        assertThat(result.getOrThrow().walletId).isNull()
    }
}
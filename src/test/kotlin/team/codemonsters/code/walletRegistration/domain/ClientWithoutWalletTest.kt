package team.codemonsters.code.walletRegistration.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ClientWithoutWalletTest {
    @Test
    fun `should be valid client for wallet registration request`() {
        //arrange
        val client = ClientWithoutWallet.emerge(
            clientId = "96104464-54ea-44e6-876b-8d45428776e3",
            name = "Рома",
            walletId = null
        )
        //assert
        assertThat(client.isSuccess).isTrue
    }

    @Test
    fun `should be invalid client for wallet registration request`() {
        //arrange
        val client = ClientWithoutWallet.emerge(
            clientId = "96104464-54ea-44e6-876b-8d45428776e3",
            name = "Рома",
            walletId = "bc1qar0srrr7xfkvy5l643lydnw9re59gtzzwf5mdq"
        )
        //act
        assertInvalid(client)
    }

    private fun assertInvalid(client: Result<ClientWithoutWallet>) {
        assertThat(client.isFailure).isTrue
        assertThat(client.exceptionOrNull()!!.message).isEqualTo("У клиента уже есть кошелек")
    }
}
package team.codemonsters.code.walletRegistration.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ClientWithWalletTest {

    @Test
    fun `should create client with wallet`() {
        val clientWithoutWallet = ClientWithoutWallet.emerge(
            clientId = "96104464-54ea-44e6-876b-8d45428776e3",
            name = "Рома",
            walletId = null
        )
        val clientWithWallet = ClientWithWallet.emerge(
           client = clientWithoutWallet.getOrThrow(),
            walletId = WalletId("bc1qar0srrr7xfkvy5l643lydnw9re59gtzzwf5mdq")
        )
        assertThat(clientWithWallet.isSuccess).isTrue
    }
}
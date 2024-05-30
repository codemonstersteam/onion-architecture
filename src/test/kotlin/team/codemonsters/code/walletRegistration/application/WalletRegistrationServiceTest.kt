package team.codemonsters.code.walletRegistration.application

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import team.codemonsters.code.walletRegistration.domain.ClientWithWallet
import team.codemonsters.code.walletRegistration.domain.ClientWithoutWallet
import team.codemonsters.code.walletRegistration.infrastructure.ClientRepository

class WalletRegistrationServiceTest {

    @Test
    fun `should register wallet`() {
        //arrange
        val service = WalletRegistrationService(ClientGateway(ClientRepository()), WalletGateway())
        val unvalidatedRequest = UnvalidatedRequest("96104464-54ea-44e6-876b-8d45428776e3")
        //act
        val result = service.registerWallet(unvalidatedRequest)
        //assert
        assertThatWalletRegistered(result)
    }

    private fun assertThatWalletRegistered(result: Result<ClientWithWallet>) {
        assertThat(result.isSuccess).isTrue
        assertThat(result.getOrThrow().walletId.value).isEqualTo("3J98t1WpEZ73CNmQviecrnyiWrnqRhWNLy")
    }

}
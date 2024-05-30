package team.codemonsters.code.walletRegistration.application

import org.springframework.stereotype.Service
import team.codemonsters.code.walletRegistration.domain.ClientId
import team.codemonsters.code.walletRegistration.domain.WalletId

@Service
class WalletGateway {

    fun registerWallet(clientId: ClientId): Result<WalletId> =
        WalletId.emerge("3J98t1WpEZ73CNmQviecrnyiWrnqRhWNLy")
}
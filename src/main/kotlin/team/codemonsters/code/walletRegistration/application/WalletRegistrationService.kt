package team.codemonsters.code.walletRegistration.application

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import team.codemonsters.code.walletRegistration.domain.ClientWithoutWallet
import team.codemonsters.code.walletRegistration.domain.ClientId
import team.codemonsters.code.walletRegistration.domain.ClientWithWallet

@Service
class WalletRegistrationService(
    private val clientGateway: ClientGateway,
    private val walletGateway: WalletGateway
) {
    private val log = LoggerFactory.getLogger(WalletRegistrationService::class.java)

    fun registerWallet(unvalidatedRequest: UnvalidatedRequest): Result<ClientWithWallet> {
        val clientIdResult = ClientId.emerge(unvalidatedRequest.clientId)
        if (clientIdResult.isFailure) {
            return Result.failure(clientIdResult.exceptionOrNull()!!)
        }
        val clientId = clientIdResult.getOrThrow()

        val clientResult = clientGateway.findClient(clientId)
        if (clientResult.isFailure)
            return Result.failure(clientResult.exceptionOrNull()!!)

        val walletId = walletGateway.registerWallet(clientId)
        if (walletId.isFailure)
            return Result.failure(walletId.exceptionOrNull()!!)

        val clientWithWallet = ClientWithWallet.emerge(
            clientResult.getOrThrow(),
            walletId.getOrThrow()
        )
        if (clientWithWallet.isFailure)
            return Result.failure(clientWithWallet.exceptionOrNull()!!)

        return clientGateway.registerWallet(clientWithWallet.getOrThrow())
    }

}
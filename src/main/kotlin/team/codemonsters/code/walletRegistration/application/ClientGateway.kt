package team.codemonsters.code.walletRegistration.application

import org.springframework.stereotype.Service
import team.codemonsters.code.walletRegistration.domain.ClientId
import team.codemonsters.code.walletRegistration.domain.ClientWithoutWallet
import team.codemonsters.code.walletRegistration.domain.ClientWithWallet
import team.codemonsters.code.walletRegistration.infrastructure.ClientEntity
import team.codemonsters.code.walletRegistration.infrastructure.ClientRepository

/**
 * Транслирует данные из уровня хранения данных в уровень приложения.
 * Обеспечивает интеграцию с репозиторием и абстрагирует доменную модель от уровня хранения.
 */
@Service
class ClientGateway(val clientRepository: ClientRepository) {

    fun findClient(clientId: ClientId): Result<ClientWithoutWallet> {
        val client = clientRepository.find(clientId.value.toString())
        if (client.isFailure) {
            return Result.failure(client.exceptionOrNull()!!)
        }
        println(client.getOrThrow().clientName)
        return map(client.getOrThrow())
    }

    private fun map(clientEntity: ClientEntity): Result<ClientWithoutWallet> {
        return ClientWithoutWallet.emerge(
            clientEntity.id,
            clientEntity.clientName,
            clientEntity.walletId
        )
    }

    fun registerWallet(walletRegistrationRequest: ClientWithWallet): Result<ClientWithWallet> {
        val updatedClient = clientRepository.update(ClientEntity.from(walletRegistrationRequest))
        if (updatedClient.isFailure) {
            return Result.failure(updatedClient.exceptionOrNull()!!)
        }
        return ClientWithWallet.emerge(
            updatedClient.getOrThrow().id.toString(),
            updatedClient.getOrThrow().clientName,
            updatedClient.getOrThrow().walletId
        )
    }

}
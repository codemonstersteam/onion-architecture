package team.codemonsters.code.walletRegistration.domain


data class ClientWithWallet private constructor(
    val clientId: ClientId,
    val name: Name,
    val walletId: WalletId,
) {
    companion object {
        fun emerge(client: ClientWithoutWallet, walletId: WalletId): Result<ClientWithWallet> {
            if (WalletId.Empty == client.walletId) {
                return Result.success(ClientWithWallet(client.clientId, client.name, walletId))
            }
            return Result.failure(RuntimeException("Клиент уже имеет кошелек"))
        }

        fun emerge(clientId: String, name: String, walletId: String?): Result<ClientWithWallet> {
            if (null == walletId) {
                return Result.failure(RuntimeException("У клиента должен быть кошелек"))
            }
            val validClientId = ClientId.emerge(clientId)
            if (validClientId.isFailure)
                return Result.failure(validClientId.exceptionOrNull()!!)
            val validName = Name.emerge(name)
            if (validName.isFailure)
                return Result.failure(validName.exceptionOrNull()!!)
            val validWalletId = WalletId.emerge(walletId)
            if (validWalletId.isFailure)
                return Result.failure(validWalletId.exceptionOrNull()!!)
            return Result.success(
                ClientWithWallet(
                    validClientId.getOrThrow(),
                    validName.getOrThrow(),
                    validWalletId.getOrThrow()
                )
            )
        }

    }
}
package team.codemonsters.code.walletRegistration.domain

data class ClientWithoutWallet(
    val clientId: ClientId,
    val name: Name,
    val walletId: WalletId
) {

    companion object {
        fun emerge(clientId: String, name: String, walletId: String?): Result<ClientWithoutWallet> {
            val validClientId = ClientId.emerge(clientId)
            if (validClientId.isFailure)
                return Result.failure(validClientId.exceptionOrNull()!!)

            val validName = Name.emerge(name)
            if (validName.isFailure)
                return Result.failure(validName.exceptionOrNull()!!)


            if (null == walletId) {
                return Result.success(ClientWithoutWallet(validClientId.getOrThrow(), validName.getOrThrow(), WalletId.Empty))
            }
            return Result.failure(RuntimeException("У клиента уже есть кошелек"))
        }
    }
}
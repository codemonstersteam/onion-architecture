package team.codemonsters.code.walletRegistration.infrastructure

import team.codemonsters.code.walletRegistration.domain.ClientWithWallet

data class ClientEntity(
    //uuid
    val id: String,
    //9 cyrillic characters
    val clientName: String,
    //26-62 alphanumeric character
    val walletId: String?
) {
    companion object {
        fun from(walletRegistrationRequest: ClientWithWallet) = ClientEntity(
            id = walletRegistrationRequest.clientId.value.toString(),
            clientName = walletRegistrationRequest.name.value,
            walletId = walletRegistrationRequest.walletId.value
        )
    }


}
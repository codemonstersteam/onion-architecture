package team.codemonsters.code.walletRegistration.domain

data class WalletId(val value: String) {

    companion object {
        private val ADDRESS_PATTERN = Regex("^[a-zA-Z0-9]{26,62}$")

        val Empty = WalletId("")

        fun emerge(walletId: String): Result<WalletId> {
            if (ADDRESS_PATTERN.matches(walletId)) {
                return Result.success(WalletId(walletId))
            }
            return Result.failure(RuntimeException("Некорректный адрес кошелька"))
        }
    }

}


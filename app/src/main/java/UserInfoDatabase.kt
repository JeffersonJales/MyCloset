data class UserInfoDatabase(
    val database_table : String = "usuario_info"
)

data class UserInfoItemDatabase(
    val uid : String = "uid",
    val foto : String = "foto",
    val endereco_numero : String = "endereco_numero",
    val endereco_cep : String = "endereco_cep"
)
package lab5.model

data class Organization(
    val id: Long,
    val name: String,
    val fullName: String,
    val employeesCount: Long,
) {
    init {
        require(id > 0) { "id должен быть уникальным" }
        require(name.isNotBlank()) { "name не может быть пустым" }
        require(fullName.length() <= 532 && fullName.isNotBlank()) { "fullName не должна быть больше 532 и строка не может быть пустой" }
        require(employeesCount > 0) { "employeesCount должно быть больше 0" }
    }
}

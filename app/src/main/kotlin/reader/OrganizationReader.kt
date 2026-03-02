package reader

import io.IOHandler
import model.Organization

class OrganizationReader(
    private val io: IOHandler,
) {
    fun read(): Organization {
        val name = io.readString("введите название")
        val fullName = io.readString("введите полное название")
        val employees = io.readLong("введите количество участников")

        return Organization(1L, name, fullName, employees)
    }
}

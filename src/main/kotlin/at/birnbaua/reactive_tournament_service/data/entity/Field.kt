package at.birnbaua.reactive_tournament_service.data.entity

import at.birnbaua.reactive_tournament_service.data.entity.abstract.AbstractEntity

open class Field(
    override var no: Int = -1
) : Comparable<Field>, AbstractEntity() {
    var name: String = "Field $no"
    var desc: String = ""
    var sponsor: String = ""

    override fun compareTo(other: Field): Int {
        return this.no - other.no
    }
}
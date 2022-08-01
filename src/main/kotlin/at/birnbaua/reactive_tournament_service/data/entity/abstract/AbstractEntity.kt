package at.birnbaua.reactive_tournament_service.data.entity.abstract

abstract class AbstractEntity {
    open var no: Int = -1
    override fun equals(other: Any?): Boolean {
        return if(other is AbstractEntity) {
            other.no == this.no
        } else {
            false
        }
    }

    override fun hashCode(): Int {
        return this.no.hashCode()
    }
}
package at.birnbaua.reactive_tournament_service.data.entity.gameround.group

data class Group(
    var no: Int = 0
) : Comparable<Group> {
    var teams = sortedSetOf<Int>()

    override fun compareTo(other: Group): Int {
        return this.no - other.no
    }
}

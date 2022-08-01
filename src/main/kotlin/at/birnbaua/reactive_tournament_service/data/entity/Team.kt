package at.birnbaua.reactive_tournament_service.data.entity

data class Team(
    var no: Int = -1
) : Comparable<Team> {
    var name: String = "Team"
    var desc: String = ""
    var isReferee: Boolean = false

    companion object {
        val DEFAULT = Team()
    }

    override fun compareTo(other: Team): Int {
        return this.no!! - other.no!!
    }
}

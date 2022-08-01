package at.birnbaua.reactive_tournament_service.data.entity.config

data class TournamentType internal constructor(val category: String = "", val name: String = "") {

    var desc: String = ""

    companion object {
        val SINGLE_ELIMINATION: TournamentType = TournamentType("SINGLE_ELIMINATION","SINGLE_ELIMINATION")
        val DOUBLE_ELIMINATION: TournamentType = TournamentType("DOUBLE_ELIMINATION","DOUBLE_ELIMINATION")
        val FEIZI: TournamentType = TournamentType("FEIZI","FEIZI")
        fun CUSTOM(name: String) = TournamentType("CUSTOM",name)
    }

    override fun toString(): String {
        return "$category - $name"
    }
}

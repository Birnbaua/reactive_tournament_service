package at.birnbaua.reactive_tournament_service.data.dto

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("tournament")
open class TournamentOverviewDTO {
    @Id @Column("id") open var id: String? = null
    @Column("name") open var name: String? = null
    @Column("title")open var title: String? = null
    @Column("start") open var start: LocalDateTime? = null
    @Column("`end`") open var end: LocalDateTime? = null
    @Column("created_at") open var createdAt: LocalDateTime? = null
    @Column("updated_at") open var updatedAt: LocalDateTime? = null
}
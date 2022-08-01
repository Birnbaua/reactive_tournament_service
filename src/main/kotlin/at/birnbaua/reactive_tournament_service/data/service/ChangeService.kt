package at.birnbaua.reactive_tournament_service.data.service

import at.birnbaua.reactive_tournament_service.controller.NotificationController
import at.birnbaua.reactive_tournament_service.data.entity.Field
import at.birnbaua.reactive_tournament_service.data.entity.Team
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class ChangeService {

    @Autowired
    private lateinit var notificationController: NotificationController

    @Autowired
    private lateinit var mapper : ObjectMapper

}
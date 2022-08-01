package at.birnbaua.reactive_tournament_service

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "storage")
class StorageProperties {
    var url = "localhost"
    var port = "8081"
    var path = "/file/single/json"
}
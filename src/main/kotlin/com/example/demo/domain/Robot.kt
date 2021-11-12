package com.example.demo.domain;

import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "ROBOTS", schema = "DEMO_KOTLIN")
class Robot(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var name: String,
    var description: String,
    var internalUid: String? = null,
    var created: Instant? = null,

    var updated: Instant? = null
) {
    companion object {
        fun new(name: String, description: String) = Robot(
            null,
            name,
            description,
            null,
            null,
            null
        )
    }
}

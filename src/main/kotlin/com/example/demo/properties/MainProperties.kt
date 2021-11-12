package com.example.demo.properties;

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties("app.api")
class MainProperties (
    var cors: String = "localhost",
    var maxPageSize: Int = 50,

    val maxNameChars: Int = 255
)

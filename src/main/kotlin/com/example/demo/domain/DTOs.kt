package com.example.demo.domain

class NewRobotRequest(
    var name: String,
    var description: String
)

class PatchRobotRequest(
    var name: String,
    var description: String
)

class RobotView(
    var id: Long? = null,
    var name: String,
    var description: String,
    var created: String,
    var updated: String
)
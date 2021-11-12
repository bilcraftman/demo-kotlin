package com.example.demo.domain;

import org.springframework.data.domain.Page
import org.springframework.stereotype.Component

@Component
class RobotsMapper {

    fun toDto(page: Page<Robot>): List<RobotView> {
        return page
            .map(this::toDto)
            .content
    }

    fun toDto(robot: Robot): RobotView {
        return RobotView(
            robot.id,
            robot.name,
            robot.description,
            robot.created.toString(),
            robot.updated.toString()
        )
    }

    fun toEntity(dto: NewRobotRequest): Robot {
        return Robot.new(dto.name, dto.description)
    }
}

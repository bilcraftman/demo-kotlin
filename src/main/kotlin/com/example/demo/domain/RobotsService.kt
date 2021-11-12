package com.example.demo.domain;

import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import tech.becoming.common.exceptions.NotFoundException
import java.time.Instant
import java.util.*

@Service
class RobotsService(
    val repository: RobotsRepository,
    val helper: RobotsHelper,
    val mapper: RobotsMapper
) {

    fun findInRange(pageRequest: PageRequest): List<RobotView> {
        helper.validatePage(pageRequest)
        val page = repository.findAll(pageRequest)

        return mapper.toDto(page)
    }

    fun findById(id: Long): RobotView {
        helper.validateId(id)
        val existing = repository.findById(id);
        NotFoundException.throwIfEmpty(existing)

        return mapper.toDto(existing.get());
    }

    fun create(dto: NewRobotRequest): RobotView {

        helper.validate(dto)
        var e = mapper.toEntity(dto)
        e = setupNew(e)
        e = repository.save(e)

        return mapper.toDto(e)
    }

    fun update(id: Long, dto: PatchRobotRequest): RobotView
    {
        helper.validate(dto)
        val found = repository.findById(id)
        NotFoundException.throwIfEmpty(found)
        applyPatch(found.get(), dto)
        val saved = repository.save(found.get())

        return mapper.toDto(saved)
    }

    fun setupNew(robot: Robot): Robot
    {
        with(robot) {
            created = Instant.now()
            updated = Instant.now()
            internalUid = UUID.randomUUID().toString()
        }

        return robot
    }

    fun applyPatch(robot: Robot, dto: PatchRobotRequest): Robot
    {
        with(robot) {
            name = dto.name
            description = dto.description
            updated = Instant.now()
        }

        return robot
    }
}

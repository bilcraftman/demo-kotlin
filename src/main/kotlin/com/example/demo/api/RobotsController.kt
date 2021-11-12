package com.example.demo.api;

import com.example.demo.domain.NewRobotRequest
import com.example.demo.domain.PatchRobotRequest
import com.example.demo.domain.RobotView
import com.example.demo.domain.RobotsService
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("robots")
class RobotsController(val service: RobotsService) {

    @GetMapping
    fun getRobots(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "50") size: Int
    ): List<RobotView> = service.findInRange(PageRequest.of(page, size))

    @GetMapping("{id}")
    fun getRobot(@PathVariable id: Long): RobotView = service.findById(id)

    @PostMapping
    fun create(@RequestBody dto: NewRobotRequest): RobotView = service.create(dto)

    @PutMapping("{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody dto: PatchRobotRequest
    ): RobotView = service.update(id, dto)

}

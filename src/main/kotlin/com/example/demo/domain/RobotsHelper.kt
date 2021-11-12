package com.example.demo.domain

import com.example.demo.properties.MainProperties
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component
import tech.becoming.common.exceptions.BadRequestException
import tech.becoming.common.exceptions.ExceptionDetail

@Component
class RobotsHelper (
        val properties: MainProperties
) {

    fun validatePage(pageRequest: PageRequest): PageRequest {
        val details = mutableListOf<ExceptionDetail>()

        if (pageRequest.pageNumber < 0) {
            val i = ExceptionDetail.ofNameAndMessage(
                    "page",
                    "Page value must be a positive number.")

            details.add(i)
        }

        if (pageRequest.pageSize < 0) {
            val i = ExceptionDetail.ofNameAndMessage(
                    "size",
                    "Size value must be a positive number.")

            details.add(i)
        }

        if (pageRequest.pageSize > properties.maxPageSize) {
            val i = ExceptionDetail.ofNameAndMessage(
                    "size",
                    "Size value must be lower than " + properties.maxPageSize + ".")

            details.add(i)
        }

        BadRequestException.throwIfHasDetails(details)

        return pageRequest
    }

    fun validateId(id: Long):Long {
        val details = mutableListOf<ExceptionDetail>()

        if (id < 0) {
            val i = ExceptionDetail.ofNameAndMessage(
                    "id",
                    "ID value must be a positive number.")

            details.add(i)
        }

        BadRequestException.throwIfHasDetails(details)

        return id
    }

    @SuppressWarnings("DuplicatedCode")
    fun validate(dto: NewRobotRequest): NewRobotRequest {
        val details = mutableListOf<ExceptionDetail>()

        if (isEmpty(dto.name)) {
            val i = ExceptionDetail.ofNameAndMessage(
                    "name",
                    "Name value must be a string with at least one character.")

            details.add(i)
        }

        if (dto.name.length > properties.maxNameChars) {
            val i = ExceptionDetail.ofNameAndMessage(
                    "name",
                    "Name length must be shorter than " + properties.maxNameChars + " characters.")

            details.add(i)
        }

        BadRequestException.throwIfHasDetails(details)

        return dto
    }

    // suppressed for the sake of demo
    @SuppressWarnings("DuplicatedCode")
    fun validate(dto: PatchRobotRequest): PatchRobotRequest {
        val details = mutableListOf<ExceptionDetail>()
        if (isEmpty(dto.name)) {
            val i = ExceptionDetail.ofNameAndMessage(
                    "name",
                    "Name value must be a string with at least one character.")

            details.add(i)
        }

        if (dto.name.length > properties.maxNameChars) {
            val i = ExceptionDetail.ofNameAndMessage(
                    "name",
                    "Name length must be shorter than " + properties.maxNameChars + " characters.")

            details.add(i)
        }

        BadRequestException.throwIfHasDetails(details)

        return dto
    }

    fun isEmpty(s: String?): Boolean {
        return null == s || s.replace(" ", "").isEmpty()
    }
}

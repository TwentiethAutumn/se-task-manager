package ru.quipy.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.quipy.api.project.*
import ru.quipy.core.EventSourcingService
import ru.quipy.logic.project.*
import java.util.*

@RestController
@RequestMapping("/projects")
class ProjectController(
    val projectEsService: EventSourcingService<UUID, ProjectAggregate, ProjectAggregateState>
) {

    @PostMapping("/{projectTitle}")
    fun createProject(@PathVariable projectTitle: String, @RequestParam creatorId: String) : ProjectCreatedEvent {
        return projectEsService.create { it.create(UUID.randomUUID(), projectTitle, creatorId) }
    }

    @PostMapping("/{projectId}/statuses")
    fun createStatus(
        @PathVariable projectId: UUID,
        @RequestParam title: String,
        @RequestParam color: String
    ) : StatusCreatedEvent {
        return projectEsService.update(projectId){
            it.createStatus(title, color) // TODO: проверить, что пользователь сам состоит в проекте
            //a more informative description of the edit is required
        }
    }

    // TODO: delete status
    @PostMapping("/{projectId}/deleteStatus")
    fun deleteStatus(
        @PathVariable projectId: UUID,
        @RequestParam statusId: UUID
    ) : StatusDeletedEvent {
        return projectEsService.update(projectId){
            it.deleteStatus(statusId)
        }
    }

    // TODO: edit status
    @PostMapping("/{projectId}/editStatus")
    fun editStatus(
        @PathVariable projectId: UUID,
        @RequestParam statusId: UUID,
        @RequestParam title: String,
        @RequestParam color: String,
    ) : StatusEditedEvent {
        return projectEsService.update(projectId) {
            it.editStatus(statusId, title, color)
        }
    }

    @PostMapping("/{projectId}/{userId}")
    fun assignUserToProject(@PathVariable projectId: UUID, @PathVariable userId: UUID) : UserAssignedToProjectEvent {
        return projectEsService.update(projectId) {
            it.assignUser(userId) // TODO: проверить, что пользователь сам состоит в проекте
            //a more informative description of the edit is required
        }
    }
}
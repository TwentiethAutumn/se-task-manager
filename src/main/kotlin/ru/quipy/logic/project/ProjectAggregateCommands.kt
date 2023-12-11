package ru.quipy.logic.project

import ru.quipy.api.project.*
import java.util.*

fun ProjectAggregateState.create(id: UUID, title: String, creatorId: String): ProjectCreatedEvent {
    return ProjectCreatedEvent(
        projectId = id,
        title = title,
        creatorId = creatorId,
    )
}

fun ProjectAggregateState.assignUser(userId: UUID) : UserAssignedToProjectEvent {
    return UserAssignedToProjectEvent(
            projectId = this.getId(),
            userId = userId,
    )
}

fun ProjectAggregateState.createStatus(name: String, color: String): StatusCreatedEvent {
    if (projectStatuses.values.any { it.name == name }) {
        throw IllegalArgumentException("Status already exists: $name")
    }
    return StatusCreatedEvent(projectId = this.getId(), statusId = UUID.randomUUID(), statusName = name, color = color)
}

fun ProjectAggregateState.editStatus(statusId: UUID, name: String, color: String): StatusEditedEvent {
    return StatusEditedEvent(
        projectId = this.getId(),
        statusId = statusId,
        title = name,
        color = color,
    )
}

fun ProjectAggregateState.deleteStatus(statusId: UUID): StatusDeletedEvent {
    return StatusDeletedEvent(
        projectId = this.getId(),
        statusId = statusId
    )
}
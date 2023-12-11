package ru.quipy.api.project

import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import java.util.*

const val PROJECT_CREATED_EVENT = "PROJECT_CREATED_EVENT"
const val STATUS_CREATED_EVENT = "STATUS_CREATED_EVENT"
const val USER_ASSIGNED_TO_PROJECT_EVENT = "USER_ASSIGNED_TO_PROJECT_EVENT"

const val STATUS_DELETED_EVENT = "STATUS_DELETED_EVENT"
const val STATUS_EDITED_EVENT = "STATUS_EDITED_EVENT"

@DomainEvent(name = STATUS_EDITED_EVENT)
class StatusEditedEvent(
    val projectId: UUID,
    val statusId: UUID,
    val title: String,
    val color: String,
    createdAt: Long = System.currentTimeMillis(),
) : Event<ProjectAggregate>(
    name = STATUS_EDITED_EVENT,
    createdAt = createdAt,
)

@DomainEvent(name = STATUS_DELETED_EVENT)
class StatusDeletedEvent(
    val projectId: UUID,
    val statusId: UUID,
    createdAt: Long = System.currentTimeMillis(),
) : Event<ProjectAggregate>(
    name = STATUS_DELETED_EVENT,
    createdAt = createdAt,
)

@DomainEvent(name = PROJECT_CREATED_EVENT)
class ProjectCreatedEvent(
    val projectId: UUID,
    val title: String,
    val creatorId: String,
    createdAt: Long = System.currentTimeMillis(),
) : Event<ProjectAggregate>(
    name = PROJECT_CREATED_EVENT,
    createdAt = createdAt,
)

@DomainEvent(name = STATUS_CREATED_EVENT)
class StatusCreatedEvent(
    val projectId: UUID,
    val statusId: UUID,
    val statusName: String,
    val color: String,
    createdAt: Long = System.currentTimeMillis(),
) : Event<ProjectAggregate>(
    name = STATUS_CREATED_EVENT,
    createdAt = createdAt,
)

@DomainEvent(name = USER_ASSIGNED_TO_PROJECT_EVENT)
class UserAssignedToProjectEvent(
        val projectId: UUID,
        val userId: UUID,
        createdAt: Long = System.currentTimeMillis(),
) : Event<ProjectAggregate>(
        name = USER_ASSIGNED_TO_PROJECT_EVENT,
        createdAt = createdAt,
)
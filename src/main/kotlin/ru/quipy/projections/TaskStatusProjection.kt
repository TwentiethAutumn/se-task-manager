package ru.quipy.projections


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import ru.quipy.api.project.ProjectAggregate
import ru.quipy.api.project.StatusCreatedEvent
import ru.quipy.api.task.StatusAssignedToTaskEvent
import ru.quipy.api.task.TaskAggregate
import ru.quipy.api.task.TaskCreatedEvent
import ru.quipy.api.task.TaskRenamedEvent
import ru.quipy.streams.AggregateSubscriptionsManager
import java.util.*
import javax.annotation.PostConstruct

@Component
class TaskStatusRelation(
        private val taskStatusProjectionRepo: TaskStatusesProjectionRepo,
        private val taskProjectionRepo: TaskProjectionRepo
) {

    @Autowired
    lateinit var subscriptionsManager: AggregateSubscriptionsManager

    @PostConstruct
    fun init() {
        subscriptionsManager.createSubscriber(TaskAggregate::class, "TaskAggregateSubscriberTSProjection") {

            `when`(StatusAssignedToTaskEvent::class) { event ->
                val status = taskStatusProjectionRepo.findById(event.statusId)
                        ?: throw Exception()
                val task = taskProjectionRepo.findByTaskId(event.taskId) ?: throw Exception()
                task.statusId = event.statusId
                task.taskUpdatedAt = System.currentTimeMillis();
                taskProjectionRepo.save(task);
            }

            `when`(TaskRenamedEvent::class) { event ->
                val task = taskProjectionRepo.findByTaskId(event.taskId) ?: throw Exception();
                task.taskTitle = event.title;
                task.taskUpdatedAt = System.currentTimeMillis();
                taskProjectionRepo.save(task);
            }

            `when`(TaskCreatedEvent::class) { event ->
                taskProjectionRepo.save(TaskProjection(event.taskId, event.statusId, event.createdAt, event.createdAt,
                        event.taskTitle, event.projectId, null))
            }
        }

        subscriptionsManager.createSubscriber(ProjectAggregate::class, "ProjectAggregateSubscriberTSProjection") {

            `when`(StatusCreatedEvent::class) { event ->
                taskStatusProjectionRepo.save(TaskStatusProjection(event.statusId, event.statusName))
            }

        }
    }
}

@Document("task-status-projection")
data class TaskStatusProjection(
        @Id
        var statusId: UUID?,
        var statusName: String?
)

@Document("task-projection")
data class TaskProjection(
        @Id
        var taskId: UUID?,
        var statusId: UUID?,
        var taskCreatedAt: Long?,
        var taskUpdatedAt: Long?,
        var taskTitle: String?,
        var projectId: UUID,
        var assignedUserId: UUID? = null
)

@Repository
interface TaskStatusesProjectionRepo : MongoRepository<TaskStatusProjection, UUID>;

@Repository
interface TaskProjectionRepo : MongoRepository<TaskProjection, UUID> {
    fun findByTaskId(task: UUID): TaskProjection
}
package ru.quipy.service

import org.springframework.stereotype.Service
import ru.quipy.projections.*
import java.util.*

@Service
class ProjectionsService constructor(val userProjectionRepo: UserProjectionRepo,
                                     val taskProjectionRepo: TaskProjectionRepo,
                                     val taskStatusesProjectionRepo: TaskStatusesProjectionRepo,
                                     val projectUserProjectionRepo: ProjectUserProjectionRepo,
                                     val projectTaskUserRepo: ProjectTaskUserRepo){

    fun checkIfUserWithNickExists(nickname: String) : Boolean {
        return userProjectionRepo.existsByNickname(nickname)
    }

    fun checkIfUserExistsByPartOfNick(nicknamePart: String) : List<UserProjection> {
        return userProjectionRepo.findByNicknameContaining(nicknamePart);
    }

    fun getProjectsWhereParticipant(id: UUID): List<ProjectUserProjection> {
        return projectUserProjectionRepo.findAllByUserId(id);
    }

    fun getParticipantsOfTheProjects(id: UUID): List<ProjectUserProjection> {
        return projectUserProjectionRepo.findAllByProjectId(id);
    }

    fun getTaskById(id: UUID): TaskStatusProjectionResult {
        val task = taskProjectionRepo.findByTaskId(id);
        val status = taskStatusesProjectionRepo.findById(task.statusId!!);
        if (status.isPresent) {
            return TaskStatusProjectionResult(task.taskId, status.get().statusId,
                    status.get().statusName, task.taskCreatedAt, task.taskUpdatedAt, task.taskTitle, task.projectId,
                    task.assignedUserId);
        }
        return TaskStatusProjectionResult(task.taskId, null,
                null, task.taskCreatedAt, task.taskUpdatedAt, task.taskTitle, task.projectId,
                task.assignedUserId);
    }

    fun getProjectById(id: UUID): ProjectTasksUserProjection {
        return projectTaskUserRepo.findByProjectId(id);
    }
}

data class TaskStatusProjectionResult(
        var taskId: UUID?,
        var statusId: UUID?,
        var statusName: String?,
        var taskCreatedAt: Long?,
        var taskUpdatedAt: Long?,
        var taskTitle: String?,
        var projectId: UUID?,
        var assignedUserId: UUID? = null
)
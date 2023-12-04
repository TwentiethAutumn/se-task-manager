package ru.quipy.controller

import org.springframework.web.bind.annotation.*
import ru.quipy.projections.ProjectTasksUserProjection
import ru.quipy.projections.ProjectUserProjection
import ru.quipy.projections.TaskStatusProjection
import ru.quipy.projections.UserProjection
import ru.quipy.service.ProjectionsService
import ru.quipy.service.TaskStatusProjectionResult
import java.util.*

@RestController
@RequestMapping("/projections")
class ProjectionsController (val projectionsService: ProjectionsService) {

    /**
     * “Гость” может направить запрос в систему, существует ли уже пользователь
     *      с переданным никнеймом и получить ответ в формате “true”/”false”
     */
    @GetMapping("/userProjection/checkIfExists")
    fun checkUserExists(@RequestParam nickname: String): Boolean {
        return projectionsService.checkIfUserWithNickExists(nickname);
    }

    /**
     * Выполнить поиск по всем пользователям, используя никнейм (или его части)
     */
    @GetMapping("/userProjection/findByNicknamePart")
    fun findUserByNicknamePart(@RequestParam partOfNick: String): List<UserProjection> {
        return projectionsService.checkIfUserExistsByPartOfNick(partOfNick);
    }

    /**
     * Пользователь должен иметь возможность просмотреть список всех проектов, в которых он состоит.
     */
    @GetMapping("/projectUserProjection/findProjectsWhereParticipant")
    fun findProjectsWhereParticipant(@RequestParam userId: UUID): List<ProjectUserProjection> {
        return projectionsService.getProjectsWhereParticipant(userId);
    }

    /**
     * Пользователь должен иметь возможность увидеть всех участников проекта.
     */
    @GetMapping("/projectUserProjection/")
    fun getAllParticipantsOfTheProject(@RequestParam projectId: UUID) : List<ProjectUserProjection> {
        return projectionsService.getParticipantsOfTheProjects(projectId);
    }

    /**
     * Пользователь должен иметь возможность получить задачу по id.
     */
    @GetMapping("/taskStatusProjection")
    fun getTaskById(@RequestParam taskId: UUID): TaskStatusProjectionResult {
        return projectionsService.getTaskById(taskId);
    }

    /**
     * Пользователь должен иметь возможность получить проект по id.
     */
    @GetMapping("/projectTaskUserProjection")
    fun getProjectById(@RequestParam projectId: UUID): ProjectTasksUserProjection {
        return projectionsService.getProjectById(projectId);
    }
}
package ru.quipy.controller

import org.springframework.web.bind.annotation.*
import ru.quipy.projections.ProjectTasksUserProjection
import ru.quipy.projections.ProjectUserProjection
import ru.quipy.projections.TaskStatusProjection
import ru.quipy.projections.UserProjection
import ru.quipy.service.ProjectionsService
import java.util.*

@RestController
@RequestMapping("/projections")
class ProjectionsController (val projectionsService: ProjectionsService) {
    @GetMapping("/userProjections")
    fun getUserProjections() : List<UserProjection> { //todo: исправить на запросы из доки
        return this.projectionsService.getAllUserProjection();
    }

    @GetMapping("/taskStatusProjections")
    fun getTaskStatusProjections() : List<TaskStatusProjection> { //todo: исправить на запросы из доки
        return this.projectionsService.getAllTaskStatusProjection();
    }

    @GetMapping("/projectUserProjections")
    fun getProjectUserProjections() : List<ProjectUserProjection> { //todo: исправить на запросы из доки
        return this.projectionsService.getAllProjectUserProjection();
    }

    @GetMapping("/projectTaskuser")
    fun getTaskUserProjections() : List<ProjectTasksUserProjection>{ //todo: исправить на запросы из доки. Для 4 проекции это getProjectByID и listTasksInProject
        return this.projectionsService.getAllProjectTaskUserProjection();
    }
}
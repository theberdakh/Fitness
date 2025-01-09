# Fitness App
This fitness application follows a clean architecture pattern with clear separation of concerns. The project is organized into distinct modules to ensure maintainability, testability, and scalability.

## Screenshots 
|                 Free lessons Screen                 |                  Lesson Screen                   |                     Home Screen                     |                 About Screen                 |
|:---------------------------------------------------:|:------------------------------------------------:|:---------------------------------------------------:|:--------------------------------------------:|
| ![Free lessons](/docs/screenshots/screenshot-1.png) | ![Exercises](/docs/screenshots/screenshot-1.png) | ![Free lessons](/docs/screenshots/screenshot-3.png) | ![About](/docs/screenshots/screenshot-4.png) |

## About Repository
| Description                  | Badge                                                                                           | 
|:-----------------------------|:------------------------------------------------------------------------------------------------|
| Size                         | ![Size](https://img.shields.io/github/repo-size/theberdakh/kotlin-practice)                     | 
| Last commit                  | ![Last commit](https://img.shields.io/github/last-commit/theberdakh/kotlin-practice)            | 
| Total commits                | ![Total commits](https://img.shields.io/github/commit-activity/t/theberdakh/kotlin-practice)    |
| Total commits on Main branch | ![Main branch](https://img.shields.io/github/commit-activity/t/theberdakh/kotlin-practice/Main) | 
| Licence                      | ![MIT License](https://img.shields.io/badge/License-MIT-green.svg)                              | 

## Official resources for reference
| Resource              | Link                                                                                                                                          | 
|:----------------------|:----------------------------------------------------------------------------------------------------------------------------------------------|
| User interface design | [Figma](https://www.figma.com/board/KFQJ2Za0yr0NpKFVhEpuAt/Fitness-app?node-id=0-1&p=f)                                                       | 
| Remote database       | [Postman](https://aralhubteam.postman.co/workspace/1d62bd13-5d81-4d75-8da9-6070cebe2d73/folder/39477068-a865022b-1830-44cf-ae2f-631235dfc1e0) |

## How to
### Send arguments between fragments
This project does not use Google's safe args plugin. Instead, it uses a custom implementation to send arguments between fragments.
For example, to send arguments from ScreenA to ScreenB, you should use ScreenB's companion object function of ScreenB.args() which contains all the arguments ScreenB needs: 
```kotlin
findNavController().navigate(R.id.action_screenA_to_screenB, ScreenB.args("arg1", "arg2"))
```




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

## Personal task log by date 

Tasks: 25/10/2021 
- [x]  feat(auth): Add verification logic for user-entered codes
- [x]  feat(auth): Implement user logout functionality
- [ ]  fix(auth): Redirect to login page on 401 error
- [ ]  feat(videos): Set up fake videos repository for testing
- [ ]  feat(videos): Implement video playback functionality

Tasks: 26/10/2021
- [x]  add support YouTube video thumbnails in home video list
- [ ]  add support for video playback in lesson screen
- [ ]  add support for video playback in full screen mode

Tasks: 27/10/2021
- [x] add support for network error handling

Tasks: 28/10/2021
- [x] get packs from server
- [ ] get modules from server
- [x] get random lessons from server

Tasks: 3/1/2025 
- [x] add all free videos without pagination 
- [x] add description & checklist for each lesson

Tasks: 4/1/2025
- [ ] add lessons screen to show all modules 
- [ ] add lessons screen to show all lessons in a module
- [ ] add support to show videos in lesson screen

Tasks 6/1/2025
- [x] my orders screen [subscribed packs]
- [x] my modules screen 
- [x] lessons screen

Tasks 7/1/2025
- [x] modules unstarted status, lesson numbers 
- [x] lesson lock based on lesson_view_finished (preferably on top of thumbnail)
- [x] show dialog when locked lesson clicked 
- [] navigate to lesson screen when unlocked lesson clicked
- [] update text of finish dialog text 

Tasks 8/1/2025
- [] start documenting code
- [] extract data layer
- [] extract domain layer



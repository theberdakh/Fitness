## Fitness App

Figma design link: https://www.figma.com/board/KFQJ2Za0yr0NpKFVhEpuAt/Fitness-app?node-id=0-1&p=f
Postman collection link: https://aralhubteam.postman.co/workspace/1d62bd13-5d81-4d75-8da9-6070cebe2d73/folder/39477068-a865022b-1830-44cf-ae2f-631235dfc1e0

## Overview
This fitness application follows a clean architecture pattern with clear separation of concerns. The project is organized into distinct modules to ensure maintainability, testability, and scalability.

## Project Structure
The core module contains essential application components:
/log: Logging functionality and utilities
/navigation: Navigation management and routing
/preferences: Application preferences and settings management

#### Authentication Module (/feature/auth)
Authentication flow is implemented with the following components:
Screens:
- AddAgeGenderScreen: User age and gender input
- AddGoalScreen: Fitness goals selection
- AddHeightScreen: Height measurement input
- AddNameScreen: User name registration
- AddPhoneNumberScreen: Phone number collection
- AddWeightScreen: Weight measurement input
- EnterSMSCodeScreen: SMS verification
- LogoScreen: Application branding screen

The auth module is further divided into:
/adapter: Contains adapters for external service integration
/domain: Business logic and domain models

### UI Module (/ui)
Contains shared UI components and resources used across the application.
App Module (/app)
The main application module containing:
- Application class instance and DI initialization

Tasks: 25/10/2021 
- [+]  feat(auth): Add verification logic for user-entered codes
- [+]  feat(auth): Implement user logout functionality
- [-]  fix(auth): Redirect to login page on 401 error
- [-]  feat(videos): Set up fake videos repository for testing
- [-]  feat(videos): Implement video playback functionality

Tasks: 26/10/2021
- [+]  add support YouTube video thumbnails in home video list
- [+]  add support for video playback in lesson screen
- [+]  add support for video playback in full screen mode

Tasks: 27/10/2021
- [+] add support for network error handling

Tasks: 28/10/2021
- [+] get packs from server
- [-] get modules from server
- [+] get random lessons from server

Tasks: 3/1/2025 
- [+] add all free videos without pagination 
- [+] add description & checklist for each lesson

Tasks: 4/1/2025
- [-] add lessons screen to show all modules 
- [-] add lessons screen to show all lessons in a module
- [-] add support to show videos in lesson screen

Tasks 6/1/2025
- [+] my orders screen [subscribed packs]
- [+] my modules screen 
- [+] lessons screen

Tasks 7/1/2025
- [] modules unstarted status, lesson numbers 
- [] lesson lock based on lesson_view_finished (preferably on top of thumbnail)
- [] show dialog when locked lesson clicked 
- [] navigate to lesson screen when unlocked lesson clicked
- [] update text of finish dialog text 



## Fitness App

Figma design link: https://www.figma.com/board/KFQJ2Za0yr0NpKFVhEpuAt/Fitness-app?node-id=0-1&p=f

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


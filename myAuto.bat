@echo off
SET "PROJECT_DIR=C:\Users\Production\IdeaProjects\kraya-platform"
SET "MODEL_DIR=%PROJECT_DIR%\src\main\java\com\kraya\platform\model"
SET "REPOSITORY_DIR=%PROJECT_DIR%\src\main\java\com\kraya\platform\repository"
SET "SERVICE_DIR=%PROJECT_DIR%\src\main\java\com\kraya\platform\service"
SET "SERVICE_IMPL_DIR=%PROJECT_DIR%\src\main\java\com\kraya\platform\service\impl"

REM Create directories if they do not exist
mkdir "%MODEL_DIR%"
mkdir "%REPOSITORY_DIR%"
mkdir "%SERVICE_DIR%"
mkdir "%SERVICE_IMPL_DIR%"

REM Create empty model file for Role
type nul > "%MODEL_DIR%\Role.java"

REM Create empty repository file for Role
type nul > "%REPOSITORY_DIR%\RoleRepository.java"

REM Create empty service file for Role
type nul > "%SERVICE_DIR%\RoleService.java"

REM Create empty service implementation file for Role
type nul > "%SERVICE_IMPL_DIR%\RoleServiceImpl.java"

echo All directories and empty files for Role have been created.
pause

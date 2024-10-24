@echo off
SET "PROJECT_DIR=C:\Users\Production\IdeaProjects\kraya-platform"
SET "DTO_DIR=%PROJECT_DIR%\src\main\java\com\kraya\platform\dto"
SET "MODEL_DIR=%PROJECT_DIR%\src\main\java\com\kraya\platform\model"
SET "REPOSITORY_DIR=%PROJECT_DIR%\src\main\java\com\kraya\platform\repository"
SET "SERVICE_IMPL_DIR=%PROJECT_DIR%\src\main\java\com\kraya\platform\service\impl"
SET "CONTROLLER_DIR=%PROJECT_DIR%\src\main\java\com\kraya\platform\controller"

REM Create directories if they do not exist
mkdir "%DTO_DIR%"
mkdir "%MODEL_DIR%"
mkdir "%REPOSITORY_DIR%"
mkdir "%SERVICE_IMPL_DIR%"
mkdir "%CONTROLLER_DIR%"

REM Create empty DTO files for Role if they do not exist
if not exist "%DTO_DIR%\RoleRequest.java" type nul > "%DTO_DIR%\RoleRequest.java"
if not exist "%DTO_DIR%\RoleResponse.java" type nul > "%DTO_DIR%\RoleResponse.java"

REM Create empty model file for Role if it does not exist
if not exist "%MODEL_DIR%\Role.java" type nul > "%MODEL_DIR%\Role.java"

REM Create empty service implementation file for Role if it does not exist
if not exist "%SERVICE_IMPL_DIR%\RoleServiceImpl.java" type nul > "%SERVICE_IMPL_DIR%\RoleServiceImpl.java"

REM Create empty controller file for Role if it does not exist
if not exist "%CONTROLLER_DIR%\RoleController.java" type nul > "%CONTROLLER_DIR%\RoleController.java"

echo All directories and empty files for Role management have been created if they did not already exist.
pause

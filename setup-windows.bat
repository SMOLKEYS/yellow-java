@echo off
cd %userprofile%
rem Build tools install dir
mkdir android
cd android
mkdir build-tools\33.0.0

curl https://dl.google.com/android/repository/build-tools_r33-windows.zip -o r33.zip
curl https://dl.google.com/android/repository/commandlinetools-win-10406996_latest.zip -o cli_tools.zip

if %errorlevel% neq 0 exit /b %errorlevel%

setx ANDROID_HOME %userprofile%\android
rem temporary dir because idk window 1 9
set ANDROID_HOME %userprofile%\android

tar -xf r33.zip
tar -xf cli_tools.zip

.\cmdline-tools\bin\sdkmanager.bat --sdk_root=%userprofile%\android "platforms;android-30" "platform-tools"

move android-13\* build-tools\33.0.0
rd /S /Q android-13

setx ANDROID_SDK_ROOT %userprofile%\android\platforms\android-30
setx ANDROID_BINARIES %userprofile%\android\build-tools\33.0.0

setx PATH "%PATH%;%userprofile%\android\platforms\android-30;%userprofile%\android\platform-tools;%userprofile%\android\build-tools\33.0.0"

echo Programs will detect the changes you set when restarted. You can also restart your PC so it applies to everything.
pause
# Scan2Go

Scan2Go is an Android application designed to turn scanned documents and photos into clean, shareable PDF files in seconds. The app combines mobile document scanning with a lightweight document history, local persistence, and sharing/downloading actions to make everyday paperwork easier.

The project is built in Kotlin using Jetpack Compose and follows a modern Android architecture with repository abstraction and dependency injection.

## Overview

Scan2Go helps users:
- Capture documents using the device camera or built-in document scanner flow
- Generate a PDF from scanned pages
- Review recent documents in a local history list
- Download generated PDFs to device storage
- Share PDFs with other apps
- View AdMob banner placements for monetization

## Current status

This repository currently focuses on the core document-scanning workflow and PDF lifecycle. Planned enhancements include OCR/text extraction, compression, and additional utilities for document productivity.

## Features

- Document scanning with Google ML Kit Document Scanner
- PDF output with page count metadata
- Recent document history stored locally via Room
- Download and share actions for generated PDFs
- Material 3 interface with Compose-based UI components
- AdMob banner integration
- Simple modular architecture for future expansion

## Tech stack

- Kotlin
- Android SDK / Jetpack
- Jetpack Compose
- Material 3
- ViewModel + StateFlow-style UI state management
- Room for local persistence
- Hilt for dependency injection
- Google ML Kit Document Scanner
- Google AdMob

## Architecture

The app uses a straightforward layered architecture:

- UI layer: Compose screens and reusable components in the app UI package
- ViewModel layer: state coordination for document history and actions
- Repository layer: abstraction for document access and persistence
- Data layer: Room database and Document entity
- Dependency injection: Hilt modules for database and repository wiring

This keeps the app easy to extend while maintaining clear separation of responsibilities.

## Project structure

```text
Scan2Go/
├── app/
│   ├── build.gradle.kts
│   └── src/
│       ├── main/
│       │   ├── AndroidManifest.xml
│       │   ├── java/com/keru/pdfcreator/
│       │   │   ├── App.kt
│       │   │   ├── data/
│       │   │   │   ├── AppDatabase.kt
│       │   │   │   ├── Document.kt
│       │   │   │   └── repositories/
│       │   │   ├── di/
│       │   │   │   ├── AppModule.kt
│       │   │   │   └── DocumentModule.kt
│       │   │   ├── domain/
│       │   │   │   └── repositories/
│       │   │   ├── ui/
│       │   │   │   ├── AppViewModel.kt
│       │   │   │   ├── MainActivity.kt
│       │   │   │   └── components/
│       │   │   └── utils/
│       │   └── res/
│       └── test/
├── build.gradle.kts
├── gradle/
├── gradle.properties
├── gradlew
├── gradlew.bat
├── settings.gradle.kts
├── README.md
└── .gitignore
```

## Getting started

### Prerequisites

- Android Studio (latest stable version recommended)
- Android SDK with API 34 configured
- JDK 17 or a compatible Java version for Android development
- A connected Android device or emulator

### Install and run

1. Clone the repository:

```bash
git clone https://github.com/alesfc1/Scan2Go.git
cd Scan2Go
```

2. Open the project in Android Studio.

3. Let Gradle sync the project dependencies.

4. Run the app on an emulator or a physical Android device.

Alternatively, from the command line:

```bash
./gradlew assembleDebug
```

## Usage

1. Launch the app.
2. Tap the primary card to start a document scan.
3. Capture or import pages using the scanner flow.
4. Save the generated PDF.
5. Use the document history to review, download, or share recent files.

## Notes on permissions and storage

The app requests storage-related permissions needed for saving and sharing generated PDFs, especially on Android versions where filesystem access is restricted. On newer Android versions, the application may open system settings to allow file access when saving to Downloads.

## Roadmap

The project roadmap includes:
- OCR/text extraction from scanned images
- PDF compression utilities
- Rename or better file management capabilities
- Expanded productivity features
- Additional monetization and app-store optimization work
- Play Store launch preparation and marketing assets


## Repository information

- Repository: alesfc1/Scan2Go
- Project type: Android mobile application
- Primary language: Kotlin

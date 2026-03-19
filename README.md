<div align="center">

# 📚 StudyMate

**A production-ready student management system for educational institutions**

[![Android CI/CD](https://github.com/rajatt04/StudyMateAlpha/actions/workflows/android-ci.yml/badge.svg)](https://github.com/rajatt04/StudyMateAlpha/actions/workflows/android-ci.yml)
![API](https://img.shields.io/badge/API-26%2B-brightgreen.svg)
![Kotlin](https://img.shields.io/badge/Kotlin-2.2.0-blue.svg)
![Architecture](https://img.shields.io/badge/Architecture-Clean%20MVVM-purple.svg)
![License](https://img.shields.io/badge/License-MIT-green.svg)

</div>

---

## App Screenshots

Below are some screenshots of the StudyMate application showcasing its features and user interface:

| Screenshot 1      | Screenshot 2      | Screenshot 3      |Screenshot 4       |
|-------------------|-------------------|-------------------|-------------------|
| ![Image1](https://github.com/rajatt04/rajatt04.github.io/blob/main/images/StudyMate/Screenshot_20260318_093117.jpg?raw=true) | ![Image2](https://github.com/rajatt04/rajatt04.github.io/blob/main/images/StudyMate/Screenshot_20260318_095245.jpg?raw=true) | ![Image3](https://github.com/rajatt04/rajatt04.github.io/blob/main/images/StudyMate/Screenshot_20260318_095344.jpg?raw=true) |![Image4](https://github.com/rajatt04/rajatt04.github.io/blob/main/images/StudyMate/Screenshot_20260318_095447.jpg?raw=true) |

| Screenshot 5      | Screenshot 6      | Screenshot 7      |Screenshot 8       |
|-------------------|-------------------|-------------------|-------------------|
| ![Image5](https://github.com/rajatt04/rajatt04.github.io/blob/main/images/StudyMate/Screenshot_20260318_095523.jpg?raw=true) | ![Image6](https://github.com/rajatt04/rajatt04.github.io/blob/main/images/StudyMate/Screenshot_20260318_095611.jpg?raw=true) | ![Image7](https://github.com/rajatt04/rajatt04.github.io/blob/main/images/StudyMate/Screenshot_2026_0318_093254.jpg?raw=true) |![Image8](https://github.com/rajatt04/rajatt04.github.io/blob/main/images/StudyMate/Screenshot_2026_0318_093315.jpg?raw=true) |

## 🏗️ Architecture

StudyMate follows **Clean Architecture** with **MVVM** pattern and **Hilt** dependency injection.

```
app/
├── core/               # Cross-cutting concerns
│   ├── crash/          # Global exception handling
│   ├── security/       # EncryptedSharedPreferences
│   └── util/           # Resource wrapper, PasswordUtils
├── data/               # Data layer
│   ├── local/          # Room entities, DAOs, AppDatabase
│   └── repository/     # Repository implementations
├── di/                 # Hilt DI modules
├── domain/             # Domain layer
│   ├── repository/     # Repository interfaces
│   └── usecase/        # Business logic use cases
├── presentation/       # UI layer (Jetpack Compose)
│   ├── auth/           # Login, Role Selection, Splash
│   ├── common/         # Reusable Loading/Error/Empty states
│   ├── dashboard/      # Admin, Faculty, Student dashboards
│   ├── navigation/     # NavHost, routes, bottom nav
│   ├── notices/        # Notice feed & management
│   ├── user/           # User directory & management
│   └── ...             # Academics, Chat, Library, Settings
└── ui/theme/           # Material 3 theming
```

## 🛠️ Tech Stack

| Category | Technology |
|----------|-----------|
| **Language** | Kotlin 2.2.0 |
| **UI** | Jetpack Compose + Material 3 |
| **Architecture** | Clean Architecture + MVVM |
| **DI** | Hilt (Dagger) |
| **Database** | Room |
| **State** | StateFlow / Coroutines |
| **Security** | EncryptedSharedPreferences, PBKDF2 password hashing |
| **Navigation** | Navigation Compose |
| **CI/CD** | GitHub Actions |
| **Build** | Gradle KTS with version catalog |

## ✨ Features

- **Multi-role authentication** — Admin, Faculty, Student with secure password hashing
- **Dashboard** — Live metrics from database for each role
- **User management** — Add, view, search, delete students and faculty
- **Notices** — Create, view, and manage institutional notices
- **Academics** — Timetable, grades, attendance tracking
- **Responsive** — Bottom nav (phone) / Nav rail (tablet) via WindowSizeClass
- **Dark mode** — Full light + dark theme with Material 3 dynamic colors
- **Edge-to-edge** — Modern immersive UI

## 🚀 Getting Started

### Prerequisites
- Android Studio Ladybug (2024.2.1) or later
- JDK 17
- Min SDK 26 (Android 8.0)

### Build & Run
```bash
# Clone the repository
git clone https://github.com/rajatt04/StudyMateAlpha.git

# Open in Android Studio and sync Gradle

# Build debug APK
./gradlew assembleDebug

# Run unit tests
./gradlew test
```

## 🔐 Security

- Passwords hashed with **PBKDF2WithHmacSHA256** (65536 iterations, 256-bit key)
- Session data in **EncryptedSharedPreferences** (AES-256)
- Network restricted to **HTTPS only** via network security config
- **R8/ProGuard** obfuscation enabled for release builds
- Log statements stripped from release builds

## 📋 Release Checklist

- [ ] Update `versionCode` and `versionName` in `build.gradle.kts`
- [ ] Run `./gradlew lint` and fix all warnings
- [ ] Run `./gradlew test` and ensure all pass
- [ ] Generate signed AAB via `./gradlew bundleRelease`
- [ ] Test on multiple screen sizes (phone + tablet)
- [ ] Verify dark mode compatibility
- [ ] Update `PRIVACY_POLICY.md` if data handling changed
- [ ] Create GitHub Release with changelog

## 👤 Author

**Rajat Kevat** — [GitHub](https://github.com/rajatt04) · [Portfolio](https://rajatt04.github.io)

## 📄 License

This project is licensed under the MIT License.

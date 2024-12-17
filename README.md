# PictureViewer

Этот проект представляет собой простое Android приложение для просмотра изображений с поддержкой следующих функций:

## Особенности

-   **Отображение списка изображений:** Приложение загружает список изображений из URL-адреса и отображает их в виде сетки изображений.
-   **Просмотр полного изображения:** Нажатие на превью открывает экран с полным изображением.
-   **Pinch to Zoom:** На экране с полным изображением можно использовать жесты "щипок" для масштабирования.
-   **Скрытие/отображение навбара:** Нажатие на экран с полным изображением скрывает/отображает системную навигационную панель.
-   **Переключение темы:** Приложение поддерживает переключение между светлой и темной темой(зависит от темы телефона).

## Технологии

*   **Jetpack Compose:** Современный UI toolkit для Android.
*   **Coil:** Библиотека для загрузки и кэширования изображений.
*   **Coroutines:** Для асинхронных операций.
*   **Navigation Compose:** Для навигации между экранами.
*   **Material Design 3:** Для стилизации UI компонентов.

## Структура проекта

*   **`MainActivity.kt`:** Главный класс приложения, устанавливает `NavHost` и управляет темой.
*   **`ImageListScreen.kt`:** Экран со списком превью изображений и логикой загрузки.
*   **`ZoomedImageScreen.kt`:** Экран с увеличенным изображением и возможностью pinch-to-zoom, а также скрытием/отображением навигационной панели.

## Зависимости

Следующие зависимости должны быть объявлены в файле `build.gradle.kts` модуля приложения:

```kotlin
implementation("androidx.core:core-ktx:1.15.0")
implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")
implementation("androidx.activity:activity-compose:1.9.3")
implementation(platform("androidx.compose:compose-bom:2024.12.01"))
implementation("androidx.compose.ui:ui")
implementation("androidx.compose.ui:ui-graphics")
implementation("androidx.compose.ui:ui-tooling-preview")
implementation("androidx.compose.material3:material3")
implementation("androidx.compose.material:material-icons-extended")
implementation("io.coil-kt.coil3:coil-compose:3.0.4")
implementation("io.coil-kt.coil3:coil-network-okhttp:3.0.4")
implementation("androidx.navigation:navigation-compose:2.8.5")
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.9.0")
debugImplementation("androidx.compose.ui:ui-tooling")
debugImplementation("androidx.compose.ui:ui-test-manifest")

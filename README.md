# groschenberry

Учебный проект Дитиной Марии
Курс [Kotlin Backend Developer](https://otus.ru/lessons/kotlin/).
Поток курса 2025-02.

Groschenberry -- это приложение для нумизматов, позволяющее вести учёт монет, осуществлять обмен/продажу монет. 
Предоставляет справочник монет Российской Федерации, позволяет добавлять монеты из справочника в свои коллекции (приватные и публичные), 
выставлять свои монеты для продажи/обмена, искать монеты по публичным коллекциям других пользователй.

## Визуальная схема фронтенда

![Макет фронта](imgs/design-layout.png)

## Документация

1. Маркетинг и аналитика
    1. [Целевая аудитория](./docs/01-biz/01-target-audience.md)
    2. [Заинтересанты](./docs/01-biz/02-stakeholders.md)
    3. [Пользовательские истории](./docs/01-biz/03-bizreq.md)
2. Аналитика:
    1. [Функциональные требования](./docs/02-analysis/01-functional-requiremens.md)
    2. [Нефункциональные требования](./docs/02-analysis/02-nonfunctional-requirements.md)
3. DevOps
   1. [Файлы сборки](./deploy)
4. Архитектура
   1. [ADR](./docs/04-architecture/01-adrs.md)
   2. [Описание API](./docs/04-architecture/02-api.md)
   3. [Компонентная схема](./docs/04-architecture/03-arch.md)
5. Тесты
   1. [Тестовые сценарии](./docs/05-testing/01-tests-list.md)

# Структура проекта

### Плагины Gradle сборки проекта

1. [build-plugin](build-plugin) Модуль с плагинами
2. [BuildPluginJvm](build-plugin/src/main/kotlin/BuildPluginJvm.kt) Плагин для сборки проектов JVM
2. [BuildPluginMultiplarform](build-plugin/src/main/kotlin/BuildPluginMultiplatform.kt) Плагин для сборки
   мультиплатформенных проектов

## Проектные модули

### Транспортные модели, API

1. [specs](specs) - описание API в форме OpenAPI-спецификаций
2. [groschenberry-api-v1-kmp](groschenberry-be/groschenberry-api-v1-kmp) - Генерация транспортных моделей с KMP
3. [groschenberry-common](groschenberry-be/groschenberry-common) - модуль с общими классами для модулей проекта. В
   частности, там располагаются внутренние модели и контекст.
4. [groschenberry-api-log](groschenberry-be/groschenberry-api-log) - Маппер между внутренними моделями и
   моделями логирования первой версии

### Фреймворки и транспорты

1. [ok-marketplace-app-ktor](ok-marketplace-be/ok-marketplace-app-ktor) - Приложение на Ktor

### Модули бизнес-логики

1. [ok-marketplace-stubs](ok-marketplace-be/ok-marketplace-stubs) - Стабы для ответов сервиса
2. [ok-marketplace-biz](ok-marketplace-be/ok-marketplace-biz) - Модуль бизнес-логики приложения: обслуживание стабов,
   валидация, работа с БД

## Библиотеки

### Мониторинг и логирование

1. [deploy](deploy) - Инструменты мониторинга и деплоя
2. [groschenberry-lib-logging-common](ok-marketplace-libs/ok-marketplace-lib-logging-common) - Общие объявления для
   логирования
3. [groschenberry-lib-logging-logback](ok-marketplace-libs/ok-marketplace-lib-logging-logback) - Библиотека логирования
   на базе библиотеки Logback

## Тестирование

### Сквозные/интеграционные тесты

1. [groschenberry-e2e-be](ok-marketplace-tests/ok-marketplace-e2e-be) - Сквозные/интеграционные тесты для бэкенда
   системы

package com.otus.otuskotlin.groschenberry.e2e.be.fixture.client

/**
 * Клиент к нашему приложению в докер-композе, который умеет отправлять запрос и получать ответ.
 * Способ отправки/получения зависит от приложения - rabbit, http, ws, ...
 */
interface Client {
    /**
     * @param type : basic или detail
     * @param path путь к ресурсу, имя топика и т.п. (create)
     * @param request тело сообщения в виде строки
     * @return тело ответа
     */
    suspend fun sendAndReceive(type: String, path: String, request: String): String
}

/*-
 * #%L
 * fancyDelete
 * %%
 * Copyright (C) 2016 - 2019 Frederik Kammel
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.github.vatbub.fancyDelete

import org.slf4j.LoggerFactory
import java.util.logging.Level
import java.util.logging.LogManager
import java.util.logging.Logger

@Suppress("unused")
val Any.logger
    get() = LoggerFactory.getLogger(this.javaClass)!!

val logger
    get() = LoggerFactory.getLogger("globalLogger")!!

private val logManager = LogManager.getLogManager()!!

private val rootLogger: Logger
    get() = logManager.getLogger("")

private val loggers: List<Logger>
    get() = logManager.loggerNames.asList().transform { logManager.getLogger(it) }

var logLevel: Level
    get() = rootLogger.level
    set(value) {
        rootLogger.level = value
        rootLogger.handlers.forEach { it.level = value }
        loggers.forEach {
            it.level = value
            it.handlers.forEach { handler -> handler.level = value }
        }
    }

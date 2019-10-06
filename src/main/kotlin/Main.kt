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

import com.beust.jcommander.JCommander
import com.beust.jcommander.ParameterException
import com.github.vatbub.commandlineUserPromptProcessor.Prompt
import com.github.vatbub.common.core.Common
import com.github.vatbub.kotlin.preferences.Key
import com.github.vatbub.kotlin.preferences.Preferences
import com.github.vatbub.kotlin.preferences.PropertiesFileKeyValueProvider
import java.io.File
import java.io.IOException
import java.nio.file.Files
import kotlin.system.exitProcess

val preferences by lazy {
    Preferences(PropertiesFileKeyValueProvider(Common.getInstance().andCreateAppDataPathAsFile.toPath().resolve("settings.properties").toFile()))
}

object DryRunKey : Key<Boolean>("dryRun", false, { it.toBoolean() }, { it.toString() })
object ConfirmationKey : Key<Boolean>("confirmBeforeDelete", true, { it.toBoolean() }, { it.toString() })

fun main(args: Array<String>) {
    Common.getInstance().appName = "fancyDelete"
    val jarFileName = Common.getInstance().pathAndNameOfCurrentJar

    logger.trace("Parsing command line arguments...")
    val commandLineParams = CommandLineParams()
    val jCommander = JCommander(commandLineParams)
    jCommander.programName = jarFileName
    jCommander.mainParameterDescription

    try {
        jCommander.parse(*args)
    } catch (parameterException: ParameterException) {
        logger.warn("Could not parse command line parameters", parameterException)
        jCommander.usage()
        exitProcess(1)
    }

    logLevel = commandLineParams.logLevel

    logger.debug("The supplied command line parameters are:\n$commandLineParams")

    if (commandLineParams.help) {
        jCommander.usage()
        exitProcess(0)
    }

    val dryRunCopy = commandLineParams.dryRun
    if (dryRunCopy != null) {
        logger.info("Setting dryRun to ${dryRunCopy}...")
        preferences[DryRunKey] = dryRunCopy
    }

    val confirmationSpecifiedCopy = commandLineParams.confirmation
    if (confirmationSpecifiedCopy != null) {
        logger.info("Setting confirmBeforeDelete to ${confirmationSpecifiedCopy}...")
        preferences[ConfirmationKey] = confirmationSpecifiedCopy
    }

    val dryRun = preferences[DryRunKey]
    val confirmBeforeDeletion = !commandLineParams.skipConfirmationOnce && preferences[ConfirmationKey]

    val fileToDeleteCopy = commandLineParams.fileToDelete

    if (fileToDeleteCopy == null) {
        logger.warn("No file to delete was specified, hence, no file is deleted. If you only wanted to update the configuration, that's great but if you need help to use this program, type $jarFileName -h")
        exitProcess(0)
    }
    val inputFile = File(fileToDeleteCopy)

    val absoluteFile =
            if (inputFile.isAbsolute)
                inputFile
            else
                inputFile.absoluteFile

    require(absoluteFile.exists()) { "'$absoluteFile' does not exist! (File not found)" }
    require(absoluteFile.isFile) { "'$absoluteFile' is not a file!" }

    val filesToDelete = getMatchingFiles(absoluteFile, commandLineParams.allowedExtensions)
    if (dryRun) {
        logger.info("This is a dry run, no files will be deleted!")
        logger.info("Files to be deleted:")
        filesToDelete.forEach {
            logger.info(it.absolutePath)
        }
    } else {
        filesToDelete.forEach {
            if (confirmBeforeDeletion) {
                val prompt = Prompt("Do you really want to delete '$it'?", CustomParsableBoolean())
                val promptResult = prompt.doPrompt() as CustomParsableBoolean
                if (!promptResult.toValue())
                    return@forEach
            }
            logger.info("Deleting '$it'...")
            Files.delete(it.toPath())
            logger.info("Deleted!")
        }
    }
}

fun getMatchingFiles(file: File, allowedExtensions: List<String>? = null): List<File> {
    val result = mutableListOf<File>()
    val files = file.parentFile.listFiles()
            ?: throw IOException("The parent folder is either not a folder (don't ask why) or some other IO error occurred while listing the files.")

    files.forEach {
        if (it.isFile && it.nameWithoutExtension == file.nameWithoutExtension && (allowedExtensions == null || it.extension in allowedExtensions))
            result.add(it)
    }
    return result
}

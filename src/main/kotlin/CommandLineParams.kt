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

import com.beust.jcommander.IStringConverter
import com.beust.jcommander.Parameter
import java.util.logging.Level

class CommandLineParams {
    companion object {
        internal var dryRunSpecified = false
        internal var confirmationSpecified = false
    }

    // The file to be deleted. All files with the same name but other extensions are deleted too. If you want to specify a filter for extensions, see --extensions
    @Parameter(required = false, description = "<fileToDelete>")
    var fileToDelete: String? = null

    @Parameter(names = ["-e", "--extensions"], variableArity = true, description = "Specifies the extensions of the files to be deleted.If not specified, all files with the same file name with any extension are deleted.")
    var allowedExtensions: List<String>? = null

    @Parameter(names = ["-d", "--dryRun"], arity = 1, description = "If set to true, the program will display what files it would delete but no files are actually deleted. IMPORTANT: This setting is saved. That means that all future executions of the program will be dry runs until --dryRun false is specified.")
    var dryRun: Boolean? = null

    @Parameter(names = ["-y", "--yes"], description = "Suppresses the confirmation before files are deleted for this execution only.")
    var skipConfirmationOnce = false

    @Parameter(names = ["-c", "--confirmation"], arity = 1, description = "Enables or disables the confirmation before deleting any file permanently.")
    var confirmation: Boolean? = null

    @Parameter(names = ["-h", "--help", "help", "/?"], help = true, description = "Shows this help.")
    var help = false

    @Parameter(names = ["--logLevel"], arity = 1, converter = LevelConverter::class, description = "Specifies the application log level for this execution")
    var logLevel: Level = Level.INFO

    override fun toString(): String =
            "fileToDelete: ${fileToDelete.nullSafeToString()}\n" +
                    "allowedExtensions = ${allowedExtensions.nullSafeToString { it.toStringWithElements() }}\n" +
                    "dryRun = ${dryRun.nullSafeToString()}\n" +
                    "skipConfirmationOnce = $skipConfirmationOnce\n" +
                    "confirmation = ${confirmation.nullSafeToString()}\n" +
                    "help = $help\n" +
                    "logLevel = $logLevel"
}

private fun <T : Any> T?.nullSafeToString() = nullSafeToString { it.toString() }
private fun <T : Any> T?.nullSafeToString(toStringIfNotNull: (T) -> String) =
        if (this == null)
            "null"
        else
            toStringIfNotNull(this)

private class LevelConverter : IStringConverter<Level> {
    override fun convert(value: String?): Level = Level.parse(value)
}

private class CustomDryRunBooleanConverter : IStringConverter<Boolean> {
    override fun convert(value: String?): Boolean {
        CommandLineParams.dryRunSpecified = true
        return value?.toBoolean() ?: false
    }
}

private class CustomConfirmationBooleanConverter : IStringConverter<Boolean> {
    override fun convert(value: String?): Boolean {
        CommandLineParams.confirmationSpecified = true
        return value?.toBoolean() ?: false
    }
}

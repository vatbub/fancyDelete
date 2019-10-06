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

import com.github.vatbub.commandlineUserPromptProcessor.parsables.Parsable
import com.github.vatbub.commandlineUserPromptProcessor.parsables.ParseException


class CustomParsableBoolean(var optionsStringKt: String = "Y/N", var defaultValueKt: Boolean? = null) : Parsable<Boolean> {
    private var value: Boolean? = null
    override fun getOptionsString() = optionsStringKt
    override fun getDefaultValue() = defaultValueKt
    override fun setDefaultValue(value: Boolean?) {
        defaultValueKt = value
    }

    override fun getStringForDefaultValue() = defaultValueKt?.toString()

    override fun fromString(string: String) {
        value = when (string.toLowerCase()) {
            "true", "t", "y", "yes" -> true
            "false", "f", "n", "no" -> false
            else -> defaultValue ?: throw ParseException()
        }
    }

    override fun toValue() = value ?: throw NullPointerException("No value has been parsed yet")
}

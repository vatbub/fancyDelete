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

import java.util.*

fun <TIn, TOut> List<TIn>.transform(block: (TIn) -> TOut) =
        List(this.size) { block(this[it]) }

fun <T> Enumeration<T>.asList():List<T>{
    val result = mutableListOf<T>()
    while (this.hasMoreElements())
        result.add(this.nextElement())
    return result
}

fun <T> List<T>.toStringWithElements():String{
    val resultBuilder = StringBuilder()
    this.forEach{resultBuilder.append(it.toString() + ", ")}
    return resultBuilder.toString().removeSuffix(", ")
}

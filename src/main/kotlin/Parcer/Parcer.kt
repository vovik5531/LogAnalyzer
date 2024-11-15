package org.example.Parcer

import org.example.types.ArbitaryLog

interface Parcer<in T: ArbitaryLog>
{
    abstract fun execute(input: T)
    abstract fun formResult()
    val filterFunctions: List<(T)->Boolean>
}
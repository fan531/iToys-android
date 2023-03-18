package com.itoys.env

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.validate
import com.itoys.env.annotation.Env
import com.itoys.env.annotation.Module

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 13/03/2023
 * @desc
 */
internal class IToysEnvSymbolProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        return IToysEnvSymbolProcessor(environment)
    }
}

class IToysEnvSymbolProcessor(
    private val environment: SymbolProcessorEnvironment
) : SymbolProcessor {

    private companion object {
        val MODULE_NAME: String = requireNotNull(Module::class.qualifiedName)
        val ENV_NAME: String = requireNotNull(Env::class.qualifiedName)
    }

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val ret = mutableListOf<KSAnnotated>()

        val symbols = resolver.getSymbolsWithAnnotation(MODULE_NAME)
        symbols.toList().forEach { kSAnnotated ->
            if (!kSAnnotated.validate()) {
                ret.add(kSAnnotated)
            } else {
                kSAnnotated.accept(IToysKspVisitor(environment, resolver.builtIns), Unit)
            }
        }
        return ret.toList()
    }
}
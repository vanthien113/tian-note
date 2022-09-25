package com.vanthien113.tiannote.presentation.base

abstract class BaseMapper<Entity, Model> {
    abstract fun transform(entity: Entity): Model

    fun nullableTransform(entity: Entity?): Model? {
        entity ?: return null
        return transform(entity)
    }

    fun transformList(entities: List<Entity>): List<Model> {
        val modelList = mutableListOf<Model>()
        entities.forEach {
            transform(it)?.let {
                modelList.add(it)
            }
        }
        return modelList
    }

    fun nullableTransformList(entities: List<Entity?>?): List<Model> {
        entities ?: return mutableListOf()
        val modelList = mutableListOf<Model>()
        entities.forEach {
            nullableTransform(it)?.let {
                modelList.add(it)
            }
        }
        return modelList
    }
}

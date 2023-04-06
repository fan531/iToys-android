package com.itoys.kit.tool.repository

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 03/04/2023
 * @desc 工具面板
 */
data class ToolPanelEntity(
    val group: String?,
    val list: List<ToolPanelItemEntity>?,
    val visibility: Boolean?,
)

data class ToolPanelItemEntity(
    val name: String?,
    val desc: String?,
    val link: String?,
    val type: String?,
    val icon: Int?,
    val visibility: Boolean?,
)

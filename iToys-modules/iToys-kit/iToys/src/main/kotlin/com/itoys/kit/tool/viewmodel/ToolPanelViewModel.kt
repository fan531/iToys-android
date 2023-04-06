package com.itoys.kit.tool.viewmodel

import android.content.res.AssetManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itoys.kit.tool.repository.ToolPanelEntity
import com.itoys.kit.tool.repository.ToolPanelItemEntity
import com.itoys.logcat.LogPriority
import com.itoys.logcat.asLog
import com.itoys.logcat.logcat
import com.itoys.network.json.JsonMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.nio.charset.StandardCharsets

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 04/04/2023
 * @desc kit tool panel view model.
 */
class ToolPanelViewModel : ViewModel() {

    private val _toolPanelListData: MutableLiveData<List<ToolPanelEntity>> by lazy {
        MutableLiveData()
    }
    val toolPanelListData: LiveData<List<ToolPanelEntity>> = _toolPanelListData

    /**
     * 工具面板 list
     */
    fun toolPanelList(assetManager: AssetManager) {
        requestWithFlow(
            liveData = _toolPanelListData,
            request = { mapperToolPanel(assetManager) },
        )
    }

    private fun requestWithFlow(
        liveData: MutableLiveData<List<ToolPanelEntity>>? = null,
        request: suspend () -> List<ToolPanelEntity>,
    ) {
        viewModelScope.launch {
            flow {
                emit(request())
            }.flowOn(Dispatchers.IO)
                .onStart { }
                .onEmpty {
                    // 当流完成却没有发出任何元素时回调
                }
                .onCompletion {
                    // 流取消或结束时调用

                }
                .catch { exception ->
                    logcat(LogPriority.ERROR) { exception.asLog() }
                }
                .collect { value ->
                    val toolPanelList = arrayListOf<ToolPanelEntity>()
                    var toolPanel: ToolPanelEntity
                    var toolItemList: ArrayList<ToolPanelItemEntity>
                    var toolItem: ToolPanelItemEntity
                    value.forEach { group ->
                        if (group.visibility == true) {
                            toolItemList = arrayListOf()
                            group.list?.forEach { item ->
                                 if (item.visibility == true) {
                                     toolItem = ToolPanelItemEntity(
                                         item.name,
                                         item.desc,
                                         item.link,
                                         item.type,
                                         item.icon,
                                         item.visibility,
                                     )

                                     toolItemList.add(toolItem)
                                 }
                            }

                            toolPanel = ToolPanelEntity(group.group, toolItemList.toList(), group.visibility)
                            toolPanelList.add(toolPanel)
                        }
                    }

                    liveData?.postValue(toolPanelList.toList())
                }
        }
    }

    /**
     * json string to tool panel list.
     */
    private fun mapperToolPanel(assetManager: AssetManager): List<ToolPanelEntity> {
        val toolPanelsStream = assetManager.open("tool_panels.json")
        val buffer = ByteArray(toolPanelsStream.available())
        toolPanelsStream.read(buffer)
        toolPanelsStream.close()
        return JsonMapper.stringToList(
            String(buffer, StandardCharsets.UTF_8),
            ToolPanelEntity::class.java
        ) ?: emptyList()
    }
}
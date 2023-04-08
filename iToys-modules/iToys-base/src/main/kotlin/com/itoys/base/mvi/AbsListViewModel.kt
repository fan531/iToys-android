package com.itoys.base.mvi

import com.itoys.expansion.then
import com.itoys.network.entity.BaseEntity
import com.itoys.network.entity.PageEntity

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 07/03/2023
 * @desc
 */
abstract class AbsListViewModel<T> : AbsViewModel<IListUIState<T>, ListUIIntent>() {

    private val queryMap: HashMap<String, Any> by lazy { HashMap() }

    /** 当前页下标 */
    open var pageIndex = 1

    /** 每页数据大小 */
    open val pageSize = 20

    override fun createUIState(): IListUIState<T> {
        return IListUIState(ListUIState.EMPTY())
    }

    /**
     * 刷新请求参数
     */
    open fun refresh() {
        pageIndex = 1
        queryMap["limit"] = pageSize
        queryMap["curPage"] = pageIndex
    }

    /**
     * 加载更多请求参数
     */
    open fun loadMore() {
        pageIndex++
        queryMap["curPage"] = pageIndex
    }

    fun launchRequest(
        isRefresh: Boolean = true,
        request: suspend () -> BaseEntity<PageEntity<T>>,
    ) {
        isRefresh.then({ refresh() }, { loadMore() })

        launchRequestWithFlow(
            success = { entity ->
                sendUIState {
                    copy(
                        listUiState = isRefresh.then(
                            { ListUIState.RefreshList(entity?.list) },
                            { ListUIState.LoadMoreList(entity?.list) })
                    )
                }
            },
            request = request,
        )
    }
}
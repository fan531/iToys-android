package com.itoys.views.statelayout

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 13/04/2023
 * @desc Page state.
 */
enum class PageStatus {

    /**
     * Page - 加载中
     */
    LOADING,

    /**
     * Page - 空
     */
    EMPTY,

    /**
     * Page - 错误
     */
    ERROR,

    /**
     * Page - 网络错误
     */
    NET_ERROR,

    /**
     * Page -原始内容
     */
    CONTENT;
}
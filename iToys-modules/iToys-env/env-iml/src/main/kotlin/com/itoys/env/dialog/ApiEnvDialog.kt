package com.itoys.env.dialog

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.itoys.env.entity.EnvConfigEntity
import com.itoys.env.iml.R
import com.itoys.env.iml.databinding.EnvDialogLayoutNetworkBinding
import com.itoys.env.iml.databinding.EnvRecyclerItemNetworkApiBinding
import com.itoys.expansion.doOnClick
import com.itoys.expansion.dp2px
import com.itoys.expansion.invalid
import com.itoys.expansion.isBlank
import com.itoys.expansion.isNotBlank
import com.itoys.expansion.tagName
import com.itoys.expansion.then
import com.itoys.utils.ScreenUtils

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 06/04/2023
 * @desc api 环境 dialog
 */
class ApiEnvDialog : DialogFragment() {

    companion object {
        fun show(fm: FragmentManager, builder: Builder.() -> Unit) {
            val build = Builder(fm).apply(builder).build()
            val dialog = ApiEnvDialog()
            dialog.setBuilder(build)
            dialog.showDialog(fm = build.getFragmentManager())
        }
    }

    private lateinit var builder: Builder

    fun setBuilder(builder: Builder) {
        this.builder = builder
    }

    class Builder(
        private val fm: FragmentManager
    ) {

        private var callback: INetworkApiCallback? = null

        private var apiList: List<EnvConfigEntity>? = null

        private var envAlias: String = ""

        fun getFragmentManager(): FragmentManager {
            return this.fm
        }

        fun setCallback(callback: INetworkApiCallback): Builder {
            this.callback = callback
            return this
        }

        fun setEnvApiList(list: List<EnvConfigEntity>): Builder {
            this.apiList = list
            return this
        }

        fun getApiList(): List<EnvConfigEntity>? {
            return this.apiList
        }

        fun setEnvAlias(alias: String): Builder {
            this.envAlias = alias
            return this
        }

        fun getEnvAlias(): String {
            return this.envAlias
        }

        fun getCallback(): INetworkApiCallback? {
            return this.callback
        }

        fun build(): Builder {
            return this
        }
    }

    private var mBinding: EnvDialogLayoutNetworkBinding? = null

    private val mHorizontalMargin: Int by lazy {
        16.dp2px()
    }

    private val mDialogHeight: Int by lazy {
        (ScreenUtils.getScreenHeight() * 0.65).toInt()
    }

    private var curNetworkAlias: String = ""
    private var curNetworkApi: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = EnvDialogLayoutNetworkBinding.inflate(inflater, container, false)
        initDialog()
        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (builder.getEnvAlias().isNotBlank()) {
            mBinding?.envTvNetwork?.text = builder.getEnvAlias()
        }

        mBinding?.envEtNetworkApi?.addTextChangedListener {
            curNetworkApi = it.toString()
        }

        mBinding?.envBtnOk?.doOnClick {
            builder.getCallback()?.networkApi(curNetworkAlias, curNetworkApi)
            dismiss()
        }

        mBinding?.envRecyclerNetworkApi?.apply {
            linear().setup {
                addType<EnvConfigEntity>(R.layout.env_recycler_item_network_api)

                onClick(R.id.env_ll_network_api) {
                    val clickIem = getModelOrNull<EnvConfigEntity>()
                    curNetworkAlias = clickIem?.alias.invalid()
                    curNetworkApi = clickIem?.url.invalid()
                    mBinding?.envTvNetwork?.text = clickIem?.alias.invalid("自定义")

                    mBinding?.envEtNetworkApi?.visibility = curNetworkApi.isBlank().then(View.VISIBLE, View.GONE)
                }

                onBind {
                    val binding = getBinding<EnvRecyclerItemNetworkApiBinding>()
                    val item = getModelOrNull<EnvConfigEntity>()
                    binding.envTvNetworkApi.text = item?.alias.invalid()
                }
            }.models = builder.getApiList()
        }
    }

    private fun initDialog() {
        dialog?.window?.apply {
            // 设置dialog padding
            decorView.setPadding(mHorizontalMargin, 0, mHorizontalMargin, 0)
            val params = attributes
            params.width = WindowManager.LayoutParams.MATCH_PARENT
            params.height = mDialogHeight
            params.gravity = Gravity.CENTER
            attributes = params
            isCancelable = true
        }
    }

    /**
     * 显示对话框
     */
    fun showDialog(fm: FragmentManager) {
        setStyle(STYLE_NORMAL, R.style.EnvDialog)
        show(fm, javaClass.tagName)
    }

    interface INetworkApiCallback {
        fun networkApi(alias: String, url: String)
    }
}
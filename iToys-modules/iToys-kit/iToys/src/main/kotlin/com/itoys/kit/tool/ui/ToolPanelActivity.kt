package com.itoys.kit.tool.ui

import android.content.Context
import android.content.Intent
import android.content.res.TypedArray
import android.graphics.Color
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.drake.brv.BindingAdapter
import com.drake.brv.utils.divider
import com.drake.brv.utils.grid
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.gyf.immersionbar.ImmersionBar
import com.hjq.bar.OnTitleBarListener
import com.hjq.bar.TitleBar
import com.itoys.expansion.invalid
import com.itoys.kit.IToysKitViewModelFactory
import com.itoys.kit.R
import com.itoys.kit.databinding.KitActivityToolPanelBinding
import com.itoys.kit.databinding.KitRecyclerItemGroupPanelBinding
import com.itoys.kit.databinding.KitRecyclerItemToolPanelBinding
import com.itoys.kit.tool.repository.ToolPanelEntity
import com.itoys.kit.tool.repository.ToolPanelItemEntity
import com.itoys.kit.tool.viewmodel.ToolPanelViewModel

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 01/04/2023
 * @desc 工具面板 activity
 */
class ToolPanelActivity : AppCompatActivity() {

    companion object {
        fun open(context: Context) {
            val bundle = Bundle()
            start(context, bundle)
        }

        private fun start(context: Context, bundle: Bundle) {
            val intent = Intent(context, ToolPanelActivity::class.java)
            intent.putExtras(bundle)
            context.startActivity(intent)
        }
    }

    private var self: AppCompatActivity? = null

    private val icons: TypedArray by lazy {
        resources.obtainTypedArray(R.array.kit_icons)
    }

    private lateinit var mBinding: KitActivityToolPanelBinding

    private val mViewModel by viewModels<ToolPanelViewModel> { IToysKitViewModelFactory }

    private var mToolPanelAdapter: BindingAdapter? = null

    private fun createViewBinding(): KitActivityToolPanelBinding {
        return KitActivityToolPanelBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initImmersionBar()
        self = this
        mBinding = createViewBinding()
        setContentView(mBinding.root)

        initRecyclerView()

        mViewModel.toolPanelList(assets)
        addClickListen()
    }

    private fun initRecyclerView() {
        mBinding.kitRecyclerTool.linear().divider(R.drawable.kit_divider_linear_height_10dp_color_non)
        mToolPanelAdapter = mBinding.kitRecyclerTool.setup {
            addType<ToolPanelEntity>(R.layout.kit_recycler_item_group_panel)

            onBind {
                val groupBinding = getBinding<KitRecyclerItemGroupPanelBinding>()
                val groupItem = getModelOrNull<ToolPanelEntity>()
                groupBinding.kitTvGroupTitle.text = groupItem?.group.invalid()

                groupBinding.kitRecyclerGroupList.grid(spanCount = 4)
                    .setup {
                    addType<ToolPanelItemEntity>(R.layout.kit_recycler_item_tool_panel)
3
                    onClick(R.id.kit_ll_tool) {
                        val clickItem = getModelOrNull<ToolPanelItemEntity>()
                        when (clickItem?.type) {
                            "web" -> {}
                            else -> {
                                self?.let { act ->
                                    ToolPanelUriParse.parseToolPanelUri(act, clickItem?.link)
                                }
                            }
                        }
                    }

                    onBind {
                        val toolBinding = getBinding<KitRecyclerItemToolPanelBinding>()
                        val bindItem = getModelOrNull<ToolPanelItemEntity>()

                        toolBinding.kitTvToolTitle.text = bindItem?.name.invalid()
                        toolBinding.kitIvToolImg.setImageDrawable(icons.getDrawable(bindItem?.icon ?: 0))
                    }
                }.models = groupItem?.list
            }
        }
    }

    private fun addClickListen() {
        mBinding.kitTitleBar.setOnTitleBarListener(object : OnTitleBarListener {
            override fun onLeftClick(titleBar: TitleBar?) {
                finish()
            }
        })

        mViewModel.toolPanelListData.observe(this) {
            mToolPanelAdapter?.models = it
        }
    }

    private fun initImmersionBar() {
        ImmersionBar.with(this)
            .statusBarColorInt(Color.WHITE)
            .fitsSystemWindows(true)
            .statusBarDarkFont(true)
            .navigationBarColorInt(Color.parseColor("#D9000000"), 0.4f)
            .navigationBarDarkIcon(true)
            .keyboardEnable(false)
            .init()
    }
}
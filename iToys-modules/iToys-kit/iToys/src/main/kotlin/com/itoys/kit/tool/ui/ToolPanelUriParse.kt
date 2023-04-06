package com.itoys.kit.tool.ui

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import com.itoys.env.dialog.ApiEnvDialog
import com.itoys.env.iml.IToysEnvApiConfig
import com.itoys.expansion.isBlank
import com.itoys.network.NetworkInitialization

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 05/04/2023
 * @desc tool panel uri parse.
 */
object ToolPanelUriParse {

    fun parseToolPanelUri(activity: AppCompatActivity, link: String?) {
        if (link.isBlank()) return

        val linkUri = Uri.parse(link)

        when (linkUri.path) {
            "/env" -> {
                ApiEnvDialog.show(activity.supportFragmentManager) {
                    setEnvApiList(IToysEnvApiConfig.envConfigList())

                    setCallback(object : ApiEnvDialog.INetworkApiCallback {
                        override fun networkApi(api: String) {
                            NetworkInitialization.initialization(api)
                        }
                    })
                }
            }

            "/developer" -> {
                activity.startActivity(Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS))
            }

            "/settings" -> {

            }
        }
    }
}
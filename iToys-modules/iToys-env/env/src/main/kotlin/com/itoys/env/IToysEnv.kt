package com.itoys.env

import android.app.Application
import com.itoys.network.NetworkInitialization

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 17/03/2023
 * @desc
 */
object IToysEnv {

    fun install(application: Application) {
        NetworkInitialization.initialization()
    }
}
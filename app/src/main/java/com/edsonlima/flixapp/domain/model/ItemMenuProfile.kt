package com.edsonlima.flixapp.domain.model

import com.edsonlima.flixapp.R

data class ItemMenuProfile(
    var text: String,
    var icon: Int,
    var type: Type
) {

    companion object {
        val items = listOf(
            ItemMenuProfile(
                text = "Editar perfil",
                icon = R.drawable.ic_profile,
                type = Type.EDIT
            ),
            ItemMenuProfile(
                text = "Notificações",
                icon = R.drawable.ic_notification,
                type = Type.NOTIFICATION
            ),
            ItemMenuProfile(
                text = "Downloads",
                icon = R.drawable.ic_download_profile,
                type = Type.DOWNLOAD
            ),
            ItemMenuProfile(
                text = "Segurança",
                icon = R.drawable.ic_security,
                type = Type.SECURITY
            ),
            ItemMenuProfile(
                text = "Idioma",
                icon = R.drawable.ic_language,
                type = Type.LANGUAGE
            ),
            ItemMenuProfile(
                text = "Modo escuro",
                icon = R.drawable.ic_dark_mode,
                type = Type.DARK_MODE
            ),
            ItemMenuProfile(
                text = "Ajuda",
                icon = R.drawable.ic_info,
                type = Type.HELP
            ),
            ItemMenuProfile(
                text = "Politica",
                icon = R.drawable.ic_info,
                type = Type.PRIVACY_POLITIC
            ),

            ItemMenuProfile(
                text = "Sair",
                icon = R.drawable.logout,
                type = Type.LOGOUT
            )
        )
    }

}


enum class Type {
    EDIT,
    NOTIFICATION,
    DOWNLOAD,
    SECURITY,
    LANGUAGE,
    DARK_MODE,
    HELP,
    PRIVACY_POLITIC,
    LOGOUT
}
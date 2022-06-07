package br.com.tcc.util

import android.Manifest
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Build
import android.text.Html
import androidx.appcompat.app.AppCompatActivity
import br.com.tcc.R


object CheckReadPermission {
    var status = true
    var REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124

    fun show(activity: AppCompatActivity) {

        try {
            val permissionsNeeded = ArrayList<String>()

            val permissionsList = ArrayList<String>()
            if (!addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE, activity))
                permissionsNeeded.add(activity.resources.getString(R.string.permission_sd_card_escrita))
            if (!addPermission(permissionsList, android.Manifest.permission.READ_EXTERNAL_STORAGE, activity))
                permissionsNeeded.add(activity.resources.getString(R.string.permission_sd_card_leitura))
            if (!addPermission(permissionsList, Manifest.permission.CAMERA, activity))
                permissionsNeeded.add(activity.resources.getString(R.string.permission_camera))
            if (!addPermission(permissionsList, android.Manifest.permission.READ_PHONE_STATE, activity))
                permissionsNeeded.add(activity.resources.getString(R.string.permission_telefone))
            if (!addPermission(permissionsList, android.Manifest.permission.INTERNET, activity))
                permissionsNeeded.add(activity.resources.getString(R.string.permission_internet))

            if (permissionsList.size > 0) {
                if (permissionsNeeded.size > 0) {
                    // Need Rationale
                    val sbPermissao = StringBuilder()
                    sbPermissao.append(activity.resources.getString(R.string.msg_permissao))

                    for (i in permissionsNeeded.indices)
                        sbPermissao.append(String.format("<br>%s", permissionsNeeded[i]))

                    Alerta.show(activity,
                        "Atenção",
                        Html.fromHtml(sbPermissao.toString()).toString(),
                        activity.resources.getString(R.string.btn_ok),
                        DialogInterface.OnClickListener { dialog, which ->
                            if (Build.VERSION.SDK_INT >= 23) {
                                activity.requestPermissions(
                                    permissionsList.toTypedArray(),
                                    REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS
                                )
                            }
                        },
                        false
                    )
                    return
                }

                if (Build.VERSION.SDK_INT >= 23) {
                    activity.requestPermissions(permissionsList.toTypedArray(), REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS)
                }

                return
            }
        } catch (e: Exception) {
        }

        return
    }

    fun addPermission(permissionsList: MutableList<String>, permission: String, activity: AppCompatActivity): Boolean {
        if (Build.VERSION.SDK_INT >= 23) {
            if (activity.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsList.add(permission)
                // Check for Rationale Option
                if (!activity.shouldShowRequestPermissionRationale(permission))
                    return false
            }
        }
        return true
    }

    fun validaPermissao(activity: AppCompatActivity): Boolean {

        val permissionsList = ArrayList<String>()
        addPermission(permissionsList, Manifest.permission.RECORD_AUDIO, activity)
        addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE, activity)
        addPermission(permissionsList, android.Manifest.permission.READ_EXTERNAL_STORAGE, activity)
        addPermission(permissionsList, Manifest.permission.CAMERA, activity)
        addPermission(permissionsList, android.Manifest.permission.ACCESS_FINE_LOCATION, activity)
        addPermission(permissionsList, android.Manifest.permission.READ_PHONE_STATE, activity)
        addPermission(permissionsList, android.Manifest.permission.INTERNET, activity)

        return (permissionsList.size <= 0)

    }

}
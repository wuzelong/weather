/*
 * @Copyright: 2020-2021 www.thread0.com Inc. All rights reserved.
 */
package com.thread0.weather.ui.activity

import android.Manifest
import android.annotation.TargetApi
import android.content.ContentUris
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.thread0.weather.databinding.ActivityHmsBinding
import com.thread0.weather.ui.widget.CircleDot
import kotlinx.android.synthetic.main.activity_hms.*
import top.xuqingquan.base.view.activity.SimpleActivity


/**
 * TODO:Hms界面
 *      人像分割模型包
 *      文档：https://developer.huawei.com/consumer/cn/doc/development/HMSCore-Guides-V5/image-segmentation-0000001050040109-V5#ZH-CN_TOPIC_0000001050990259__section1658976113112
 */
class HmsActivity : SimpleActivity() {

    // view binding
    private lateinit var binding: ActivityHmsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHmsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 设置点击事件
        setClickEvent()
    }
    private fun setClickEvent() {
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
        binding.photoImgIV.setOnClickListener {
            //TODO:点击前往选择图片，选择完成后显示在此
            //权限检查
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),1);
            }
            //打开系统相册
            val albumIntent = Intent(Intent.ACTION_PICK)
            albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
            startActivityForResult(albumIntent, 0x999 )
        }
        binding.redCircleDot.setOnClickListener(object : CircleDot.OnClickListener {
            override fun onClick() {
                //TODO：开始运用HMSCore的抠图能力，抠出人像，并设置背景色为红色后显示到photoImgIV，处理过程需要有处理中的提示
                Toast.makeText(applicationContext, "处理中", Toast.LENGTH_SHORT).show()
            }
        })
        binding.blueCircleDot.setOnClickListener(object : CircleDot.OnClickListener {
            override fun onClick() {
                //TODO：开始运用HMSCore的抠图能力，抠出人像，并设置背景色为蓝色后显示到photoImgIV，处理过程需要有处理中的提示
                Toast.makeText(applicationContext, "处理中", Toast.LENGTH_SHORT).show()
            }
        })
        binding.whiteCircleDot.setOnClickListener(object : CircleDot.OnClickListener {
            override fun onClick() {
                //TODO：开始运用HMSCore的抠图能力，抠出人像，并设置背景色为白色后显示到photoImgIV，处理过程需要有处理中的提示
                Toast.makeText(applicationContext, "处理中", Toast.LENGTH_SHORT).show()
            }
        })
    }

    /**
     * 获取数据
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            handleImageOnKitKat(data)
        }
    }

    /**
     * 获取相册中图片并显示到控件上
     */
    @TargetApi(value = 19)
    private fun handleImageOnKitKat(data: Intent) {
        var imagePath: String? = null
        val uri: Uri? = data.data
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            val docId = DocumentsContract.getDocumentId(uri)
            if (uri != null) {
                if ("com.android.providers.media.documents" == uri.authority) {
                    val id = docId.split(":").toTypedArray()[1]
                    // 解析出数字格式的id
                    val selection = MediaStore.Images.Media._ID + "=" + id
                    imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection)
                } else if ("com.android.providers.downloads.documents" == uri.authority) {
                    val contentUri: Uri = ContentUris.withAppendedId(
                        Uri.parse("content: //downloads/public_downloads"),
                        java.lang.Long.valueOf(docId)
                    )
                    imagePath = getImagePath(contentUri, null)
                }
            }
        } else if (uri != null) {
            if ("content".equals(uri.scheme, ignoreCase = true)) {
                // 如果是content类型的Uri，则使用普通方式处理
                imagePath = getImagePath(uri, null)
            } else if ("file".equals(uri.scheme, ignoreCase = true)) {
                // 如果是file类型的Uri，直接获取图片路径即可
                imagePath = uri.path
            }
        }
        // 根据图片路径显示图片
        if (imagePath != null) {
            val bitmap = BitmapFactory.decodeFile(imagePath)
            photoImgIV.setImageBitmap(bitmap)
        }
        println(imagePath)
    }

    /**
     * 获取图片的路径
     */
    private fun getImagePath(uri: Uri, selection: String?): String? {
        var path: String? = null
        val cursor: Cursor? = contentResolver.query(uri, null, selection, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
            }
            cursor.close()
        }
        return path
    }
}
package com.example.hawk.wallpaper;

import android.annotation.TargetApi;
import android.app.WallpaperManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class Wallpaper extends AppCompatActivity {
    private Button btn;
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        tv = (TextView)findViewById(R.id.text1);
        btn = (Button)findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                //点击事件
                SetLockWallPaper();       //设置锁屏壁纸
//                SetWallPaper();         //设置桌面壁纸
            }
        });
    }

    public void SetWallPaper() {
        WallpaperManager mWallManager = WallpaperManager.getInstance(this);
        try {
            Resources res = Wallpaper.this.getResources();
            Bitmap bitmap = BitmapFactory.decodeResource(res, R.mipmap.ic_launcher);
            mWallManager.setBitmap(bitmap);
            Toast.makeText(Wallpaper.this, "壁纸设置成功", Toast.LENGTH_SHORT)
                    .show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void SetLockWallPaper() {
        // TODO Auto-generated method stub
        WallpaperManager mWallManager = WallpaperManager.getInstance(this);
        try {
            Resources res = Wallpaper.this.getResources();
            Bitmap bitmap = BitmapFactory.decodeResource(res, R.mipmap.ic_launcher);
            mWallManager.setBitmap(bitmap,null,true,WallpaperManager.FLAG_LOCK);
            Toast.makeText(Wallpaper.this, "锁屏壁纸设置成功", Toast.LENGTH_SHORT)
                    .show();

        } catch (Throwable e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}

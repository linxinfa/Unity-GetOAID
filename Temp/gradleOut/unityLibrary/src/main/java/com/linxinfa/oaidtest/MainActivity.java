package com.linxinfa.oaidtest;

import android.os.Bundle;

import com.bun.miitmdid.core.ErrorCode;
import com.bun.miitmdid.core.JLibrary;
import com.bun.miitmdid.core.MdidSdkHelper;
import com.bun.supplier.IIdentifierListener;
import com.bun.supplier.IdSupplier;
import com.unity3d.player.UnityPlayer;
import com.unity3d.player.UnityPlayerActivity;


public class MainActivity extends UnityPlayerActivity{

	private long timeb, timee;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
			JLibrary.InitEntry(MainActivity.this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//初始化
    }

   
    
    public void GetOAID()
    {
    	timeb = System.currentTimeMillis();
    	int errorCode = MdidSdkHelper.InitSdk(MainActivity.this, true, new IIdentifierListener() {
            @Override
            public void OnSupport(boolean b, IdSupplier idSupplier) {
                if (idSupplier == null) {
                    return;
                }
                SendToUnity("OAID=" + idSupplier.getOAID());
                timee = System.currentTimeMillis();
                SendToUnity("耗时：" + (timee - timeb) + "毫秒");
            }
        });

        if (errorCode == ErrorCode.INIT_ERROR_DEVICE_NOSUPPORT) {
        	SendToUnity("获取OAID：" + "不支持的设备");
        } else if (errorCode == ErrorCode.INIT_ERROR_LOAD_CONFIGFILE) {
        	SendToUnity("获取OAID：" + "加载配置文件出错");
        } else if (errorCode == ErrorCode.INIT_ERROR_MANUFACTURER_NOSUPPORT) {
        	SendToUnity("获取OAID：" + "不支持的设备厂商");
        } else if (errorCode == ErrorCode.INIT_ERROR_RESULT_DELAY) {
        	SendToUnity("获取OAID：" + "获取接口是异步的，结果会在回调中返回，回调执行的回调可能在工作线程");
        } else if (errorCode == ErrorCode.INIT_HELPER_CALL_ERROR) {
        	SendToUnity("获取OAID：" + "反射调用出错");
        } else {
        	SendToUnity("获取OAID：" + "获取成功");
        }
    }
    
    
     // 发送消息给Unity
     private void SendToUnity(String msg)
     {
    	 UnityPlayer.UnitySendMessage("Main Camera", "OnJavaMsg", msg);
     }
}


using UnityEngine;
using UnityEngine.UI;

public class Main : MonoBehaviour
{
    public Button getOaidBtn;
    public Text logLbl;
    

    void Start()
    {
        logLbl.text = "";
        // 日志监听
        Application.logMessageReceived += LogCallback;

        // 获取java类对象
        m_jc = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
        m_jo = m_jc.GetStatic<AndroidJavaObject>("currentActivity");
        
        
        //按钮点击事件
        getOaidBtn.onClick.AddListener(() =>
        {
            // 调用java接口
            m_jo.Call("GetOAID");
        });
    }

    // java那边通过UnityPlayer.UnitySendMessage发送过来的消息
    public void OnJavaMsg(string msg)
    {
        Debug.Log(msg);
    }

    // 日志监听回调
    public void LogCallback(string condition, string stackTrace, LogType type)
    {
        logLbl.text += condition + "\n";
    }

    private AndroidJavaClass m_jc;
    private AndroidJavaObject m_jo;
}

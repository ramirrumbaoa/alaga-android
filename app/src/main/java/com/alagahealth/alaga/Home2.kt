package com.alagahealth.alaga


import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment


import io.agora.rtc.IRtcEngineEventHandler
import io.agora.rtc.RtcEngine
import io.agora.rtc.video.VideoCanvas
import io.agora.rtc.video.VideoEncoderConfiguration
import kotlinx.android.synthetic.main.fragment_home_2.view.*


class Home2 : Fragment(){

    //REFERENCE
    //https://github.com/AgoraIO/Basic-Video-Call/blob/master/One-to-One-Video/Agora-Android-Tutorial-Kotlin-1to1/app/src/main/java/io/agora/tutorials1v1vcall/VideoChatViewActivity.kt

    private var mRtcEngine: RtcEngine? = null
    private val mRtcEventHandler = object : IRtcEngineEventHandler() {
        override fun onFirstRemoteVideoDecoded(uid: Int, width: Int, height: Int, elapsed: Int) {
            activity?.runOnUiThread { setupRemoteVideo(uid) }
        }
    }

    private fun initializeAgoraEngine() {
        try {
            mRtcEngine = RtcEngine.create(activity, getString(R.string.agora_app_id), mRtcEventHandler)
        } catch (e: Exception) {
            Log.e("TAG Home2", Log.getStackTraceString(e))
            throw RuntimeException("NEED TO check rtc sdk init fatal error\n" + Log.getStackTraceString(e))
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_home_2, container, false)

//        if (ContextCompat.checkSelfPermission(activity!!, Manifest.permission.WRITE_CALENDAR)
//            != PackageManager.PERMISSION_GRANTED) {
//            // Permission is not granted
//            initAgoraEngineAndJoinChannel(view)
//        }

        return view
    }

    private fun initAgoraEngineAndJoinChannel(view: View) {
        initializeAgoraEngine()
        setupVideoProfile()
        setupLocalVideo(view)
        joinChannel()
    }

    private fun setupRemoteVideo(uid: Int) {
        val container = activity?.findViewById(R.id.remote_video_view_container) as FrameLayout

        if (container.childCount >= 1) {
            return
        }

        val surfaceView = RtcEngine.CreateRendererView(activity)
        container.addView(surfaceView)
        mRtcEngine!!.setupRemoteVideo(VideoCanvas(surfaceView, VideoCanvas.RENDER_MODE_FIT, uid))

        surfaceView.tag = uid // for mark purpose
        val tipMsg = activity?.findViewById<TextView>(R.id.quick_tips_when_use_agora_sdk) // optional UI
        tipMsg?.visibility = View.GONE
    }

    private fun setupLocalVideo(view: View) {
        val surfaceView = RtcEngine.CreateRendererView(activity)
        surfaceView.setZOrderMediaOverlay(true)
        view.local_video_view_container.addView(surfaceView)
        mRtcEngine!!.setupLocalVideo(VideoCanvas(surfaceView, VideoCanvas.RENDER_MODE_FIT, 0))
    }

    private fun joinChannel() {
        var token: String? = getString(R.string.agora_access_token)
        if (token!!.isEmpty()) {
            token = null
        }
        mRtcEngine!!.joinChannel(token, "demoChannel1", "Extra Optional Data", 0) // if you do not specify the uid, we will generate the uid for you
    }

    private fun setupVideoProfile() {
        mRtcEngine!!.enableVideo()
        mRtcEngine!!.setVideoEncoderConfiguration(VideoEncoderConfiguration(VideoEncoderConfiguration.VD_640x360,
            VideoEncoderConfiguration.FRAME_RATE.FRAME_RATE_FPS_15,
            VideoEncoderConfiguration.STANDARD_BITRATE,
            VideoEncoderConfiguration.ORIENTATION_MODE.ORIENTATION_MODE_FIXED_PORTRAIT))
    }

}
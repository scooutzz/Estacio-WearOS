package com.example.missao3;

import android.content.Context;
import android.media.AudioDeviceCallback;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.TextView;
import android.content.Intent;
import android.provider.Settings;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private AudioManager audioManager;
    private TextView audioOutputTextView;
    private AudioHelper audioHelper;
    private MediaPlayer mediaPlayer;

    private boolean isSpeakerAvailable() {
        AudioDeviceInfo[] devices = audioManager.getDevices(AudioManager.GET_DEVICES_OUTPUTS);
        for (AudioDeviceInfo device : devices) {
            if (device.getType() == AudioDeviceInfo.TYPE_BUILTIN_SPEAKER) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioOutputTextView = findViewById(R.id.audio_output_text);
        audioHelper = new AudioHelper(this);

        if (isSpeakerAvailable()) {
            audioOutputTextView.setText("Alto-falante embutido disponível");
            playAudio();
        } else {
            audioOutputTextView.setText("Alto-falante embutido não disponível");
        }

        mediaPlayer = MediaPlayer.create(this, R.raw.boing);

        audioManager.registerAudioDeviceCallback(new AudioDeviceCallback() {

            @Override
            public void onAudioDevicesAdded(AudioDeviceInfo[] addedDevices) {
//                super.onAudioDevicesAdded(addedDevices);

                if (audioHelper.audioOutputAvailable(AudioDeviceInfo.TYPE_BLUETOOTH_A2DP)) {
                    audioOutputTextView.setText("Fone Bluetooth conectado");
                    playAudio();
                }
            }

            @Override
            public void onAudioDevicesRemoved(AudioDeviceInfo[] removedDevices) {
//                super.onAudioDevicesRemoved(removedDevices);

                if (!audioHelper.audioOutputAvailable(AudioDeviceInfo.TYPE_BLUETOOTH_A2DP)) {
                    audioOutputTextView.setText("Fone Bluetooth desconectado");
                    pauseAudio();
                }
            }

        }, null);

        if (!audioHelper.audioOutputAvailable(AudioDeviceInfo.TYPE_BLUETOOTH_A2DP)) {
            openBluetoothSettings();
        }
    }

    private void playAudio() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    private void pauseAudio() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null)  {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private void openBluetoothSettings() {
        Intent intent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("EXTRA_CONNECTION_ONLY", true);
        intent.putExtra("EXTRA_CLOSE_ON_CONNECT", true);
        intent.putExtra("android.bluetooth.devicepicker.extra.FILTER_TYPE", 1);
        startActivity(intent);
    }
}
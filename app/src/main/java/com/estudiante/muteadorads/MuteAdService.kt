package com.estudiante.muteadorads

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.media.AudioManager
import android.content.Context
import android.util.Log

class MuteAdService : NotificationListenerService() {

    private lateinit var audioManager: AudioManager
    private var originalVolume: Int = -1

    override fun onCreate() {
        super.onCreate()
        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        Log.d("MuteAdService", "Servicio de Muteador Antifalsos Iniciado")
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)

        val packageName = sbn?.packageName ?: return
        val extras = sbn.notification.extras

        val title = (extras.getString("android.title") ?: "").trim()
        val text = (extras.getCharSequence("android.text")?.toString() ?: "").trim()
        val subText = (extras.getCharSequence("android.subText")?.toString() ?: "").trim()

        if (packageName.contains("spotify")) {
            Log.d("MuteAdService", "Spotify Info -> Titulo: '$title' | Texto: '$text' | Subtexto: '$subText'")

            // DETECCIÓN REFORZADA:
            // Evaluamos si el texto es EXACTAMENTE igual a "Anuncio" o "Publicidad",
            // o si contiene la estructura clásica de anuncios separados por puntos "•"
            val esAnuncio = title.equals("Anuncio", ignoreCase = true) ||
                    title.equals("Publicidad", ignoreCase = true) ||
                    title.equals("Ad", ignoreCase = true) ||
                    text.equals("Anuncio", ignoreCase = true) ||
                    text.equals("Publicidad", ignoreCase = true) ||
                    text.contains("Anuncio •", ignoreCase = true) ||
                    text.contains("Publicidad •", ignoreCase = true) ||
                    subText.contains("Anuncio", ignoreCase = true)

            if (esAnuncio) {
                mutearSonido(true)
            } else {
                // Si NO es anuncio, y hay datos de una canción, devolvemos el audio inmediatamente
                if (title.isNotEmpty() || text.isNotEmpty() || subText.isNotEmpty()) {
                    mutearSonido(false)
                }
            }
        }
    }

    private fun mutearSonido(mute: Boolean) {
        try {
            if (mute) {
                Log.d("MuteAdService", "¡ANUNCIO REAL DETECTADO! Silenciando...")
                audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_MUTE, 0)

                if (audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) > 0) {
                    if (originalVolume == -1) {
                        originalVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
                    }
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0)
                }
            } else {
                Log.d("MuteAdService", "Canción normal confirmada. Devolviendo audio.")
                audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_UNMUTE, 0)

                if (originalVolume != -1) {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, originalVolume, 0)
                    originalVolume = -1
                }
            }
        } catch (e: Exception) {
            Log.e("MuteAdService", "Error al controlar el volumen: ${e.message}")
        }
    }
}
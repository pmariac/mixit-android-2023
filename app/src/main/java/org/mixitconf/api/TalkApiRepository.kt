package org.mixitconf.api

import org.mixitconf.api.dto.TalkApiDto
import org.mixitconf.service.synchronization.dto.SpeakerApiDto
import retrofit2.Call
import retrofit2.http.GET

interface TalkApiRepository {

    /**
     * Read speaker list for current edition
     */
    @GET("speaker")
    fun speakers(): Call<List<SpeakerApiDto>>

    /**
     * Read talk list for current edition
     */
    @GET("talk")
    fun talks(): Call<List<TalkApiDto>>
}
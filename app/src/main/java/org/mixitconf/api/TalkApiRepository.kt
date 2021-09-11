package org.mixitconf.api

import org.mixitconf.api.dto.TalkApiDto
import org.mixitconf.service.synchronization.dto.SpeakerApiDto
import retrofit2.Response
import retrofit2.http.GET

interface TalkApiRepository {

    /**
     * Read speaker list for current edition
     */
    @GET("speaker")
    suspend fun speakers(): Response<List<SpeakerApiDto>>

    /**
     * Read talk list for current edition
     */
    @GET("talk")
    suspend fun talks(): Response<List<TalkApiDto>>
}
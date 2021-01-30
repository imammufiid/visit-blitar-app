package com.mufiid.visitblitar.api

import com.mufiid.visitblitar.data.source.local.entity.TicketEntity
import com.mufiid.visitblitar.data.source.local.entity.TourismCategoryEntity
import com.mufiid.visitblitar.data.source.local.entity.TourismEntity
import com.mufiid.visitblitar.data.source.local.entity.UserEntity
import com.mufiid.visitblitar.data.source.remote.response.MessageResponse
import com.mufiid.visitblitar.data.source.remote.response.WrappedListResponses
import com.mufiid.visitblitar.data.source.remote.response.WrappedResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.*

interface ApiService {
    // LOGIN
    @FormUrlEncoded
    @POST("auth/login")
    fun login(
        @Field("username") username: String?,
        @Field("password") password: String?
    ): Observable<WrappedResponse<UserEntity>>

    // REGISTRATION
    @FormUrlEncoded
    @POST("auth/register")
    fun register(
        @Field("username") username: String?,
        @Field("firstname") firstName: String?,
        @Field("lastname") lastName: String?,
        @Field("email") email: String?,
        @Field("password") password: String?,
    ): Observable<WrappedResponse<UserEntity>>

    // REGISTRATION
    @FormUrlEncoded
    @POST("reservation/isComing")
    fun isComing(
        @Field("user_id") userId: Int?,
        @Field("code_reservation") codeReservation: String?,
    ): Observable<MessageResponse>

    // GET
    @GET("/tourism/all")
    fun getListOfTouristAttraction(
        @Query("query") query: String? = null,
        @Query("recommended") recommended: Int? = null,
        @Query("random") random: Int? = null
    ): Observable<WrappedListResponses<TourismEntity>>

    // GET
    @GET("/tourism/categories")
    fun getListOfTourismCategory(): Observable<WrappedListResponses<TourismCategoryEntity>>

    // GET
    @GET("/tourism/search")
    fun getListOfTourismBySearch(
        @Query("query") query: String
    ): Observable<WrappedListResponses<TourismEntity>>

    // Add Reservation
    @FormUrlEncoded
    @POST("/reservation/")
    fun addReservation(
        @Field("user_id") userId: Int?,
        @Field("name_visitor") nameVisitor: String?,
        @Field("tourism_id") tourismId: Int?,
        @Field("email") email: String?,
        @Field("date") date: String?,
        @Field("total_visitors") totalVisitors: Int?,
        @Field("nik") nik: String?,
        @Field("no_telp") noTelp: String?
    ): Observable<WrappedResponse<UserEntity>>

    // GET
    @GET("/reservation/ticket/")
    fun getAllMyTicket(
        @Query("user_id") user_id: Int
    ): Observable<WrappedListResponses<TicketEntity>>

    // GET
    @GET("/reservation/ticketById/")
    fun getTicketById(
        @Query("ticket_id") ticketId: Int?
    ): Observable<WrappedResponse<TicketEntity>>

    // GET
    @GET("/reservation/scan/")
    fun scanQr(): Observable<MessageResponse>
}
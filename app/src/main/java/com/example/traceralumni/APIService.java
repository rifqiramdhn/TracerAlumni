package com.example.traceralumni;

import com.example.traceralumni.Notification.MyResponse;
import com.example.traceralumni.Notification.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-type:application/json",
                    "Authorization:key=AAAA6Pl6vVc:APA91bH_jrF9-1e6TyFH1RSdeVkuplvlRsD_iE804qdtmMorHqiNZi-UQK_YF3CQRwYqpEkA6Tg6-OQtIYuitHqCITXOQA78jgAIQwYAJTKCVHl0O8_jVGlWkmHCCSVpLrVtFMzjkW7k"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}

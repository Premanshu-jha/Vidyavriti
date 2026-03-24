package org.example.vidyavriti.Controller.Redis;

import org.example.vidyavriti.Models.Redis.OtpDetails;
import org.example.vidyavriti.Services.Redis.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class OtpController {

    @Autowired
    OtpService otpService;

    @PostMapping("/generate-otp")
    public String generateOtp(@RequestBody OtpDetails otpDetails, @RequestHeader("Authorization") String token){
        otpService.generateOtp(otpDetails,token);
        return "OTP sent to ur email!";
    }

    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestBody OtpDetails reqOtp,@RequestHeader("Authorization") String token){
        otpService.verifyOtp(reqOtp,token);
        return "OTP Verified Succesfully!";
    }
}

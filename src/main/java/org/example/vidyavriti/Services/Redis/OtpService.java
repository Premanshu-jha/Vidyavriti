package org.example.vidyavriti.Services.Redis;

import jakarta.servlet.http.HttpServletRequest;
import org.example.vidyavriti.Models.Redis.OtpDetails;
import org.example.vidyavriti.Models.Users;
import org.example.vidyavriti.Repositories.Redis.OtpRepository;
import org.example.vidyavriti.Repositories.UsersRepository;
import org.example.vidyavriti.Services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class OtpService {

    @Autowired
    OtpRepository otpRepository;

    @Autowired
    JwtService jwtService;

    @Autowired
    UsersRepository usersRepository;

    public String generateOtp(OtpDetails otpDetails,String token){
        String tokenEmail = (String) jwtService.extractAllClaims(token.substring(7)).get("email");
         if(otpDetails.getEmail() == null)
             throw new RuntimeException("Please enter your email!");

         if(!otpDetails.getEmail().equals(tokenEmail))
             throw new RuntimeException("Please enter your registered email!");

         String otp = String.format("%06d",new Random().nextInt(999999));
         otpDetails.setOtpCode(otp);
        otpRepository.save(otpDetails);
         return String.format("Your otp is %s",otp);
    }

    public String verifyOtp(OtpDetails reqOtp,String token){
        String tokenEmail = (String) jwtService.extractAllClaims(token.substring(7)).get("email");
         if(reqOtp.getOtpCode() == null || reqOtp.getOtpCode().length() < 6)
             throw new RuntimeException("Please enter a valid otp!");
         String tokenUserName = jwtService.extractUserName(token.substring(7));

         OtpDetails otpDetails = otpRepository.findById(tokenEmail).orElseThrow(()->new RuntimeException("PLease generate otp before verifying!"));
         Users user = usersRepository.findByUserName(tokenUserName).orElseThrow(()->new RuntimeException("The user with the given email doesnt exist!"));

         boolean isVerified = otpDetails.getOtpCode().equals(reqOtp.getOtpCode());
         boolean isUserVerified = user.isVerified();
         if(!isUserVerified){
             user.setVerified(isVerified);
             usersRepository.save(user);
         }
         if(isVerified){
             otpRepository.deleteById(tokenEmail);
             return "Verification Succesfull!";
         }
         return "Verification Failed!";

    }
}

package org.example.vidyavriti.Services.Redis;
import org.example.vidyavriti.Exception.CustomException;
import org.example.vidyavriti.Models.Redis.OtpDetails;
import org.example.vidyavriti.Models.Users;
import org.example.vidyavriti.Repositories.Redis.OtpRepository;
import org.example.vidyavriti.Repositories.UsersRepository;
import org.example.vidyavriti.Services.EmailService;
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

    @Autowired
    EmailService emailService;

    public void generateOtp(OtpDetails otpDetails,String token){
        String tokenEmail = (String) jwtService.extractAllClaims(token.substring(7)).get("email");
         if(otpDetails.getEmail() == null)
             throw new CustomException("Please enter your email!");

         if(!otpDetails.getEmail().equals(tokenEmail))
             throw new CustomException("Please enter your registered email!");

         String otp = String.format("%06d",new Random().nextInt(999999));
         otpDetails.setOtpCode(otp);
        otpRepository.save(otpDetails);
        emailService.sendOtpEmail(otp,tokenEmail);
    }

    public void verifyOtp(OtpDetails reqOtp,String token){
        String tokenEmail = (String) jwtService.extractAllClaims(token.substring(7)).get("email");
         if(reqOtp.getOtpCode() == null || reqOtp.getOtpCode().length() < 6)
             throw new CustomException("Please enter a valid otp!");
         String tokenUserName = jwtService.extractUserName(token.substring(7));

         OtpDetails otpDetails = otpRepository.findById(tokenEmail).orElseThrow(()->new CustomException("PLease generate otp before verifying!"));
         Users user = usersRepository.findByUserName(tokenUserName).orElseThrow(()->new CustomException("The user with the given email doesn't exist!"));

         boolean isVerified = otpDetails.getOtpCode().equals(reqOtp.getOtpCode());
         boolean isUserVerified = user.isVerified();
         if(!isUserVerified){
             user.setVerified(isVerified);
             usersRepository.save(user);
         }
         if(isVerified){
             otpRepository.deleteById(tokenEmail);
             emailService.verificationStatus("Verification Succesfull!",tokenEmail);
         }
         else throw new CustomException("Invalid OTP!");

    }
}

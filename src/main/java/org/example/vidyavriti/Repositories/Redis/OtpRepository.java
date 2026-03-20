package org.example.vidyavriti.Repositories.Redis;

import org.example.vidyavriti.Models.Redis.OtpDetails;
import org.springframework.data.repository.CrudRepository;

public interface OtpRepository extends CrudRepository<OtpDetails,String> {
}

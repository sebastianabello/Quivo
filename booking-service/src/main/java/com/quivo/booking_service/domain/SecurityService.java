package com.quivo.booking_service.domain;

import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    public String getLoginUser() {
        return "user";
    }
}

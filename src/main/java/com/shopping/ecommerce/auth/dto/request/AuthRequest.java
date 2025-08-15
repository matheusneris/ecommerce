package com.shopping.ecommerce.auth.dto.request;

public record AuthRequest(
        String username,
        String password
) {
}

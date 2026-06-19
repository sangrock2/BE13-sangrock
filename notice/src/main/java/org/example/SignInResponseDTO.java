package org.example;

public record SignInResponseDTO(
        boolean status,
        String userId,
        String name
) {
}

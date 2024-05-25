package com.tmi.gateway.dto;

public class AuthorizationCodeRequestDto {
    String clientId;
    String redirectUri;
    String responseType;
    String scope;
    String state;
    String nonce;
}

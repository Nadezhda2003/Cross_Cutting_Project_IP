package com.bazhanova.restApi.responses;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UploadResponse {
    private String fileName;
    private String downloadPath;
}
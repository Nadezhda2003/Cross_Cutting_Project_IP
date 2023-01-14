package com.bazhanova.restApi.responses;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FileUploadResponse {
    private String fileName;
    private String downloadUri;
    private long size;
}
package com.example.springbatchmultifile.configuration;

import java.util.List;

import lombok.Data;

@Data
public class ClientFileConfiguration {
    List<FileConfiguration> fileConfigurations;
    private Integer configProcessCount;
    
    public FileConfiguration getCurrentConfig() {
        return this.fileConfigurations.get(this.getConfigProcessCount());
    }
}

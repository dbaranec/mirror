package com.magic.mirror;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "primary.datasource")
public class PrimaryDBConfiguration extends AbstractDBConfiguration {
}

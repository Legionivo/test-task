package com.github.legionivo.testtask.utils;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigFactory;
import org.aeonbits.owner.Reloadable;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
		"classpath:custom_config.properties",
		"classpath:config.properties"
})
public interface ServerConfig extends Config, Reloadable {

	// properties passed from command line will overwrite properties from files
	ServerConfig BASE_CONFIG = ConfigFactory.create(ServerConfig.class, System.getenv(), System.getProperties());

	// Application URL
	@Key("base.url")
	String baseUrl();

	// Browsers configuration: chrome, firefox, ie
	@Key("selenide.browser")
	String selenideBrowser();

	@Key("selenide.browserSize")
	String selenideBrowserSize();

	@Key("selenide.timeout")
	int selenideTimeout();

	// User credentials
	@Key("standard.user")
	String standardUserName();

	@Key("password")
	String password();

    //API
    @Key("base.uri")
    String baseUri();

    @Key("base.path")
    String basePath();

}

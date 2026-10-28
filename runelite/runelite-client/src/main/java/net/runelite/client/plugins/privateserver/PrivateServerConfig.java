package net.runelite.client.plugins.privateserver;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("privateserver")

public interface PrivateServerConfig extends Config
{

	@ConfigItem(
		keyName = "codebase",
		name = "URL (codebase)",
		description = "IP address or URL of RSPS you wish to connect to.",
		position = 0
	)
	default String codebase()
	{
		return "http://127.0.0.1";
	}

	@ConfigItem(
		keyName = "modulus",
		name = "Key (modulus)",
		description = "RSA key used by the RSPS you wish to connect to.",
		position = 1
	)
	default String modulus()
	{
		return "114440212436318422537250644804178996725650399594294134034391370383719549098100140178972155870315947951545723882028376820806140341251243356126963128364633270282357918491736871481956866880895096226134740995684598746388518910155954585097693062841901631065168978074760987403362212442320280265243460794104108341573";
	}

}
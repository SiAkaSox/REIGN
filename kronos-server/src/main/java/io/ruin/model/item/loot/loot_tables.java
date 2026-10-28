package io.ruin.model.item.loot;

import io.ruin.utility.JsonUtils;
import lombok.var;

public class loot_tables {

	public static void initialize(String fileName, String json) {
		LootTables.initialize();
		LootTable table = JsonUtils.fromJson(json, LootTable.class);
		var lootTables = LootTables.getTables().get(fileName);
		if (lootTables != null) {
			table.calculateWeight();
			lootTables.setTable(table);
		}
	}
}

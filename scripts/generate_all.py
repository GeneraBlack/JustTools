import os
import json
import sys
from asset_gen_core import *

BASE_RES = "src/main/resources"
ASSETS = f"{BASE_RES}/assets/justtools"
DATA = f"{BASE_RES}/data"

def ensure_dir(path):
    os.makedirs(path, exist_ok=True)

# 1. TEXTURES
item_tex_dir = f"{ASSETS}/textures/item"
block_tex_dir = f"{ASSETS}/textures/block"
gui_tex_dir = f"{ASSETS}/textures/gui/container"
ensure_dir(item_tex_dir)
ensure_dir(block_tex_dir)
ensure_dir(gui_tex_dir)

print("Generating textures...")

# Plates
write_png(f"{item_tex_dir}/hardening_plate.png", 16, 16, draw_plate())
write_png(f"{item_tex_dir}/depth_plate.png", 16, 16, draw_depth_plate())
write_png(f"{item_tex_dir}/echo_plate.png", 16, 16, draw_echo_plate())
write_png(f"{item_tex_dir}/lava_plate.png", 16, 16, draw_lava_plate())
write_png(f"{item_tex_dir}/overclock_plate.png", 16, 16, draw_overclock_plate())
write_png(f"{item_tex_dir}/breeze_plate.png", 16, 16, draw_breeze_plate())
write_png(f"{item_tex_dir}/auto_smelt_plate.png", 16, 16, draw_auto_smelt_plate())
write_png(f"{item_tex_dir}/reinforced_plate.png", 16, 16, draw_reinforced_plate())
write_png(f"{item_tex_dir}/amethyst_plate.png", 16, 16, draw_amethyst_plate())
write_png(f"{item_tex_dir}/moss_plate.png", 16, 16, draw_moss_plate())
write_png(f"{item_tex_dir}/magnetic_plate.png", 16, 16, draw_magnetic_plate())

# Blocks
write_png(f"{block_tex_dir}/hardening_station_top.png", 16, 16, draw_block_top())
write_png(f"{block_tex_dir}/hardening_station_side.png", 16, 16, draw_block_side())
write_png(f"{block_tex_dir}/hardening_station_front.png", 16, 16, draw_block_front())
write_png(f"{block_tex_dir}/hardening_station_bottom.png", 16, 16, draw_block_bottom())

# GUI
write_png(f"{gui_tex_dir}/hardening_station.png", 176, 166, draw_gui())

# Tools
tiers = ["wood", "stone", "copper", "iron", "gold", "diamond", "netherite"]
tier_display_names = {
    "wood": ("Wooden", "Holz"),
    "stone": ("Stone", "Stein"),
    "copper": ("Copper", "Kupfer"),
    "iron": ("Iron", "Eisen"),
    "gold": ("Golden", "Gold"),
    "diamond": ("Diamond", "Diamant"),
    "netherite": ("Netherite", "Netherit"),
}

tools = [
    ("sword", draw_sword, ("Sword", "Schwert")),
    ("shovel", draw_shovel, ("Shovel", "Schaufel")),
    ("pickaxe", draw_pickaxe, ("Pickaxe", "Spitzhacke")),
    ("axe", draw_axe, ("Axe", "Axt")),
    ("hoe", draw_hoe, ("Hoe", "Hacke")),
]

en_lang = {
    "itemGroup.justtools": "Just Tools",
    "container.justtools.hardening_station": "Tool Hardening Station",
    "block.justtools.hardening_station": "Tool Hardening Station",
    "item.justtools.hardening_plate": "Hardening Plate",
    "item.justtools.depth_plate": "Depth Drill Plate",
    "item.justtools.echo_plate": "Soul Repair Core",
    "item.justtools.lava_plate": "Lava Seal Plating",
    "item.justtools.overclock_plate": "Redstone Turbine Plate",
    "item.justtools.breeze_plate": "Wind Core Plate",
    "item.justtools.auto_smelt_plate": "Thermal Core Plate",
    "item.justtools.reinforced_plate": "Reinforced Frame Plate",
    "item.justtools.amethyst_plate": "Amethyst Shield Plate",
    "item.justtools.moss_plate": "Photosynthesis Plate",
    "item.justtools.magnetic_plate": "Magnetic Vacuum Plate",
    "item.justtools.hardening_plate.desc": "Upgrades standard tools to Hardened variants (2.5x-4x durability) or fully repairs them.",
    "item.justtools.depth_plate.desc": "Expands 3x3 mining to 3x3x2 depth for instant tunnel & mine shaft excavation.",
    "item.justtools.echo_plate.desc": "Passively repairs 1 durability every 5s and converts collected XP directly into tool repairs.",
    "item.justtools.lava_plate.desc": "Makes tool completely fireproof and floats on lava without burning.",
    "item.justtools.overclock_plate.desc": "Grants +35% permanent mining speed and temporary Haste boost on continuous mining streaks.",
    "item.justtools.breeze_plate.desc": "Negates all mining speed penalties while underwater or airborne/flying.",
    "item.justtools.auto_smelt_plate.desc": "Directly smelts mined ores, sand, clay, and logs into ingots, glass, and bricks.",
    "item.justtools.reinforced_plate.desc": "Grants a 50% chance to consume zero durability when mining blocks.",
    "item.justtools.amethyst_plate.desc": "Prevents tool from ever breaking (stops at 1 durability) and eliminates wrong-block damage penalties.",
    "item.justtools.moss_plate.desc": "Passively regenerates durability in direct sunlight or while standing on moss/grass blocks.",
    "item.justtools.magnetic_plate.desc": "Magnetically pulls all mined block drops directly into the player's inventory.",
    "block.justtools.hardening_station.desc": "Workstation used to upgrade, modify, and repair tools for free without XP costs.",
    "tooltip.justtools.compatible_all": "Compatible with: All Tools",
    "tooltip.justtools.compatible_hammers_excavators": "Compatible with: Hammers & Excavators only",
    "tooltip.justtools.hammer_desc": "Mines blocks in a 3x3 area",
    "tooltip.justtools.excavator_desc": "Digs shovel blocks in a 3x3 area",
    "tooltip.justtools.paxel_desc": "All-in-one: Pickaxe, Axe, Shovel & Hoe",
    "tooltip.justtools.depth_upgrade": "🚜 Depth Drill: 3x3x2 Mining",
    "tooltip.justtools.auto_repair": "💚 Soul Repair: Passive Auto-Repair & XP Mending",
    "tooltip.justtools.lava_proof": "🌋 Lava-Sealed: Fireproof & floats in lava",
    "tooltip.justtools.overclock": "⚡ Redstone Turbine: +35% Mining Speed & Streak Boost",
    "tooltip.justtools.breeze_charge": "💨 Wind Core: Full Mining Speed in Water & Air",
    "tooltip.justtools.auto_smelt": "🔥 Thermal Core: Automatically Smelts Mined Drops",
    "tooltip.justtools.reinforced": "💎 Reinforced Frame: 50% Chance of Zero Durability Loss",
    "tooltip.justtools.amethyst_shield": "💠 Amethyst Shield: Prevents Tool Breakage & Absorbs Shocks",
    "tooltip.justtools.photosynthesis": "🌿 Photosynthesis: Continuous Sunlight Repair",
    "tooltip.justtools.magnetic": "🧲 Magnetic Core: Attracts Mined Drops Directly to Inventory",
    "message.justtools.break_prevented": "💠 Amethyst Shield prevented your tool from breaking!",
    "tag.item.justtools.hammers": "Hammers",
    "tag.item.justtools.excavators": "Excavators",
    "tag.item.justtools.paxels": "Paxels",
    "tag.item.justtools.hardened_tools": "Hardened Tools"
}

de_lang = {
    "itemGroup.justtools": "Just Tools",
    "container.justtools.hardening_station": "Werkzeug-Härtungsstation",
    "block.justtools.hardening_station": "Werkzeug-Härtungsstation",
    "item.justtools.hardening_plate": "Härtungsplatte",
    "item.justtools.depth_plate": "Tiefenbohrer-Platte",
    "item.justtools.echo_plate": "Seelen-Reparaturkern",
    "item.justtools.lava_plate": "Lava-Versiegelung",
    "item.justtools.overclock_plate": "Redstone-Turbinen-Platte",
    "item.justtools.breeze_plate": "Windstoß-Platte",
    "item.justtools.auto_smelt_plate": "Thermo-Schmelzplatte",
    "item.justtools.reinforced_plate": "Verstärkte Rahmenplatte",
    "item.justtools.amethyst_plate": "Amethyst-Schildplatte",
    "item.justtools.moss_plate": "Photosynthese-Platte",
    "item.justtools.magnetic_plate": "Magnetit-Vakuumplatte",
    "item.justtools.hardening_plate.desc": "Wertet Basis-Werkzeuge zu gehärteten Versionen auf (2.5x-4x Haltbarkeit) oder repariert voll.",
    "item.justtools.depth_plate.desc": "Erweitert den 3x3-Abbau auf 3x3x2 Blöcke Tiefe für blitzschnelles Graben von Tunneln.",
    "item.justtools.echo_plate.desc": "Repariert 1 Haltbarkeit alle 5s passiv und wandelt gesammelte XP direkt in Reparatur um.",
    "item.justtools.lava_plate.desc": "Macht das Werkzeug komplett feuerfest. Schwimmt auf Lava ohne zu verbrennen.",
    "item.justtools.overclock_plate.desc": "+35% permanente Abbaugeschwindigkeit und Eile-Bonus bei kontinuierlichem Abbau.",
    "item.justtools.breeze_plate.desc": "Hebt alle Abbaudrosselungen unter Wasser oder schwebend/fliegend in der Luft auf.",
    "item.justtools.auto_smelt_plate.desc": "Schmilzt abgebaute Erze, Sand, Ton und Stämme sofort automatisch ein.",
    "item.justtools.reinforced_plate.desc": "50% Chance, beim Blockabbau überhaupt keine Haltbarkeit zu verbrauchen.",
    "item.justtools.amethyst_plate.desc": "Verhindert Werkzeugbruch (stoppt bei 1 Haltbarkeit) und dämpft Fehlschlag-Abnutzung.",
    "item.justtools.moss_plate.desc": "Regeneriert Haltbarkeit tagsüber im Sonnenlicht oder auf Moos-/Grasblöcken.",
    "item.justtools.magnetic_plate.desc": "Zieht alle abgebauten Drops sofort magnetisch direkt ins Spielerinventar.",
    "block.justtools.hardening_station.desc": "Werkbank zum Härten, Veredeln und kostenlosen Reparieren von Werkzeugen ohne XP.",
    "tooltip.justtools.compatible_all": "Kompatibel mit: Allen Werkzeugen",
    "tooltip.justtools.compatible_hammers_excavators": "Kompatibel mit: Nur Hämmern & Großschaufeln",
    "tooltip.justtools.hammer_desc": "Baut Blöcke in einem 3x3-Bereich ab",
    "tooltip.justtools.excavator_desc": "Gräbt Schaufel-Blöcke in einem 3x3-Bereich aus",
    "tooltip.justtools.paxel_desc": "Alleskönner: Spitzhacke, Axt, Schaufel & Hacke in einem",
    "tooltip.justtools.depth_upgrade": "🚜 Tiefenbohrer: 3x3x2 Abbau",
    "tooltip.justtools.auto_repair": "💚 Seelen-Reparatur: Automatische Reparatur & XP-Mending",
    "tooltip.justtools.lava_proof": "🌋 Lava-Versiegelt: Feuerfest & schwimmt auf Lava",
    "tooltip.justtools.overclock": "⚡ Redstone-Turbine: +35% Abbaugeschwindigkeit & Minen-Rausch",
    "tooltip.justtools.breeze_charge": "💨 Windstoß-Kern: Volle Abbaugeschwindigkeit in Wasser & Luft",
    "tooltip.justtools.auto_smelt": "🔥 Thermo-Kern: Schmilzt Erze & Blöcke sofort beim Abbau",
    "tooltip.justtools.reinforced": "💎 Verstärkter Rahmen: 50% Chance auf 0 Haltbarkeitsverlust",
    "tooltip.justtools.amethyst_shield": "💠 Amethyst-Schutz: Verhindert Werkzeugbruch & dämpft Abnutzung",
    "tooltip.justtools.photosynthesis": "🌿 Photosynthese: Kontinuierliche Reparatur im Sonnenlicht",
    "tooltip.justtools.magnetic": "🧲 Magnet-Kern: Zieht alle Drops sofort ins Inventar",
    "message.justtools.break_prevented": "💠 Der Amethyst-Schutz hat verhindert, dass dein Werkzeug zerbricht!",
    "tag.item.justtools.hammers": "Hämmer",
    "tag.item.justtools.excavators": "Großschaufeln",
    "tag.item.justtools.paxels": "Paxels",
    "tag.item.justtools.hardened_tools": "Gehärtete Werkzeuge"
}

# Copper tools
for tool_name, draw_fn, (en_tool, de_tool) in tools:
    item_id = f"copper_{tool_name}"
    write_png(f"{item_tex_dir}/{item_id}.png", 16, 16, draw_fn(PALETTES["copper"], False))
    en_lang[f"item.justtools.{item_id}"] = f"Copper {en_tool}"
    de_lang[f"item.justtools.{item_id}"] = f"Kupfer{de_tool}"

# Hammers (Base & Hardened)
for tier in tiers:
    en_tier, de_tier = tier_display_names[tier]
    # Base hammer
    item_id = f"{tier}_hammer" if tier != "wood" and tier != "gold" else ("wooden_hammer" if tier == "wood" else "golden_hammer")
    write_png(f"{item_tex_dir}/{item_id}.png", 16, 16, draw_hammer(PALETTES[tier], False))
    en_lang[f"item.justtools.{item_id}"] = f"{en_tier} Hammer"
    de_lang[f"item.justtools.{item_id}"] = f"{de_tier}hammer"

    # Hardened hammer
    h_item_id = f"hardened_{item_id}"
    write_png(f"{item_tex_dir}/{h_item_id}.png", 16, 16, draw_hammer(PALETTES[tier], True))
    en_lang[f"item.justtools.{h_item_id}"] = f"Hardened {en_tier} Hammer"
    de_lang[f"item.justtools.{h_item_id}"] = f"Gehärteter {de_tier}hammer"

# Excavators (Base & Hardened)
for tier in tiers:
    en_tier, de_tier = tier_display_names[tier]
    # Base excavator
    item_id = f"{tier}_excavator" if tier != "wood" and tier != "gold" else ("wooden_excavator" if tier == "wood" else "golden_excavator")
    write_png(f"{item_tex_dir}/{item_id}.png", 16, 16, draw_excavator(PALETTES[tier], False))
    en_lang[f"item.justtools.{item_id}"] = f"{en_tier} Excavator"
    de_lang[f"item.justtools.{item_id}"] = f"{de_tier}-Großschaufel"

    # Hardened excavator
    h_item_id = f"hardened_{item_id}"
    write_png(f"{item_tex_dir}/{h_item_id}.png", 16, 16, draw_excavator(PALETTES[tier], True))
    en_lang[f"item.justtools.{h_item_id}"] = f"Hardened {en_tier} Excavator"
    de_lang[f"item.justtools.{h_item_id}"] = f"Gehärtete {de_tier}-Großschaufel"

# Paxels (Base & Hardened)
for tier in tiers:
    en_tier, de_tier = tier_display_names[tier]
    # Base paxel
    item_id = f"{tier}_paxel" if tier != "wood" and tier != "gold" else ("wooden_paxel" if tier == "wood" else "golden_paxel")
    write_png(f"{item_tex_dir}/{item_id}.png", 16, 16, draw_paxel(PALETTES[tier], False))
    en_lang[f"item.justtools.{item_id}"] = f"{en_tier} Paxel"
    de_lang[f"item.justtools.{item_id}"] = f"{de_tier}paxel"

    # Hardened paxel
    h_item_id = f"hardened_{item_id}"
    write_png(f"{item_tex_dir}/{h_item_id}.png", 16, 16, draw_paxel(PALETTES[tier], True))
    en_lang[f"item.justtools.{h_item_id}"] = f"Hardened {en_tier} Paxel"
    de_lang[f"item.justtools.{h_item_id}"] = f"Gehärteter {de_tier}paxel"

# Hardened Basic Tools (All 7 tiers x 5 tools)
for tier in tiers:
    en_tier, de_tier = tier_display_names[tier]
    tier_prefix = tier if tier != "wood" and tier != "gold" else ("wooden" if tier == "wood" else "golden")
    for tool_name, draw_fn, (en_tool, de_tool) in tools:
        item_id = f"hardened_{tier_prefix}_{tool_name}"
        write_png(f"{item_tex_dir}/{item_id}.png", 16, 16, draw_fn(PALETTES[tier], True))
        en_lang[f"item.justtools.{item_id}"] = f"Hardened {en_tier} {en_tool}"
        de_lang[f"item.justtools.{item_id}"] = f"Gehärtetes {de_tier}{de_tool.lower()}" if de_tool == "Schwert" else f"Gehärtete {de_tier}{de_tool.lower()}"

print("Textures generated successfully!")

# 2. MODELS
item_model_dir = f"{ASSETS}/models/item"
block_model_dir = f"{ASSETS}/models/block"
blockstate_dir = f"{ASSETS}/blockstates"
ensure_dir(item_model_dir)
ensure_dir(block_model_dir)
ensure_dir(blockstate_dir)

# Plate Models
plate_items = [
    "hardening_plate", "depth_plate", "echo_plate", "lava_plate",
    "overclock_plate", "breeze_plate", "auto_smelt_plate", "reinforced_plate",
    "amethyst_plate", "moss_plate", "magnetic_plate"
]
for p in plate_items:
    with open(f"{item_model_dir}/{p}.json", "w") as f:
        json.dump({
            "parent": "minecraft:item/generated",
            "textures": {
                "layer0": f"justtools:item/{p}"
            }
        }, f, indent=2)

# Block Models & Blockstate
with open(f"{block_model_dir}/hardening_station.json", "w") as f:
    json.dump({
        "parent": "minecraft:block/cube",
        "textures": {
            "particle": "justtools:block/hardening_station_side",
            "down": "justtools:block/hardening_station_bottom",
            "up": "justtools:block/hardening_station_top",
            "north": "justtools:block/hardening_station_front",
            "south": "justtools:block/hardening_station_side",
            "east": "justtools:block/hardening_station_side",
            "west": "justtools:block/hardening_station_side"
        }
    }, f, indent=2)

with open(f"{blockstate_dir}/hardening_station.json", "w") as f:
    json.dump({
        "variants": {
            "facing=north": { "model": "justtools:block/hardening_station" },
            "facing=south": { "model": "justtools:block/hardening_station", "y": 180 },
            "facing=west":  { "model": "justtools:block/hardening_station", "y": 270 },
            "facing=east":  { "model": "justtools:block/hardening_station", "y": 90 }
        }
    }, f, indent=2)

with open(f"{item_model_dir}/hardening_station.json", "w") as f:
    json.dump({
        "parent": "justtools:block/hardening_station"
    }, f, indent=2)

# Tool Item Models
for fn in os.listdir(item_tex_dir):
    if fn.endswith(".png") and fn[:-4] not in plate_items:
        item_name = fn[:-4]
        with open(f"{item_model_dir}/{item_name}.json", "w") as f:
            json.dump({
                "parent": "minecraft:item/handheld",
                "textures": {
                    "layer0": f"justtools:item/{item_name}"
                }
            }, f, indent=2)

# 3. LANGUAGES
lang_dir = f"{ASSETS}/lang"
ensure_dir(lang_dir)
with open(f"{lang_dir}/en_us.json", "w", encoding="utf-8") as f:
    json.dump(en_lang, f, indent=2, ensure_ascii=False)
with open(f"{lang_dir}/de_de.json", "w", encoding="utf-8") as f:
    json.dump(de_lang, f, indent=2, ensure_ascii=False)

print("Models & Languages generated successfully!")

# 4. TAGS
just_block_tags = f"{DATA}/justtools/tags/block"
just_item_tags = f"{DATA}/justtools/tags/item"
mc_block_tags = f"{DATA}/minecraft/tags/block"
mc_item_tags = f"{DATA}/minecraft/tags/item"
ensure_dir(f"{just_block_tags}/mineable")
ensure_dir(just_item_tags)
ensure_dir(f"{mc_block_tags}/mineable")
ensure_dir(mc_item_tags)

# Paxel mineable tag
with open(f"{just_block_tags}/mineable/paxel.json", "w") as f:
    json.dump({
        "replace": False,
        "values": [
            "#minecraft:mineable/pickaxe",
            "#minecraft:mineable/axe",
            "#minecraft:mineable/shovel",
            "#minecraft:mineable/hoe"
        ]
    }, f, indent=2)

# Incorrect for copper tool tag
with open(f"{just_block_tags}/incorrect_for_copper_tool.json", "w") as f:
    json.dump({
        "replace": False,
        "values": [
            "#minecraft:incorrect_for_stone_tool"
        ]
    }, f, indent=2)

# Minecraft pickaxe mineable (add hardening_station)
with open(f"{mc_block_tags}/mineable/pickaxe.json", "w") as f:
    json.dump({
        "replace": False,
        "values": [
            "justtools:hardening_station"
        ]
    }, f, indent=2)

# Hammers item tag
hammer_ids = [f"justtools:{tier}_hammer" if tier not in ["wood", "gold"] else f"justtools:{'wooden' if tier=='wood' else 'golden'}_hammer" for tier in tiers]
hammer_ids += [f"justtools:hardened_{h[10:]}" for h in hammer_ids]
with open(f"{just_item_tags}/hammers.json", "w") as f:
    json.dump({ "replace": False, "values": hammer_ids }, f, indent=2)

# Excavators item tag
excavator_ids = [f"justtools:{tier}_excavator" if tier not in ["wood", "gold"] else f"justtools:{'wooden' if tier=='wood' else 'golden'}_excavator" for tier in tiers]
excavator_ids += [f"justtools:hardened_{e[10:]}" for e in excavator_ids]
with open(f"{just_item_tags}/excavators.json", "w") as f:
    json.dump({ "replace": False, "values": excavator_ids }, f, indent=2)

# Paxels item tag
paxel_ids = [f"justtools:{tier}_paxel" if tier not in ["wood", "gold"] else f"justtools:{'wooden' if tier=='wood' else 'golden'}_paxel" for tier in tiers]
paxel_ids += [f"justtools:hardened_{p[10:]}" for p in paxel_ids]
with open(f"{just_item_tags}/paxels.json", "w") as f:
    json.dump({ "replace": False, "values": paxel_ids }, f, indent=2)

# Hardened tools item tag
all_hardened = [h for h in hammer_ids if "hardened" in h] + [e for e in excavator_ids if "hardened" in e] + [p for p in paxel_ids if "hardened" in p]
for tier in tiers:
    tp = tier if tier not in ["wood", "gold"] else ("wooden" if tier == "wood" else "golden")
    for t, _, _ in tools:
        all_hardened.append(f"justtools:hardened_{tp}_{t}")
with open(f"{just_item_tags}/hardened_tools.json", "w") as f:
    json.dump({ "replace": False, "values": all_hardened }, f, indent=2)

# Vanilla tool item tags
def write_item_tag(filename, items):
    with open(f"{mc_item_tags}/{filename}", "w") as f:
        json.dump({ "replace": False, "values": items }, f, indent=2)

write_item_tag("swords.json", ["justtools:copper_sword"] + [f"justtools:hardened_{tier if tier not in ['wood', 'gold'] else ('wooden' if tier=='wood' else 'golden')}_sword" for tier in tiers])
write_item_tag("shovels.json", ["justtools:copper_shovel"] + [f"justtools:hardened_{tier if tier not in ['wood', 'gold'] else ('wooden' if tier=='wood' else 'golden')}_shovel" for tier in tiers] + excavator_ids + paxel_ids)
write_item_tag("pickaxes.json", ["justtools:copper_pickaxe"] + [f"justtools:hardened_{tier if tier not in ['wood', 'gold'] else ('wooden' if tier=='wood' else 'golden')}_pickaxe" for tier in tiers] + hammer_ids + paxel_ids)
write_item_tag("axes.json", ["justtools:copper_axe"] + [f"justtools:hardened_{tier if tier not in ['wood', 'gold'] else ('wooden' if tier=='wood' else 'golden')}_axe" for tier in tiers] + paxel_ids)
write_item_tag("hoes.json", ["justtools:copper_hoe"] + [f"justtools:hardened_{tier if tier not in ['wood', 'gold'] else ('wooden' if tier=='wood' else 'golden')}_hoe" for tier in tiers])

# 5. RECIPES
recipe_dir = f"{DATA}/justtools/recipe"
ensure_dir(recipe_dir)

# Copper Tools
def write_shaped(name, pattern, key, result, count=1):
    with open(f"{recipe_dir}/{name}.json", "w") as f:
        json.dump({
            "type": "minecraft:crafting_shaped",
            "pattern": pattern,
            "key": key,
            "result": { "id": result, "count": count }
        }, f, indent=2)

def write_smithing(name, template, base, addition, result):
    with open(f"{recipe_dir}/{name}.json", "w") as f:
        json.dump({
            "type": "minecraft:smithing_transform",
            "template": { "item": template },
            "base": { "item": base },
            "addition": { "item": addition },
            "result": { "id": result }
        }, f, indent=2)

write_shaped("copper_sword", ["C", "C", "S"], {"C": {"item": "minecraft:copper_ingot"}, "S": {"item": "minecraft:stick"}}, "justtools:copper_sword")
write_shaped("copper_shovel", ["C", "S", "S"], {"C": {"item": "minecraft:copper_ingot"}, "S": {"item": "minecraft:stick"}}, "justtools:copper_shovel")
write_shaped("copper_pickaxe", ["CCC", " S ", " S "], {"C": {"item": "minecraft:copper_ingot"}, "S": {"item": "minecraft:stick"}}, "justtools:copper_pickaxe")
write_shaped("copper_axe", ["CC", "CS", " S"], {"C": {"item": "minecraft:copper_ingot"}, "S": {"item": "minecraft:stick"}}, "justtools:copper_axe")
write_shaped("copper_hoe", ["CC", " S", " S"], {"C": {"item": "minecraft:copper_ingot"}, "S": {"item": "minecraft:stick"}}, "justtools:copper_hoe")

# Hammers
tier_hammer_materials = {
    "wooden": "minecraft:oak_planks",
    "stone": "minecraft:cobblestone",
    "copper": "minecraft:copper_ingot",
    "iron": "minecraft:iron_ingot",
    "golden": "minecraft:gold_ingot",
    "diamond": "minecraft:diamond",
}
for tier_name, mat in tier_hammer_materials.items():
    write_shaped(f"{tier_name}_hammer", [
        "MMM",
        "MSM",
        " S "
    ], {"M": {"item": mat}, "S": {"item": "minecraft:stick"}}, f"justtools:{tier_name}_hammer")

# Netherite Hammer
write_smithing("netherite_hammer_smithing", "minecraft:netherite_upgrade_smithing_template", "justtools:diamond_hammer", "minecraft:netherite_ingot", "justtools:netherite_hammer")

# Excavators (4 material + 2 sticks)
for tier_name, mat in tier_hammer_materials.items():
    write_shaped(f"{tier_name}_excavator", [
        " M ",
        "MSM",
        " S "
    ], {"M": {"item": mat}, "S": {"item": "minecraft:stick"}}, f"justtools:{tier_name}_excavator")

# Netherite Excavator
write_smithing("netherite_excavator_smithing", "minecraft:netherite_upgrade_smithing_template", "justtools:diamond_excavator", "minecraft:netherite_ingot", "justtools:netherite_excavator")

# Paxels
paxel_recipe_parts = {
    "wooden": ("minecraft:wooden_pickaxe", "minecraft:wooden_axe", "minecraft:wooden_shovel"),
    "stone": ("minecraft:stone_pickaxe", "minecraft:stone_axe", "minecraft:stone_shovel"),
    "copper": ("justtools:copper_pickaxe", "justtools:copper_axe", "justtools:copper_shovel"),
    "iron": ("minecraft:iron_pickaxe", "minecraft:iron_axe", "minecraft:iron_shovel"),
    "golden": ("minecraft:golden_pickaxe", "minecraft:golden_axe", "minecraft:golden_shovel"),
    "diamond": ("minecraft:diamond_pickaxe", "minecraft:diamond_axe", "minecraft:diamond_shovel"),
}
for tier_name, (p, a, s) in paxel_recipe_parts.items():
    write_shaped(f"{tier_name}_paxel", [
        "PAS",
        " # ",
        " # "
    ], {
        "P": {"item": p},
        "A": {"item": a},
        "S": {"item": s},
        "#": {"item": "minecraft:stick"}
    }, f"justtools:{tier_name}_paxel")

# Netherite Paxel
write_smithing("netherite_paxel_smithing", "minecraft:netherite_upgrade_smithing_template", "justtools:diamond_paxel", "minecraft:netherite_ingot", "justtools:netherite_paxel")

# Hardening Plate (Crafting: 4 Obsidian + 4 Iron + 1 Copper Ingot -> 4 Plates)
write_shaped("hardening_plate", [
    "OIO",
    "ICI",
    "OIO"
], {
    "O": {"item": "minecraft:obsidian"},
    "I": {"item": "minecraft:iron_ingot"},
    "C": {"item": "minecraft:copper_ingot"}
}, "justtools:hardening_plate", 4)

# Depth Drill Plate (Crafting: 1 Piston + 4 Iron Ingots + 4 Smooth Stone -> 2 Plates)
write_shaped("depth_plate", [
    "SIS",
    "IPI",
    "SIS"
], {
    "P": {"item": "minecraft:piston"},
    "I": {"item": "minecraft:iron_ingot"},
    "S": {"item": "minecraft:smooth_stone"}
}, "justtools:depth_plate", 2)

# Soul Repair Core (Crafting: 1 Echo Shard + 4 Lapis Lazuli + 4 Diamonds -> 2 Plates)
write_shaped("echo_plate", [
    "DLD",
    "LEL",
    "DLD"
], {
    "E": {"item": "minecraft:echo_shard"},
    "L": {"item": "minecraft:lapis_lazuli"},
    "D": {"item": "minecraft:diamond"}
}, "justtools:echo_plate", 2)

# Lava Seal Plating (Crafting: 4 Crying Obsidian + 4 Magma Blocks + 1 Obsidian -> 2 Plates)
write_shaped("lava_plate", [
    "CMC",
    "MOM",
    "CMC"
], {
    "C": {"item": "minecraft:crying_obsidian"},
    "M": {"item": "minecraft:magma_block"},
    "O": {"item": "minecraft:obsidian"}
}, "justtools:lava_plate", 2)

# Overclock Plate (Crafting: 1 Redstone Block + 4 Gold Ingots + 4 Redstone -> 2 Plates)
write_shaped("overclock_plate", [
    "RGR",
    "GBG",
    "RGR"
], {
    "B": {"item": "minecraft:redstone_block"},
    "G": {"item": "minecraft:gold_ingot"},
    "R": {"item": "minecraft:redstone"}
}, "justtools:overclock_plate", 2)

# Breeze Wind Core Plate (Crafting: 1 Breeze Rod + 4 Iron Ingots + 4 Amethyst Shards -> 2 Plates)
write_shaped("breeze_plate", [
    "IAI",
    "ABA",
    "IAI"
], {
    "B": {"item": "minecraft:breeze_rod"},
    "I": {"item": "minecraft:iron_ingot"},
    "A": {"item": "minecraft:amethyst_shard"}
}, "justtools:breeze_plate", 2)

# Thermal Auto-Smelt Plate (Crafting: 1 Blaze Rod + 4 Magma Cream + 4 Copper Ingots -> 2 Plates)
write_shaped("auto_smelt_plate", [
    "CMC",
    "MBM",
    "CMC"
], {
    "B": {"item": "minecraft:blaze_rod"},
    "M": {"item": "minecraft:magma_cream"},
    "C": {"item": "minecraft:copper_ingot"}
}, "justtools:auto_smelt_plate", 2)

# Reinforced Frame Plate (Crafting: 1 Diamond Block + 4 Iron Ingots + 4 Smooth Stone -> 2 Plates)
write_shaped("reinforced_plate", [
    "SIS",
    "IBI",
    "SIS"
], {
    "B": {"item": "minecraft:diamond_block"},
    "I": {"item": "minecraft:iron_ingot"},
    "S": {"item": "minecraft:smooth_stone"}
}, "justtools:reinforced_plate", 2)

# Amethyst Shield Plate (Crafting: 1 Amethyst Block + 4 Copper Ingots + 4 Quartz -> 2 Plates)
write_shaped("amethyst_plate", [
    "QCQ",
    "CAC",
    "QCQ"
], {
    "A": {"item": "minecraft:amethyst_block"},
    "C": {"item": "minecraft:copper_ingot"},
    "Q": {"item": "minecraft:quartz"}
}, "justtools:amethyst_plate", 2)

# Photosynthesis Moss Plate (Crafting: 1 Moss Block + 4 Emeralds + 4 Sunflowers -> 2 Plates)
write_shaped("moss_plate", [
    "EME",
    "MBM",
    "EME"
], {
    "B": {"item": "minecraft:moss_block"},
    "E": {"item": "minecraft:emerald"},
    "M": {"item": "minecraft:sunflower"}
}, "justtools:moss_plate", 2)

# Magnetic Vacuum Plate (Crafting: 1 Compass + 4 Iron Ingots + 4 Redstone -> 2 Plates)
write_shaped("magnetic_plate", [
    "RIR",
    "ICI",
    "RIR"
], {
    "C": {"item": "minecraft:compass"},
    "I": {"item": "minecraft:iron_ingot"},
    "R": {"item": "minecraft:redstone"}
}, "justtools:magnetic_plate", 2)

# Hardening Station (Crafting: 2 Iron Ingots + 1 Anvil + 3 Deepslate / Smooth Stone)
write_shaped("hardening_station", [
    "IAI",
    "DDD"
], {
    "I": {"item": "minecraft:iron_ingot"},
    "A": {"item": "minecraft:anvil"},
    "D": {"item": "minecraft:deepslate"}
}, "justtools:hardening_station", 1)

print("All assets, tags, recipes, models, and languages generated successfully!")

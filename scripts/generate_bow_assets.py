import os
import json
from PIL import Image

PALETTES = {
    "wood": [
        (40, 30, 11, 255), (73, 54, 21, 255), (104, 78, 30, 255), (137, 103, 39, 255), (170, 130, 55, 255)
    ],
    "copper": [
        (105, 44, 24, 255), (171, 79, 48, 255), (208, 109, 72, 255), (232, 134, 93, 255), (248, 166, 128, 255)
    ],
    "deepslate": [
        (25, 26, 32, 255), (42, 44, 52, 255), (68, 70, 80, 255), (100, 104, 116, 255), (138, 142, 156, 255)
    ],
    "iron": [
        (80, 80, 80, 255), (140, 140, 140, 255), (190, 190, 190, 255), (225, 225, 225, 255), (255, 255, 255, 255)
    ],
    "diamond": [
        (17, 104, 100, 255), (27, 162, 155, 255), (44, 214, 203, 255), (118, 243, 234, 255), (190, 255, 250, 255)
    ],
    "netherite": [
        (25, 20, 22, 255), (42, 35, 38, 255), (70, 60, 64, 255), (92, 81, 86, 255), (125, 115, 120, 255)
    ]
}

HARDENED_TRIM = {
    "dark": (20, 16, 26, 255),
    "mid": (42, 33, 52, 255),
    "light": (75, 58, 92, 255),
    "rivet": (210, 210, 230, 255)
}

BOW_HARDENED_RIVETS = [(8, 7), (13, 2), (2, 13)]
CROSSBOW_HARDENED_RIVETS = [(5, 10), (10, 5), (13, 3), (3, 13)]

TEMPLATE_DIR = "scripts/vanilla_templates"
TEXTURE_OUT = "src/main/resources/assets/justtools/textures/item"
MODEL_OUT = "src/main/resources/assets/justtools/models/item"
RECIPE_OUT = "src/main/resources/data/justtools/recipe"

os.makedirs(TEXTURE_OUT, exist_ok=True)
os.makedirs(MODEL_OUT, exist_ok=True)
os.makedirs(RECIPE_OUT, exist_ok=True)

def recolor_bow(img, palette, hardened=False):
    out = Image.new("RGBA", (16, 16), (0, 0, 0, 0))
    w, h = img.size
    for y in range(h):
        for x in range(w):
            r, g, b, a = img.getpixel((x, y))
            if a == 0:
                continue
            # Check if this is the bow string or arrow feather/tip (neutral grayscale)
            is_grayscale = abs(r - g) <= 3 and abs(g - b) <= 3 and abs(r - b) <= 3
            # Check if this is arrow shaft / flint in pulling frames
            is_arrow = (x in (6, 7, 8, 9, 10) and y in (6, 7, 8, 9, 10)) and is_grayscale
            
            if is_grayscale and not (r < 45 and g < 35):
                # Keep string and arrow feather
                out.putpixel((x, y), (r, g, b, a))
            else:
                # Recolor wood limb
                brightness = (r + g + b) / 3.0
                if brightness < 50:
                    c = palette[0]
                elif brightness < 80:
                    c = palette[1]
                elif brightness < 115:
                    c = palette[2]
                elif brightness < 145:
                    c = palette[3]
                else:
                    c = palette[4]
                out.putpixel((x, y), c)
                
    if hardened:
        # Add obsidian rivets and edge accents
        for rx, ry in BOW_HARDENED_RIVETS:
            if out.getpixel((rx, ry))[3] > 0:
                out.putpixel((rx, ry), HARDENED_TRIM["rivet"])
        # Subtle dark trim near grip
        if out.getpixel((7, 8))[3] > 0:
            out.putpixel((7, 8), HARDENED_TRIM["dark"])
        if out.getpixel((8, 6))[3] > 0:
            out.putpixel((8, 6), HARDENED_TRIM["dark"])
    return out

def recolor_crossbow(img, palette, hardened=False):
    out = Image.new("RGBA", (16, 16), (0, 0, 0, 0))
    w, h = img.size
    for y in range(h):
        for x in range(w):
            r, g, b, a = img.getpixel((x, y))
            if a == 0:
                continue
            
            # String is light-mid gray:
            is_string = (abs(r - g) <= 2 and abs(g - b) <= 2) and (r >= 65 and r <= 160) and (y in (3, 4, 5, 6, 7, 8, 9, 10, 11) and x in (3, 4, 5, 6, 7, 8, 9, 10, 11))
            # Firework rocket colors or arrow fletching in loaded states:
            is_firework = (r > 180 and g < 100) or (g > 180 and r < 100) or (b > 180 and r < 100)
            
            if is_firework:
                out.putpixel((x, y), (r, g, b, a))
            elif is_string and (x + y in (11, 12, 13, 14, 15)):
                out.putpixel((x, y), (r, g, b, a))
            else:
                brightness = (r + g + b) / 3.0
                if brightness < 40:
                    c = palette[0]
                elif brightness < 70:
                    c = palette[1]
                elif brightness < 105:
                    c = palette[2]
                elif brightness < 140:
                    c = palette[3]
                else:
                    c = palette[4]
                out.putpixel((x, y), c)

    if hardened:
        for rx, ry in CROSSBOW_HARDENED_RIVETS:
            if out.getpixel((rx, ry))[3] > 0:
                out.putpixel((rx, ry), HARDENED_TRIM["rivet"])
        # Obsidian dark trim
        if out.getpixel((4, 11))[3] > 0:
            out.putpixel((4, 11), HARDENED_TRIM["dark"])
        if out.getpixel((11, 4))[3] > 0:
            out.putpixel((11, 4), HARDENED_TRIM["dark"])
    return out

print("Processing Bows...")
bow_templates = {
    "standby": Image.open(f"{TEMPLATE_DIR}/bow.png").convert("RGBA"),
    "pulling_0": Image.open(f"{TEMPLATE_DIR}/bow_pulling_0.png").convert("RGBA"),
    "pulling_1": Image.open(f"{TEMPLATE_DIR}/bow_pulling_1.png").convert("RGBA"),
    "pulling_2": Image.open(f"{TEMPLATE_DIR}/bow_pulling_2.png").convert("RGBA")
}

# 1. Hardened Vanilla Bow
for state_key, tpl in bow_templates.items():
    suffix = "" if state_key == "standby" else f"_{state_key}"
    img = recolor_bow(tpl, PALETTES["wood"], hardened=True)
    img.save(f"{TEXTURE_OUT}/hardened_bow{suffix}.png")

# 2. Material Bows (Copper, Deepslate, Iron, Diamond, Netherite) + Hardened
tiers = ["copper", "deepslate", "iron", "diamond", "netherite"]
for tier in tiers:
    for hardened in [False, True]:
        prefix = f"hardened_{tier}" if hardened else tier
        for state_key, tpl in bow_templates.items():
            suffix = "" if state_key == "standby" else f"_{state_key}"
            img = recolor_bow(tpl, PALETTES[tier], hardened=hardened)
            img.save(f"{TEXTURE_OUT}/{prefix}_bow{suffix}.png")

print("Processing Crossbows...")
cb_templates = {
    "standby": Image.open(f"{TEMPLATE_DIR}/crossbow_standby.png").convert("RGBA"),
    "pulling_0": Image.open(f"{TEMPLATE_DIR}/crossbow_pulling_0.png").convert("RGBA"),
    "pulling_1": Image.open(f"{TEMPLATE_DIR}/crossbow_pulling_1.png").convert("RGBA"),
    "pulling_2": Image.open(f"{TEMPLATE_DIR}/crossbow_pulling_2.png").convert("RGBA"),
    "arrow": Image.open(f"{TEMPLATE_DIR}/crossbow_arrow.png").convert("RGBA"),
    "firework": Image.open(f"{TEMPLATE_DIR}/crossbow_firework.png").convert("RGBA")
}

# 1. Hardened Vanilla Crossbow
for state_key, tpl in cb_templates.items():
    img = recolor_crossbow(tpl, PALETTES["iron"], hardened=True)
    img.save(f"{TEXTURE_OUT}/hardened_crossbow_{state_key}.png")

# 2. Material Crossbows + Hardened
for tier in tiers:
    for hardened in [False, True]:
        prefix = f"hardened_{tier}" if hardened else tier
        for state_key, tpl in cb_templates.items():
            img = recolor_crossbow(tpl, PALETTES[tier], hardened=hardened)
            img.save(f"{TEXTURE_OUT}/{prefix}_crossbow_{state_key}.png")

print("Generating Model JSONs...")
BOW_DISPLAY = {
    "thirdperson_righthand": { "rotation": [ -80, 260, -40 ], "translation": [ -1, -2, 2.5 ], "scale": [ 0.9, 0.9, 0.9 ] },
    "thirdperson_lefthand": { "rotation": [ -80, -280, 40 ], "translation": [ -1, -2, 2.5 ], "scale": [ 0.9, 0.9, 0.9 ] },
    "firstperson_righthand": { "rotation": [ 0, -90, 25 ], "translation": [ 1.13, 3.2, 1.13 ], "scale": [ 0.68, 0.68, 0.68 ] },
    "firstperson_lefthand": { "rotation": [ 0, 90, -25 ], "translation": [ 1.13, 3.2, 1.13 ], "scale": [ 0.68, 0.68, 0.68 ] }
}

CROSSBOW_DISPLAY = {
    "thirdperson_righthand": { "rotation": [ -90, 0, -60 ], "translation": [ 2, 0.1, -3 ], "scale": [ 0.9, 0.9, 0.9 ] },
    "thirdperson_lefthand": { "rotation": [ -90, 0, 30 ], "translation": [ 2, 0.1, -3 ], "scale": [ 0.9, 0.9, 0.9 ] },
    "firstperson_righthand": { "rotation": [ -90, 0, -55 ], "translation": [ 1.13, 3.2, 1.13 ], "scale": [ 0.68, 0.68, 0.68 ] },
    "firstperson_lefthand": { "rotation": [ -90, 0, 35 ], "translation": [ 1.13, 3.2, 1.13 ], "scale": [ 0.68, 0.68, 0.68 ] }
}

all_bow_names = ["hardened_bow"] + [f"{t}_bow" for t in tiers] + [f"hardened_{t}_bow" for t in tiers]
for bname in all_bow_names:
    main_model = {
        "parent": "minecraft:item/generated",
        "textures": { "layer0": f"justtools:item/{bname}" },
        "display": BOW_DISPLAY,
        "overrides": [
            { "predicate": { "pulling": 1 }, "model": f"justtools:item/{bname}_pulling_0" },
            { "predicate": { "pulling": 1, "pull": 0.65 }, "model": f"justtools:item/{bname}_pulling_1" },
            { "predicate": { "pulling": 1, "pull": 0.9 }, "model": f"justtools:item/{bname}_pulling_2" }
        ]
    }
    with open(f"{MODEL_OUT}/{bname}.json", "w") as f:
        json.dump(main_model, f, indent=2)

    for p in [0, 1, 2]:
        pmodel = {
            "parent": f"justtools:item/{bname}",
            "textures": { "layer0": f"justtools:item/{bname}_pulling_{p}" }
        }
        with open(f"{MODEL_OUT}/{bname}_pulling_{p}.json", "w") as f:
            json.dump(pmodel, f, indent=2)

all_cb_names = ["hardened_crossbow"] + [f"{t}_crossbow" for t in tiers] + [f"hardened_{t}_crossbow" for t in tiers]
for cbname in all_cb_names:
    main_model = {
        "parent": "minecraft:item/generated",
        "textures": { "layer0": f"justtools:item/{cbname}_standby" },
        "display": CROSSBOW_DISPLAY,
        "overrides": [
            { "predicate": { "pulling": 1 }, "model": f"justtools:item/{cbname}_pulling_0" },
            { "predicate": { "pulling": 1, "pull": 0.58 }, "model": f"justtools:item/{cbname}_pulling_1" },
            { "predicate": { "pulling": 1, "pull": 1.0 }, "model": f"justtools:item/{cbname}_pulling_2" },
            { "predicate": { "charged": 1 }, "model": f"justtools:item/{cbname}_arrow" },
            { "predicate": { "charged": 1, "firework": 1 }, "model": f"justtools:item/{cbname}_firework" }
        ]
    }
    with open(f"{MODEL_OUT}/{cbname}.json", "w") as f:
        json.dump(main_model, f, indent=2)

    for sub in ["pulling_0", "pulling_1", "pulling_2", "arrow", "firework"]:
        submodel = {
            "parent": f"justtools:item/{cbname}",
            "textures": { "layer0": f"justtools:item/{cbname}_{sub}" }
        }
        with open(f"{MODEL_OUT}/{cbname}_{sub}.json", "w") as f:
            json.dump(submodel, f, indent=2)

print("Generating Recipes...")
recipes = {
    "copper_bow": {
        "type": "minecraft:crafting_shaped",
        "pattern": [ " CS", "C S", " CS" ],
        "key": { "C": { "item": "minecraft:copper_ingot" }, "S": { "item": "minecraft:string" } },
        "result": { "id": "justtools:copper_bow", "count": 1 }
    },
    "deepslate_bow": {
        "type": "minecraft:crafting_shaped",
        "pattern": [ " DS", "D S", " DS" ],
        "key": { "D": { "item": "minecraft:cobbled_deepslate" }, "S": { "item": "minecraft:string" } },
        "result": { "id": "justtools:deepslate_bow", "count": 1 }
    },
    "iron_bow": {
        "type": "minecraft:crafting_shaped",
        "pattern": [ " IS", "I S", " IS" ],
        "key": { "I": { "item": "minecraft:iron_ingot" }, "S": { "item": "minecraft:string" } },
        "result": { "id": "justtools:iron_bow", "count": 1 }
    },
    "diamond_bow": {
        "type": "minecraft:crafting_shaped",
        "pattern": [ " DS", "D S", " DS" ],
        "key": { "D": { "item": "minecraft:diamond" }, "S": { "item": "minecraft:string" } },
        "result": { "id": "justtools:diamond_bow", "count": 1 }
    },
    "netherite_bow_smithing": {
        "type": "minecraft:smithing_transform",
        "template": { "item": "minecraft:netherite_upgrade_smithing_template" },
        "base": { "item": "justtools:diamond_bow" },
        "addition": { "item": "minecraft:netherite_ingot" },
        "result": { "id": "justtools:netherite_bow" }
    },
    "copper_crossbow": {
        "type": "minecraft:crafting_shaped",
        "pattern": [ "SCS", "ITI", " S " ],
        "key": {
            "S": { "item": "minecraft:stick" },
            "C": { "item": "minecraft:copper_ingot" },
            "I": { "item": "minecraft:string" },
            "T": { "item": "minecraft:tripwire_hook" }
        },
        "result": { "id": "justtools:copper_crossbow", "count": 1 }
    },
    "deepslate_crossbow": {
        "type": "minecraft:crafting_shaped",
        "pattern": [ "SDS", "ITI", " S " ],
        "key": {
            "S": { "item": "minecraft:stick" },
            "D": { "item": "minecraft:cobbled_deepslate" },
            "I": { "item": "minecraft:string" },
            "T": { "item": "minecraft:tripwire_hook" }
        },
        "result": { "id": "justtools:deepslate_crossbow", "count": 1 }
    },
    "iron_crossbow": {
        "type": "minecraft:crafting_shaped",
        "pattern": [ "SIS", "ITI", " S " ],
        "key": {
            "S": { "item": "minecraft:iron_ingot" },
            "I": { "item": "minecraft:string" },
            "T": { "item": "minecraft:tripwire_hook" }
        },
        "result": { "id": "justtools:iron_crossbow", "count": 1 }
    },
    "diamond_crossbow": {
        "type": "minecraft:crafting_shaped",
        "pattern": [ "SDS", "ITI", " S " ],
        "key": {
            "S": { "item": "minecraft:stick" },
            "D": { "item": "minecraft:diamond" },
            "I": { "item": "minecraft:string" },
            "T": { "item": "minecraft:tripwire_hook" }
        },
        "result": { "id": "justtools:diamond_crossbow", "count": 1 }
    },
    "netherite_crossbow_smithing": {
        "type": "minecraft:smithing_transform",
        "template": { "item": "minecraft:netherite_upgrade_smithing_template" },
        "base": { "item": "justtools:diamond_crossbow" },
        "addition": { "item": "minecraft:netherite_ingot" },
        "result": { "id": "justtools:netherite_crossbow" }
    }
}

for rname, rdata in recipes.items():
    with open(f"{RECIPE_OUT}/{rname}.json", "w") as f:
        json.dump(rdata, f, indent=2)

print("Updating Lang files...")
EN_LANG = "src/main/resources/assets/justtools/lang/en_us.json"
DE_LANG = "src/main/resources/assets/justtools/lang/de_de.json"

new_en = {
    "item.justtools.hardened_bow": "Hardened Bow",
    "item.justtools.copper_bow": "Copper Bow",
    "item.justtools.hardened_copper_bow": "Hardened Copper Bow",
    "item.justtools.deepslate_bow": "Deepslate Bow",
    "item.justtools.hardened_deepslate_bow": "Hardened Deepslate Bow",
    "item.justtools.iron_bow": "Iron Bow",
    "item.justtools.hardened_iron_bow": "Hardened Iron Bow",
    "item.justtools.diamond_bow": "Diamond Bow",
    "item.justtools.hardened_diamond_bow": "Hardened Diamond Bow",
    "item.justtools.netherite_bow": "Netherite Bow",
    "item.justtools.hardened_netherite_bow": "Hardened Netherite Bow",

    "item.justtools.hardened_crossbow": "Hardened Crossbow",
    "item.justtools.copper_crossbow": "Copper Crossbow",
    "item.justtools.hardened_copper_crossbow": "Hardened Copper Crossbow",
    "item.justtools.deepslate_crossbow": "Deepslate Crossbow",
    "item.justtools.hardened_deepslate_crossbow": "Hardened Deepslate Crossbow",
    "item.justtools.iron_crossbow": "Iron Crossbow",
    "item.justtools.hardened_iron_crossbow": "Hardened Iron Crossbow",
    "item.justtools.diamond_crossbow": "Diamond Crossbow",
    "item.justtools.hardened_diamond_crossbow": "Hardened Diamond Crossbow",
    "item.justtools.netherite_crossbow": "Netherite Crossbow",
    "item.justtools.hardened_netherite_crossbow": "Hardened Netherite Crossbow",

    "tooltip.justtools.bow_damage": "Arrow Damage: %s",
    "tooltip.justtools.draw_speed": "Draw Speed: %s",
    "tooltip.justtools.crossbow_damage": "Projectile Damage: %s",
    "tooltip.justtools.reload_speed": "Reload Speed: %s"
}

new_de = {
    "item.justtools.hardened_bow": "Gehärteter Bogen",
    "item.justtools.copper_bow": "Kupferbogen",
    "item.justtools.hardened_copper_bow": "Gehärteter Kupferbogen",
    "item.justtools.deepslate_bow": "Tiefenschieferbogen",
    "item.justtools.hardened_deepslate_bow": "Gehärteter Tiefenschieferbogen",
    "item.justtools.iron_bow": "Eisenbogen",
    "item.justtools.hardened_iron_bow": "Gehärteter Eisenbogen",
    "item.justtools.diamond_bow": "Diamantbogen",
    "item.justtools.hardened_diamond_bow": "Gehärteter Diamantbogen",
    "item.justtools.netherite_bow": "Netheritbogen",
    "item.justtools.hardened_netherite_bow": "Gehärteter Netheritbogen",

    "item.justtools.hardened_crossbow": "Gehärtete Armbrust",
    "item.justtools.copper_crossbow": "Kupferarmbrust",
    "item.justtools.hardened_copper_crossbow": "Gehärtete Kupferarmbrust",
    "item.justtools.deepslate_crossbow": "Tiefenschieferarmbrust",
    "item.justtools.hardened_deepslate_crossbow": "Gehärtete Tiefenschieferarmbrust",
    "item.justtools.iron_crossbow": "Eisenarmbrust",
    "item.justtools.hardened_iron_crossbow": "Gehärtete Eisenarmbrust",
    "item.justtools.diamond_crossbow": "Diamantarmbrust",
    "item.justtools.hardened_diamond_crossbow": "Gehärtete Diamantarmbrust",
    "item.justtools.netherite_crossbow": "Netheritarmbrust",
    "item.justtools.hardened_netherite_crossbow": "Gehärtete Netheritarmbrust",

    "tooltip.justtools.bow_damage": "Pfeilschaden: %s",
    "tooltip.justtools.draw_speed": "Ziehgeschwindigkeit: %s",
    "tooltip.justtools.crossbow_damage": "Projektilschaden: %s",
    "tooltip.justtools.reload_speed": "Nachladetempo: %s"
}

with open(EN_LANG, "r", encoding="utf-8") as f:
    en_data = json.load(f)
en_data.update(new_en)
with open(EN_LANG, "w", encoding="utf-8") as f:
    json.dump(en_data, f, indent=2, ensure_ascii=False)

with open(DE_LANG, "r", encoding="utf-8") as f:
    de_data = json.load(f)
de_data.update(new_de)
with open(DE_LANG, "w", encoding="utf-8") as f:
    json.dump(de_data, f, indent=2, ensure_ascii=False)

print("All assets generated successfully!")

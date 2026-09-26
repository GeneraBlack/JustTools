import os
import zipfile
from PIL import Image

VANILLA_JAR = r"C:\Users\Gener\curseforge\minecraft\Install\versions\1.21.1\1.21.1.jar"
OUTPUT_DIR = r"D:\projekt\JustTools\src\main\resources\assets\justtools\textures\models\armor"

assert os.path.exists(VANILLA_JAR), f"Vanilla JAR not found: {VANILLA_JAR}"
os.makedirs(OUTPUT_DIR, exist_ok=True)

# -------------------------------------------------------------
# Color Palettes
# -------------------------------------------------------------
# Copper ramp mapped from iron luminance [183, 190, 194, 209, 217, 229, 255]
COPPER_MAP = {
    183: (138, 65, 41, 255),
    190: (156, 78, 49, 255),
    194: (193, 90, 54, 255),
    209: (208, 109, 72, 255),
    217: (231, 124, 86, 255),
    229: (252, 153, 130, 255),
    255: (251, 195, 182, 255),
}

# Deepslate ramp mapped from iron luminance
DEEPSLATE_MAP = {
    183: (42, 42, 47, 255),
    190: (53, 53, 57, 255),
    194: (63, 63, 69, 255),
    209: (74, 74, 79, 255),
    217: (90, 90, 95, 255),
    229: (110, 110, 116, 255),
    255: (138, 140, 150, 255),
}

# Hardened obsidian-alloy trim colors
HARDENED_PLATE_DARK = (24, 20, 30, 255)
HARDENED_PLATE_MID = (44, 38, 54, 255)
HARDENED_PLATE_LIGHT = (72, 62, 86, 255)
RIVET_BRIGHT = (220, 225, 238, 255)
RIVET_SHADOW = (20, 18, 24, 255)

def recolor_from_iron(iron_img, color_map):
    """Recolor an iron armor texture to another material using a luminance mapping table."""
    result = Image.new("RGBA", iron_img.size, (0, 0, 0, 0))
    w, h = iron_img.size
    for x in range(w):
        for y in range(h):
            r, g, b, a = iron_img.getpixel((x, y))
            if a == 0:
                continue
            lum = r
            if lum in color_map:
                result.putpixel((x, y), color_map[lum])
            else:
                # Find nearest key
                nearest_k = min(color_map.keys(), key=lambda k: abs(k - lum))
                result.putpixel((x, y), color_map[nearest_k])
    return result

def apply_hardened_layer_1(base_img, accent="obsidian"):
    """
    Apply reinforced obsidian-alloy plates and gleaming rivets to Layer 1
    (Helmet, Chestplate, Boots).
    Only applies pixels where the base armor actually exists (preserving cutouts/openings).
    """
    img = base_img.copy()

    def set_px(x, y, color):
        # Only draw if the base texture has a non-transparent pixel here
        if 0 <= x < img.width and 0 <= y < img.height:
            if base_img.getpixel((x, y))[3] > 0:
                img.putpixel((x, y), color)

    # --- HELMET REINFORCEMENTS ---
    # Forehead / brow reinforcement bar (y=9, x=8..15)
    for x in range(9, 15):
        set_px(x, 9, HARDENED_PLATE_MID)
    set_px(8, 9, HARDENED_PLATE_DARK)
    set_px(15, 9, HARDENED_PLATE_DARK)
    # Brow rivets
    set_px(10, 9, RIVET_BRIGHT)
    set_px(13, 9, RIVET_BRIGHT)

    # Cheekguard trim (x=8, y=10..12 and x=15, y=10..12)
    for y in range(10, 13):
        set_px(8, y, HARDENED_PLATE_DARK)
        set_px(15, y, HARDENED_PLATE_DARK)
    set_px(8, 12, RIVET_BRIGHT)
    set_px(15, 12, RIVET_BRIGHT)

    # Helmet crest (top head center x=11..12, y=2..6)
    for y in range(2, 6):
        set_px(11, y, HARDENED_PLATE_LIGHT)
        set_px(12, y, HARDENED_PLATE_MID)
    set_px(11, 4, RIVET_BRIGHT)

    # --- CHESTPLATE REINFORCEMENTS ---
    # Shoulder pauldrons: top of body (x=20..27, y=16..17)
    for x in [20, 21, 26, 27]:
        set_px(x, 16, HARDENED_PLATE_MID)
        set_px(x, 17, HARDENED_PLATE_DARK)
    set_px(21, 16, RIVET_BRIGHT)
    set_px(26, 16, RIVET_BRIGHT)

    # Shoulder pauldrons: right arm top (x=44..47, y=16..17)
    for x in range(44, 48):
        set_px(x, 16, HARDENED_PLATE_MID)
        set_px(x, 17, HARDENED_PLATE_DARK)
    set_px(45, 16, RIVET_BRIGHT)
    set_px(46, 16, RIVET_BRIGHT)

    # Center chest plate reinforcement: cross-brace / emblem (x=23..24, y=23..26)
    for y in range(23, 27):
        set_px(23, y, HARDENED_PLATE_LIGHT)
        set_px(24, y, HARDENED_PLATE_MID)
    for x in range(21, 27):
        set_px(x, 24, HARDENED_PLATE_MID)
    # Center rivet
    set_px(23, 24, RIVET_BRIGHT)
    set_px(24, 24, RIVET_SHADOW)

    # Collar rivets
    set_px(21, 20, RIVET_BRIGHT)
    set_px(26, 20, RIVET_BRIGHT)

    # Arm cuffs (x=40..55, y=30..31)
    for x in range(40, 56):
        set_px(x, 31, HARDENED_PLATE_DARK)
    for x in [42, 46, 50, 54]:
        set_px(x, 30, HARDENED_PLATE_MID)
        set_px(x, 30, RIVET_BRIGHT)

    # --- BOOTS REINFORCEMENTS ---
    # Reinforced toe cap (front face: x=4..7, y=30..31)
    for x in range(4, 8):
        set_px(x, 31, HARDENED_PLATE_DARK)
        set_px(x, 30, HARDENED_PLATE_MID)
    set_px(5, 30, RIVET_BRIGHT)
    set_px(6, 30, RIVET_BRIGHT)

    # Reinforced outer heel & ankle rim (x=0..3, y=30..31 and x=12..15, y=30..31)
    for x in range(0, 4):
        set_px(x, 31, HARDENED_PLATE_DARK)
    for x in range(12, 16):
        set_px(x, 31, HARDENED_PLATE_DARK)
    set_px(1, 30, RIVET_BRIGHT)
    set_px(14, 30, RIVET_BRIGHT)

    # Ankle cuff top rim (y=26)
    for x in range(0, 16):
        set_px(x, 26, HARDENED_PLATE_MID)

    # Reinforced sole tread (x=8..11, y=16..19)
    for y in range(16, 20):
        set_px(8, y, HARDENED_PLATE_DARK)
        set_px(11, y, HARDENED_PLATE_DARK)
    set_px(9, 17, HARDENED_PLATE_MID)
    set_px(10, 18, HARDENED_PLATE_MID)

    return img

def apply_hardened_layer_2(base_img, accent="obsidian"):
    """
    Apply reinforced obsidian-alloy plates and gleaming rivets to Layer 2
    (Leggings / Pants).
    Only applies pixels where the base armor actually exists.
    """
    img = base_img.copy()

    def set_px(x, y, color):
        if 0 <= x < img.width and 0 <= y < img.height:
            if base_img.getpixel((x, y))[3] > 0:
                img.putpixel((x, y), color)

    # --- BELT / WAISTBAND ---
    # Front waist belt (x=20..27, y=20..21)
    for x in range(20, 28):
        set_px(x, 20, HARDENED_PLATE_MID)
        set_px(x, 21, HARDENED_PLATE_DARK)
    # Heavy belt buckle in center (x=23..24, y=20..22)
    set_px(23, 20, HARDENED_PLATE_LIGHT)
    set_px(24, 20, HARDENED_PLATE_LIGHT)
    set_px(23, 21, RIVET_BRIGHT)
    set_px(24, 21, RIVET_SHADOW)
    set_px(23, 22, HARDENED_PLATE_MID)
    set_px(24, 22, HARDENED_PLATE_MID)

    # Back waist belt (x=32..39, y=20..21)
    for x in range(32, 40):
        set_px(x, 20, HARDENED_PLATE_MID)
        set_px(x, 21, HARDENED_PLATE_DARK)

    # --- KNEE GUARDS ---
    # Right leg knee plate (front: x=4..7, y=25..27)
    for x in range(4, 8):
        set_px(x, 25, HARDENED_PLATE_LIGHT)
        set_px(x, 26, HARDENED_PLATE_MID)
        set_px(x, 27, HARDENED_PLATE_DARK)
    set_px(5, 26, RIVET_BRIGHT)
    set_px(6, 26, RIVET_BRIGHT)

    # Thigh side armor plate (outer leg: x=0..3, y=22..25)
    for y in range(22, 26):
        set_px(1, y, HARDENED_PLATE_MID)
        set_px(2, y, HARDENED_PLATE_DARK)
    set_px(1, 23, RIVET_BRIGHT)

    return img

def main():
    print(f"Opening vanilla jar: {VANILLA_JAR}")
    with zipfile.ZipFile(VANILLA_JAR, "r") as z:
        def load_vanilla(name):
            with z.open(f"assets/minecraft/textures/models/armor/{name}") as f:
                return Image.open(f).convert("RGBA")

        # Load all vanilla textures
        iron_1 = load_vanilla("iron_layer_1.png")
        iron_2 = load_vanilla("iron_layer_2.png")
        gold_1 = load_vanilla("gold_layer_1.png")
        gold_2 = load_vanilla("gold_layer_2.png")
        diamond_1 = load_vanilla("diamond_layer_1.png")
        diamond_2 = load_vanilla("diamond_layer_2.png")
        netherite_1 = load_vanilla("netherite_layer_1.png")
        netherite_2 = load_vanilla("netherite_layer_2.png")
        chainmail_1 = load_vanilla("chainmail_layer_1.png")
        chainmail_2 = load_vanilla("chainmail_layer_2.png")

        # Leather: combine dyeable base tinted with default leather brown (160, 101, 64) with overlay
        leather_base_1 = load_vanilla("leather_layer_1.png")
        leather_overlay_1 = load_vanilla("leather_layer_1_overlay.png")
        leather_1 = Image.new("RGBA", leather_base_1.size, (0, 0, 0, 0))
        for x in range(leather_base_1.width):
            for y in range(leather_base_1.height):
                r, g, b, a = leather_base_1.getpixel((x, y))
                if a > 0:
                    lr = int(r * (160 / 255.0))
                    lg = int(g * (101 / 255.0))
                    lb = int(b * (64 / 255.0))
                    leather_1.putpixel((x, y), (lr, lg, lb, a))
        leather_1.alpha_composite(leather_overlay_1)

        leather_base_2 = load_vanilla("leather_layer_2.png")
        leather_overlay_2 = load_vanilla("leather_layer_2_overlay.png")
        leather_2 = Image.new("RGBA", leather_base_2.size, (0, 0, 0, 0))
        for x in range(leather_base_2.width):
            for y in range(leather_base_2.height):
                r, g, b, a = leather_base_2.getpixel((x, y))
                if a > 0:
                    lr = int(r * (160 / 255.0))
                    lg = int(g * (101 / 255.0))
                    lb = int(b * (64 / 255.0))
                    leather_2.putpixel((x, y), (lr, lg, lb, a))
        leather_2.alpha_composite(leather_overlay_2)

    # 1. Copper Armor
    copper_1 = recolor_from_iron(iron_1, COPPER_MAP)
    copper_2 = recolor_from_iron(iron_2, COPPER_MAP)
    copper_1.save(os.path.join(OUTPUT_DIR, "copper_layer_1.png"))
    copper_2.save(os.path.join(OUTPUT_DIR, "copper_layer_2.png"))
    print("  [OK] copper_layer_1.png, copper_layer_2.png")

    # 2. Deepslate Armor
    deepslate_1 = recolor_from_iron(iron_1, DEEPSLATE_MAP)
    deepslate_2 = recolor_from_iron(iron_2, DEEPSLATE_MAP)
    deepslate_1.save(os.path.join(OUTPUT_DIR, "deepslate_layer_1.png"))
    deepslate_2.save(os.path.join(OUTPUT_DIR, "deepslate_layer_2.png"))
    print("  [OK] deepslate_layer_1.png, deepslate_layer_2.png")

    # 3. Hardened Leather
    h_leather_1 = apply_hardened_layer_1(leather_1)
    h_leather_2 = apply_hardened_layer_2(leather_2)
    h_leather_1.save(os.path.join(OUTPUT_DIR, "hardened_leather_layer_1.png"))
    h_leather_2.save(os.path.join(OUTPUT_DIR, "hardened_leather_layer_2.png"))
    print("  [OK] hardened_leather_layer_1.png, hardened_leather_layer_2.png")

    # 4. Hardened Copper
    h_copper_1 = apply_hardened_layer_1(copper_1)
    h_copper_2 = apply_hardened_layer_2(copper_2)
    h_copper_1.save(os.path.join(OUTPUT_DIR, "hardened_copper_layer_1.png"))
    h_copper_2.save(os.path.join(OUTPUT_DIR, "hardened_copper_layer_2.png"))
    print("  [OK] hardened_copper_layer_1.png, hardened_copper_layer_2.png")

    # 5. Hardened Deepslate
    h_deepslate_1 = apply_hardened_layer_1(deepslate_1)
    h_deepslate_2 = apply_hardened_layer_2(deepslate_2)
    h_deepslate_1.save(os.path.join(OUTPUT_DIR, "hardened_deepslate_layer_1.png"))
    h_deepslate_2.save(os.path.join(OUTPUT_DIR, "hardened_deepslate_layer_2.png"))
    print("  [OK] hardened_deepslate_layer_1.png, hardened_deepslate_layer_2.png")

    # 6. Hardened Chainmail
    h_chainmail_1 = apply_hardened_layer_1(chainmail_1)
    h_chainmail_2 = apply_hardened_layer_2(chainmail_2)
    h_chainmail_1.save(os.path.join(OUTPUT_DIR, "hardened_chainmail_layer_1.png"))
    h_chainmail_2.save(os.path.join(OUTPUT_DIR, "hardened_chainmail_layer_2.png"))
    print("  [OK] hardened_chainmail_layer_1.png, hardened_chainmail_layer_2.png")

    # 7. Hardened Iron
    h_iron_1 = apply_hardened_layer_1(iron_1)
    h_iron_2 = apply_hardened_layer_2(iron_2)
    h_iron_1.save(os.path.join(OUTPUT_DIR, "hardened_iron_layer_1.png"))
    h_iron_2.save(os.path.join(OUTPUT_DIR, "hardened_iron_layer_2.png"))
    print("  [OK] hardened_iron_layer_1.png, hardened_iron_layer_2.png")

    # 8. Hardened Gold (Save BOTH hardened_gold and hardened_golden so both names work)
    h_gold_1 = apply_hardened_layer_1(gold_1)
    h_gold_2 = apply_hardened_layer_2(gold_2)
    h_gold_1.save(os.path.join(OUTPUT_DIR, "hardened_gold_layer_1.png"))
    h_gold_2.save(os.path.join(OUTPUT_DIR, "hardened_gold_layer_2.png"))
    h_gold_1.save(os.path.join(OUTPUT_DIR, "hardened_golden_layer_1.png"))
    h_gold_2.save(os.path.join(OUTPUT_DIR, "hardened_golden_layer_2.png"))
    print("  [OK] hardened_gold_layer_1.png, hardened_golden_layer_1.png, etc.")

    # 9. Hardened Diamond
    h_diamond_1 = apply_hardened_layer_1(diamond_1)
    h_diamond_2 = apply_hardened_layer_2(diamond_2)
    h_diamond_1.save(os.path.join(OUTPUT_DIR, "hardened_diamond_layer_1.png"))
    h_diamond_2.save(os.path.join(OUTPUT_DIR, "hardened_diamond_layer_2.png"))
    print("  [OK] hardened_diamond_layer_1.png, hardened_diamond_layer_2.png")

    # 10. Hardened Netherite
    h_netherite_1 = apply_hardened_layer_1(netherite_1)
    h_netherite_2 = apply_hardened_layer_2(netherite_2)
    h_netherite_1.save(os.path.join(OUTPUT_DIR, "hardened_netherite_layer_1.png"))
    h_netherite_2.save(os.path.join(OUTPUT_DIR, "hardened_netherite_layer_2.png"))
    print("  [OK] hardened_netherite_layer_1.png, hardened_netherite_layer_2.png")

    print("\nAll 22 armor textures generated successfully with authentic Minecraft UV mapping!")

if __name__ == "__main__":
    main()

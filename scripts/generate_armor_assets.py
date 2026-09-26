import os
import struct
import zlib

def write_png(filename, width, height, pixels):
    raw_data = bytearray()
    for y in range(height):
        raw_data.append(0)  # Filter none
        for x in range(width):
            r, g, b, a = pixels[y][x]
            raw_data.extend((r, g, b, a))

    def make_chunk(chunk_type, data):
        return struct.pack(">I", len(data)) + chunk_type + data + struct.pack(">I", zlib.crc32(chunk_type + data) & 0xffffffff)

    header = b'\x89PNG\r\n\x1a\n'
    ihdr = make_chunk(b'IHDR', struct.pack(">IIBBBBB", width, height, 8, 6, 0, 0, 0))
    idat = make_chunk(b'IDAT', zlib.compress(bytes(raw_data)))
    iend = make_chunk(b'IEND', b'')

    os.makedirs(os.path.dirname(filename), exist_ok=True)
    with open(filename, 'wb') as f:
        f.write(header + ihdr + idat + iend)

# Palettes (dark outline [0], shadow [1], midtone [2], light [3], highlight [4])
PALETTES = {
    "leather": [
        (60, 32, 18, 255), (110, 60, 32, 255), (150, 85, 46, 255), (185, 110, 65, 255), (210, 135, 85, 255)
    ],
    "copper": [
        (105, 44, 24, 255), (171, 79, 48, 255), (208, 109, 72, 255), (232, 134, 93, 255), (248, 166, 128, 255)
    ],
    "deepslate": [
        (25, 26, 32, 255), (42, 44, 52, 255), (68, 70, 80, 255), (100, 104, 116, 255), (138, 142, 156, 255)
    ],
    "chainmail": [
        (50, 50, 55, 255), (85, 85, 95, 255), (135, 135, 145, 255), (175, 175, 185, 255), (215, 215, 225, 255)
    ],
    "iron": [
        (80, 80, 80, 255), (160, 160, 160, 255), (216, 216, 216, 255), (238, 238, 238, 255), (255, 255, 255, 255)
    ],
    "gold": [
        (135, 101, 8, 255), (199, 154, 20, 255), (245, 208, 51, 255), (255, 242, 117, 255), (255, 255, 180, 255)
    ],
    "diamond": [
        (17, 104, 100, 255), (27, 162, 155, 255), (44, 214, 203, 255), (118, 243, 234, 255), (190, 255, 250, 255)
    ],
    "netherite": [
        (25, 20, 22, 255), (42, 35, 38, 255), (70, 60, 64, 255), (92, 81, 86, 255), (120, 110, 115, 255)
    ]
}

HARDENED_TRIM = {
    "dark": (20, 16, 26, 255),
    "mid": (42, 33, 52, 255),
    "light": (75, 58, 92, 255),
    "rivet": (210, 210, 230, 255)
}

def empty_grid(w=16, h=16):
    return [[(0, 0, 0, 0) for _ in range(w)] for _ in range(h)]

# --- 16x16 Item Icons ---

def draw_helmet_item(pal, hardened=False):
    g = empty_grid(16, 16)
    o, sh, m, lt, hi = pal

    # Crown
    for x in range(5, 11):
        g[2][x] = o
    for x in range(4, 12):
        g[3][x] = lt if x in [6, 7] else m
    g[3][5] = hi

    for y in range(4, 8):
        g[y][3] = o
        g[y][12] = o
        for x in range(4, 12):
            g[y][x] = lt if (x == 5 and y < 6) else (sh if x >= 9 else m)

    # Face opening & nose guard
    for y in range(8, 12):
        g[y][3] = o
        g[y][4] = m
        g[y][11] = sh
        g[y][12] = o

    # Nose guard
    g[8][7] = lt
    g[8][8] = m
    g[9][7] = m
    g[9][8] = sh

    # Bottom edge of cheekguards
    g[12][3] = o
    g[12][4] = o
    g[12][11] = o
    g[12][12] = o

    if hardened:
        # Obsidian border and rivets on crown & cheeks
        g[2][6] = HARDENED_TRIM["light"]
        g[2][9] = HARDENED_TRIM["light"]
        g[4][3] = HARDENED_TRIM["rivet"]
        g[4][12] = HARDENED_TRIM["rivet"]
        g[10][3] = HARDENED_TRIM["mid"]
        g[10][12] = HARDENED_TRIM["mid"]
        g[3][8] = HARDENED_TRIM["dark"]

    return g

def draw_chestplate_item(pal, hardened=False):
    g = empty_grid(16, 16)
    o, sh, m, lt, hi = pal

    # Shoulders
    for x in [2, 3, 4, 11, 12, 13]:
        g[1][x] = o
    for x in [2, 3, 4]:
        g[2][x] = hi if x == 3 else lt
    for x in [11, 12, 13]:
        g[2][x] = m if x == 11 else sh

    # Collar cutout
    g[2][5] = o
    g[2][10] = o
    for x in range(6, 10):
        g[3][x] = o

    # Breastplate body
    for y in range(3, 13):
        g[y][1] = o
        g[y][14] = o
        for x in range(2, 14):
            if y < 5 and x in range(5, 11):
                continue
            color = lt if x in [4, 5] and y in range(4, 9) else (sh if x >= 10 else m)
            if x == 4 and y == 4:
                color = hi
            g[y][x] = color

    # Pauldron arm cutouts below y=8
    for y in range(8, 14):
        g[y][2] = (0, 0, 0, 0)
        g[y][3] = o
        g[y][13] = (0, 0, 0, 0)
        g[y][12] = o

    # Bottom waistline
    for x in range(4, 12):
        g[13][x] = m if x < 8 else sh
        g[14][x] = o
    g[13][3] = o
    g[13][12] = o

    if hardened:
        # Hardened reinforced trim on collar and chest plate
        for x in range(6, 10):
            g[4][x] = HARDENED_TRIM["light"]
        g[5][7] = HARDENED_TRIM["rivet"]
        g[5][8] = HARDENED_TRIM["rivet"]
        g[2][2] = HARDENED_TRIM["dark"]
        g[2][13] = HARDENED_TRIM["dark"]
        g[12][4] = HARDENED_TRIM["mid"]
        g[12][11] = HARDENED_TRIM["mid"]

    return g

def draw_leggings_item(pal, hardened=False):
    g = empty_grid(16, 16)
    o, sh, m, lt, hi = pal

    # Belt / Waist
    for x in range(4, 12):
        g[1][x] = o
        g[2][x] = hi if x in [5, 6] else (lt if x < 8 else sh)
        g[3][x] = m if x < 8 else sh
    g[2][3] = o
    g[2][12] = o
    g[3][3] = o
    g[3][12] = o

    # Legs
    for y in range(4, 14):
        # Left leg (viewer left)
        g[y][3] = o
        g[y][4] = lt if y < 8 else m
        g[y][5] = m
        g[y][6] = m if y < 9 else sh
        g[y][7] = o

        # Right leg (viewer right)
        g[y][8] = o
        g[y][9] = m
        g[y][10] = sh
        g[y][11] = sh if y < 10 else o
        g[y][12] = o

    # Groin gap opening
    for y in range(8, 14):
        g[y][7] = (0, 0, 0, 0)
        g[y][8] = (0, 0, 0, 0)
        g[y][6] = o
        g[y][9] = o

    # Bottom cuffs
    for x in [4, 5]:
        g[14][x] = o
    for x in [10, 11]:
        g[14][x] = o

    if hardened:
        # Belt buckle / reinforced knee plates
        g[2][7] = HARDENED_TRIM["rivet"]
        g[2][8] = HARDENED_TRIM["rivet"]
        g[7][4] = HARDENED_TRIM["light"]
        g[7][5] = HARDENED_TRIM["mid"]
        g[7][10] = HARDENED_TRIM["light"]
        g[7][11] = HARDENED_TRIM["mid"]
        g[1][4] = HARDENED_TRIM["dark"]
        g[1][11] = HARDENED_TRIM["dark"]

    return g

def draw_boots_item(pal, hardened=False):
    g = empty_grid(16, 16)
    o, sh, m, lt, hi = pal

    # Left boot
    for y in range(5, 11):
        g[y][2] = o
        g[y][3] = lt if y < 8 else m
        g[y][4] = m
        g[y][5] = sh
        g[y][6] = o

    # Left boot foot forward
    for x in range(1, 6):
        g[11][x] = lt if x in [2, 3] else m
        g[12][x] = m if x < 4 else sh
        g[13][x] = o
    g[11][0] = o
    g[12][0] = o
    g[10][1] = o

    # Right boot
    for y in range(5, 11):
        g[y][9] = o
        g[y][10] = m
        g[y][11] = sh
        g[y][12] = sh
        g[y][13] = o

    # Right boot foot forward
    for x in range(8, 13):
        g[11][x] = m if x < 11 else sh
        g[12][x] = sh
        g[13][x] = o
    g[11][7] = o
    g[12][7] = o
    g[10][8] = o

    if hardened:
        # Reinforced toe caps and heel plates
        g[11][1] = HARDENED_TRIM["rivet"]
        g[11][8] = HARDENED_TRIM["rivet"]
        g[6][3] = HARDENED_TRIM["light"]
        g[6][10] = HARDENED_TRIM["light"]
        g[12][5] = HARDENED_TRIM["dark"]
        g[12][12] = HARDENED_TRIM["dark"]

    return g

# --- 64x32 Worn Armor Textures ---

def draw_armor_layer(pal, layer=1, hardened=False):
    # Standard Minecraft armor texture map (64x32)
    g = empty_grid(64, 32)
    o, sh, m, lt, hi = pal

    def fill_box(x0, y0, w, h, fill_col):
        for y in range(y0, y0 + h):
            for x in range(x0, x0 + w):
                g[y][x] = fill_col

    def border_box(x0, y0, w, h, bord_col, fill_col):
        for y in range(y0, y0 + h):
            for x in range(x0, x0 + w):
                if x == x0 or x == x0 + w - 1 or y == y0 or y == y0 + h - 1:
                    g[y][x] = bord_col
                else:
                    g[y][x] = fill_col

    if layer == 1:
        # Layer 1: Helmet (top/sides), Chestplate (body & arms), Boots
        # Helmet Head: (0, 0) to (32, 16)
        border_box(0, 0, 32, 16, o, m)
        fill_box(8, 8, 8, 8, lt)    # Front face
        fill_box(0, 8, 8, 8, m)     # Right face
        fill_box(16, 8, 8, 8, sh)   # Left face
        fill_box(24, 8, 8, 8, o)    # Back face
        fill_box(8, 0, 8, 8, hi)    # Top head

        # Chestplate Body: (16, 16) to (40, 32)
        border_box(16, 16, 24, 16, o, m)
        fill_box(20, 20, 8, 12, lt)  # Front chest
        fill_box(32, 20, 8, 12, sh)  # Back chest

        # Right Arm: (40, 16) to (56, 32)
        border_box(40, 16, 16, 16, o, m)
        fill_box(44, 20, 4, 12, lt)

        # Left Arm: (40, 16) duplicated/flipped or mapped
        border_box(48, 16, 8, 16, o, sh)

        # Boots (Lower legs & feet): (0, 16) to (16, 32)
        border_box(0, 26, 16, 6, o, m)
        fill_box(4, 28, 4, 4, lt)
        fill_box(12, 28, 4, 4, sh)

        if hardened:
            # Add hardened obsidian/reinforced stripes to helmet and chest
            for x in range(8, 16):
                g[4][x] = HARDENED_TRIM["light"]
                g[22][x + 12] = HARDENED_TRIM["light"]
            g[23][23] = HARDENED_TRIM["rivet"]
            g[23][24] = HARDENED_TRIM["rivet"]

    elif layer == 2:
        # Layer 2: Leggings (Pants / waist & upper legs)
        # Waist / Belt: (16, 16) to (40, 32)
        border_box(16, 16, 24, 16, o, m)
        fill_box(20, 20, 8, 8, lt)  # Front pelvis
        fill_box(32, 20, 8, 8, sh)  # Back pelvis

        # Right Leg: (0, 16) to (16, 32)
        border_box(0, 16, 16, 16, o, m)
        fill_box(4, 20, 4, 10, lt)
        fill_box(12, 20, 4, 10, sh)

        if hardened:
            # Knee plate reinforcement
            for x in range(4, 8):
                g[24][x] = HARDENED_TRIM["mid"]
                g[24][x + 8] = HARDENED_TRIM["mid"]
            g[25][5] = HARDENED_TRIM["rivet"]
            g[25][13] = HARDENED_TRIM["rivet"]

    return g

def main():
    base_dir = r"d:\projekt\JustTools\src\main\resources\assets\justtools\textures"
    item_dir = os.path.join(base_dir, "item")
    armor_dir = os.path.join(base_dir, "models", "armor")

    os.makedirs(item_dir, exist_ok=True)
    os.makedirs(armor_dir, exist_ok=True)

    materials = [
        # (name, palette_key, is_hardened, is_base)
        ("copper", "copper", False, True),
        ("deepslate", "deepslate", False, True),
        ("hardened_leather", "leather", True, False),
        ("hardened_copper", "copper", True, False),
        ("hardened_deepslate", "deepslate", True, False),
        ("hardened_chainmail", "chainmail", True, False),
        ("hardened_iron", "iron", True, False),
        ("hardened_golden", "gold", True, False),
        ("hardened_diamond", "diamond", True, False),
        ("hardened_netherite", "netherite", True, False)
    ]

    for name, pal_key, is_hardened, is_base in materials:
        pal = PALETTES[pal_key]
        print(f"Generating assets for {name}...")

        # 1. Item icons (16x16)
        write_png(os.path.join(item_dir, f"{name}_helmet.png"), 16, 16, draw_helmet_item(pal, is_hardened))
        write_png(os.path.join(item_dir, f"{name}_chestplate.png"), 16, 16, draw_chestplate_item(pal, is_hardened))
        write_png(os.path.join(item_dir, f"{name}_leggings.png"), 16, 16, draw_leggings_item(pal, is_hardened))
        write_png(os.path.join(item_dir, f"{name}_boots.png"), 16, 16, draw_boots_item(pal, is_hardened))

    print("All 40 item icons generated.")
    print("Generating 3D armor layer textures with authentic Minecraft UV mapping...")
    import generate_authentic_armor
    generate_authentic_armor.main()

if __name__ == "__main__":
    main()

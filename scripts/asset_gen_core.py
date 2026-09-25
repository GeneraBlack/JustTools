import os
import json
import zlib
import struct

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

# --- PALETTES ---
# (outline, shadow, mid, light, highlight)
PALETTES = {
    "wood": {
        "handle": [(63, 42, 29, 255), (89, 61, 41, 255), (115, 80, 54, 255)],
        "head": [(70, 48, 20, 255), (105, 75, 30, 255), (134, 101, 38, 255), (161, 123, 53, 255), (189, 149, 74, 255)],
    },
    "stone": {
        "handle": [(63, 42, 29, 255), (89, 61, 41, 255), (115, 80, 54, 255)],
        "head": [(48, 48, 48, 255), (84, 84, 84, 255), (122, 122, 122, 255), (153, 153, 153, 255), (180, 180, 180, 255)],
    },
    "deepslate": {
        "handle": [(63, 42, 29, 255), (89, 61, 41, 255), (115, 80, 54, 255)],
        "head": [(25, 26, 32, 255), (42, 44, 52, 255), (68, 70, 80, 255), (100, 104, 116, 255), (138, 142, 156, 255)],
    },
    "copper": {
        "handle": [(63, 42, 29, 255), (89, 61, 41, 255), (115, 80, 54, 255)],
        "head": [(105, 44, 24, 255), (171, 79, 48, 255), (208, 109, 72, 255), (232, 134, 93, 255), (248, 166, 128, 255)],
    },
    "iron": {
        "handle": [(63, 42, 29, 255), (89, 61, 41, 255), (115, 80, 54, 255)],
        "head": [(80, 80, 80, 255), (160, 160, 160, 255), (216, 216, 216, 255), (238, 238, 238, 255), (255, 255, 255, 255)],
    },
    "gold": {
        "handle": [(63, 42, 29, 255), (89, 61, 41, 255), (115, 80, 54, 255)],
        "head": [(135, 101, 8, 255), (199, 154, 20, 255), (245, 208, 51, 255), (255, 242, 117, 255), (255, 255, 180, 255)],
    },
    "diamond": {
        "handle": [(63, 42, 29, 255), (89, 61, 41, 255), (115, 80, 54, 255)],
        "head": [(17, 104, 100, 255), (27, 162, 155, 255), (44, 214, 203, 255), (118, 243, 234, 255), (190, 255, 250, 255)],
    },
    "netherite": {
        "handle": [(31, 26, 28, 255), (49, 41, 45, 255), (70, 60, 64, 255)],
        "head": [(25, 20, 22, 255), (42, 35, 38, 255), (70, 60, 64, 255), (92, 81, 86, 255), (120, 110, 115, 255)],
    },
}

# Obsidian / Hardened reinforcement trim colors
HARDENED_TRIM = {
    "dark": (20, 16, 26, 255),
    "mid": (42, 33, 52, 255),
    "light": (75, 58, 92, 255),
    "rivet": (210, 210, 230, 255),
    "handle_band": (35, 30, 45, 255)
}

def empty_16x16():
    return [[(0, 0, 0, 0) for _ in range(16)] for _ in range(16)]

def draw_stick(grid, palette, hardened=False):
    handle = palette["handle"]
    # Diagonal handle: from (2, 13) to (9, 6)
    coords = [(2, 13), (3, 12), (4, 11), (5, 10), (6, 9), (7, 8), (8, 7), (9, 6)]
    for i, (x, y) in enumerate(coords):
        color = handle[1] if (x + y) % 2 == 0 else handle[2]
        if hardened and i in [2, 5]: # Reinforced metallic bands on handle
            color = HARDENED_TRIM["light"]
        grid[y][x] = color

def draw_sword(palette, hardened=False):
    g = empty_16x16()
    draw_stick(g, palette, hardened)
    h = palette["head"]
    # Crossguard
    guard = [(5, 9), (6, 10), (7, 7), (8, 8), (4, 10), (6, 8)]
    for x, y in guard:
        g[y][x] = h[1]
    g[9][6] = h[0]
    g[10][4] = h[0]
    g[7][7] = h[3]
    # Pommel
    g[14][1] = h[1]
    g[13][1] = h[0]
    g[14][2] = h[0]

    # Blade: (7, 7) to (14, 0)
    blade_spine = [(7, 6), (8, 5), (9, 4), (10, 3), (11, 2), (12, 1), (13, 0)]
    blade_edge = [(8, 6), (9, 5), (10, 4), (11, 3), (12, 2), (13, 1), (14, 0)]
    blade_shadow = [(6, 7), (7, 6), (8, 5), (9, 4), (10, 3), (11, 2), (12, 1)]

    for x, y in blade_shadow:
        g[y][x] = h[0]
    for x, y in blade_spine:
        g[y][x] = h[2]
    for x, y in blade_edge:
        g[y][x] = h[3]
    g[0][13] = h[4]
    g[0][14] = h[4]

    if hardened:
        # Add obsidian reinforced border & pommel stud
        g[14][1] = HARDENED_TRIM["rivet"]
        g[10][4] = HARDENED_TRIM["dark"]
        g[7][9] = HARDENED_TRIM["light"]
        g[1][12] = HARDENED_TRIM["light"]
    return g

def draw_shovel(palette, hardened=False):
    g = empty_16x16()
    draw_stick(g, palette, hardened)
    h = palette["head"]
    # Shovel blade at tip
    blade = [
        (9, 5), (10, 5), (11, 5),
        (10, 4), (11, 4), (12, 4),
        (11, 3), (12, 3), (13, 3),
        (12, 2), (13, 2), (14, 2),
        (13, 1), (14, 1), (14, 0), (15, 1)
    ]
    for x, y in blade:
        g[y][x] = h[2]
    # Highlights & shadows
    g[5][9] = h[0]
    g[5][10] = h[0]
    g[4][10] = h[1]
    g[3][11] = h[2]
    g[2][12] = h[3]
    g[1][13] = h[4]
    g[1][14] = h[4]
    g[2][14] = h[3]
    g[3][13] = h[1]
    g[4][12] = h[0]
    g[5][11] = h[0]

    if hardened:
        g[4][10] = HARDENED_TRIM["dark"]
        g[1][14] = HARDENED_TRIM["rivet"]
        g[2][13] = HARDENED_TRIM["light"]
    return g

def draw_excavator(palette, hardened=False):
    g = empty_16x16()
    # Reinforced thicker handle
    draw_stick(g, palette, hardened)
    for x, y in [(3, 13), (4, 12), (5, 11), (6, 10), (7, 9)]:
        g[y][x] = palette["handle"][0]

    h = palette["head"]
    # Broad heavy excavator spade scoop (centered at top-right, wide curved head)
    head_coords = [
        (8, 4), (9, 4), (10, 4), (11, 4), (12, 4),
        (8, 3), (9, 3), (10, 3), (11, 3), (12, 3), (13, 3),
        (9, 2), (10, 2), (11, 2), (12, 2), (13, 2), (14, 2),
        (10, 1), (11, 1), (12, 1), (13, 1), (14, 1), (15, 1),
        (11, 0), (12, 0), (13, 0), (14, 0),
        # Lateral flanges of the scoop
        (7, 4), (7, 5), (8, 5), (9, 5),
        (12, 5), (13, 5), (14, 4), (13, 4)
    ]
    for x, y in head_coords:
        if 0 <= x < 16 and 0 <= y < 16:
            g[y][x] = h[2]

    # Highlights along cutting edge
    for x, y in [(11, 0), (12, 0), (13, 0), (14, 0), (15, 1), (14, 1)]:
        if 0 <= x < 16 and 0 <= y < 16:
            g[y][x] = h[4]

    # Midtones & ribs
    for x, y in [(10, 2), (11, 2), (12, 1), (13, 2), (10, 3)]:
        if 0 <= x < 16 and 0 <= y < 16:
            g[y][x] = h[3]

    # Shadows along back and collar
    for x, y in [(7, 5), (8, 5), (9, 5), (6, 6), (7, 6), (12, 5), (13, 5)]:
        if 0 <= x < 16 and 0 <= y < 16:
            g[y][x] = h[0]

    if hardened:
        # Obsidian reinforced rim & steel studs
        for x, y in [(7, 5), (14, 4), (11, 0), (15, 1)]:
            if 0 <= x < 16 and 0 <= y < 16:
                g[y][x] = HARDENED_TRIM["dark"]
        g[4][10] = HARDENED_TRIM["rivet"]
        g[2][12] = HARDENED_TRIM["light"]
        g[10][3] = HARDENED_TRIM["rivet"]
        g[12][2] = HARDENED_TRIM["light"]
    return g

def draw_pickaxe(palette, hardened=False):
    g = empty_16x16()
    draw_stick(g, palette, hardened)
    h = palette["head"]
    # Pickaxe arch
    arch = [
        (6, 4), (7, 4), (7, 3), (8, 3), (8, 2), (9, 2),
        (10, 2), (11, 2), (11, 3), (12, 3), (12, 4), (13, 4),
        (13, 5), (14, 5), (14, 6), (14, 7)
    ]
    for x, y in arch:
        g[y][x] = h[2]

    # Tips & highlights
    g[4][6] = h[0]
    g[4][7] = h[1]
    g[3][7] = h[1]
    g[3][8] = h[2]
    g[2][8] = h[3]
    g[2][9] = h[4]
    g[2][10] = h[4]
    g[2][11] = h[3]
    g[3][11] = h[2]
    g[3][12] = h[1]
    g[4][12] = h[0]
    g[5][13] = h[0]
    g[6][14] = h[1]
    g[7][14] = h[0]

    g[5][9] = h[0]
    g[6][8] = h[1]
    g[7][8] = h[2]

    if hardened:
        g[2][10] = HARDENED_TRIM["light"]
        g[3][11] = HARDENED_TRIM["dark"]
        g[5][9] = HARDENED_TRIM["rivet"]
        g[6][8] = HARDENED_TRIM["dark"]
    return g

def draw_axe(palette, hardened=False):
    g = empty_16x16()
    draw_stick(g, palette, hardened)
    h = palette["head"]
    # Axe blade on upper-left of head
    blade = [
        (7, 3), (8, 2), (9, 2), (10, 2), (11, 3),
        (7, 4), (8, 3), (9, 3), (10, 3), (11, 4),
        (7, 5), (8, 4), (9, 4), (10, 4),
        (7, 6), (8, 5), (9, 5),
        (6, 6), (6, 5), (6, 4)
    ]
    for x, y in blade:
        g[y][x] = h[2]

    g[2][8] = h[4]
    g[2][9] = h[4]
    g[2][10] = h[3]
    g[3][7] = h[3]
    g[4][6] = h[1]
    g[5][6] = h[0]
    g[6][6] = h[0]
    g[6][7] = h[0]
    g[5][8] = h[1]

    if hardened:
        g[2][9] = HARDENED_TRIM["light"]
        g[5][6] = HARDENED_TRIM["dark"]
        g[6][7] = HARDENED_TRIM["rivet"]
    return g

def draw_hoe(palette, hardened=False):
    g = empty_16x16()
    draw_stick(g, palette, hardened)
    h = palette["head"]
    # Hoe head: hooks from top of stick forward
    hook = [
        (8, 3), (9, 2), (10, 2), (11, 2), (12, 2), (13, 2),
        (12, 3), (13, 3), (13, 4), (14, 3), (14, 4)
    ]
    for x, y in hook:
        g[y][x] = h[2]

    g[2][10] = h[3]
    g[2][11] = h[4]
    g[2][12] = h[4]
    g[2][13] = h[3]
    g[3][13] = h[1]
    g[4][13] = h[0]
    g[4][14] = h[0]
    g[3][14] = h[1]

    if hardened:
        g[2][11] = HARDENED_TRIM["light"]
        g[4][13] = HARDENED_TRIM["dark"]
        g[3][9] = HARDENED_TRIM["rivet"]
    return g

def draw_hammer(palette, hardened=False):
    g = empty_16x16()
    # Thicker, heavier reinforced handle
    draw_stick(g, palette, hardened)
    # Extra thickness along handle
    for x, y in [(3, 13), (4, 12), (5, 11), (6, 10), (7, 9), (8, 8)]:
        g[y][x] = palette["handle"][0]

    h = palette["head"]
    # Massive heavy rectangular sledgehammer head (centered around 10, 5)
    # Head bounds: x from 6 to 14, y from 1 to 8
    head_coords = [
        # Center core
        (8, 4), (9, 4), (10, 4), (11, 4), (12, 4),
        (8, 5), (9, 5), (10, 5), (11, 5), (12, 5),
        # Front striking face (top right)
        (10, 2), (11, 2), (12, 2), (13, 2),
        (10, 3), (11, 3), (12, 3), (13, 3), (14, 3),
        (12, 1), (13, 1), (14, 2),
        # Back counterweight (bottom left)
        (7, 5), (7, 6), (8, 6), (9, 6),
        (6, 6), (6, 7), (7, 7), (8, 7),
        (5, 7), (6, 8), (7, 8)
    ]
    for x, y in head_coords:
        g[y][x] = h[2]

    # Outline / shadow on head
    shadows = [(5, 7), (6, 8), (7, 8), (8, 7), (9, 6), (6, 6), (7, 5)]
    for x, y in shadows:
        g[y][x] = h[0]

    # Highlights on striking face
    highlights = [(12, 1), (13, 1), (14, 2), (14, 3), (13, 2)]
    for x, y in highlights:
        g[y][x] = h[4]

    # Midtones & bevels
    for x, y in [(10, 2), (11, 2), (12, 3), (13, 4), (11, 3)]:
        g[y][x] = h[3]

    if hardened:
        # Reinforced obsidian corners and heavy steel bolts
        for x, y in [(5, 7), (7, 8), (14, 2), (12, 1)]:
            g[y][x] = HARDENED_TRIM["dark"]
        g[5][10] = HARDENED_TRIM["rivet"]
        g[9][5] = HARDENED_TRIM["rivet"]
        g[8][4] = HARDENED_TRIM["light"]
        g[13][3] = HARDENED_TRIM["light"]
    return g

def draw_paxel(palette, hardened=False):
    g = empty_16x16()
    draw_stick(g, palette, hardened)
    h = palette["head"]

    # Hybrid multi-tool:
    # Axe blade on left (7..5, 4..7)
    # Pickaxe horn on top (7..11, 1..3)
    # Shovel scoop on top-right (11..14, 2..6)
    paxel_head = [
        # Center core
        (8, 4), (9, 4), (9, 5), (8, 5),
        # Axe blade (left)
        (7, 4), (6, 5), (5, 6), (6, 6), (7, 5), (7, 6),
        # Pickaxe arch (top)
        (7, 3), (8, 2), (9, 2), (10, 2),
        # Shovel scoop (right)
        (10, 3), (11, 3), (12, 3),
        (11, 4), (12, 4), (13, 4),
        (12, 5), (13, 5), (14, 5)
    ]
    for x, y in paxel_head:
        g[y][x] = h[2]

    # Shadows
    for x, y in [(5, 6), (6, 7), (7, 6), (8, 6), (12, 6), (13, 6), (14, 6)]:
        if 0 <= x < 16 and 0 <= y < 16:
            g[y][x] = h[0]

    # Highlights
    g[2][8] = h[4]
    g[2][9] = h[4]
    g[3][10] = h[3]
    g[3][12] = h[3]
    g[4][13] = h[4]
    g[5][14] = h[4]

    if hardened:
        g[5][6] = HARDENED_TRIM["dark"]
        g[6][7] = HARDENED_TRIM["dark"]
        g[2][9] = HARDENED_TRIM["light"]
        g[4][13] = HARDENED_TRIM["light"]
        g[4][8] = HARDENED_TRIM["rivet"]
        g[9][5] = HARDENED_TRIM["rivet"]
    return g

def draw_plate():
    g = empty_16x16()
    # 12x12 metal plate with obsidian & steel finish
    # border x=2..13, y=2..13
    for y in range(2, 14):
        for x in range(2, 14):
            # Inner body
            g[y][x] = (42, 33, 52, 255) # Deep obsidian purple-black

    # Dark outer bevel
    for x in range(2, 14):
        g[2][x] = (140, 140, 150, 255) # Top bright steel rim
        g[13][x] = (20, 16, 26, 255)   # Bottom dark shadow
    for y in range(2, 14):
        g[y][2] = (120, 120, 130, 255) # Left rim
        g[y][13] = (25, 20, 32, 255)   # Right rim

    # Inner recessed panel
    for y in range(4, 12):
        for x in range(4, 12):
            g[y][x] = (30, 24, 38, 255)

    # 4 Corner rivets (gleaming steel)
    rivet_pos = [(4, 4), (11, 4), (4, 11), (11, 11)]
    for rx, ry in rivet_pos:
        g[ry][rx] = (220, 220, 240, 255)
        g[ry+1][rx] = (15, 12, 20, 255)

    # Center reinforcing diamond/cross
    cross = [(7, 7), (8, 7), (7, 8), (8, 8), (6, 7), (9, 7), (6, 8), (9, 8), (7, 6), (8, 6), (7, 9), (8, 9)]
    for cx, cy in cross:
        g[cy][cx] = (75, 58, 92, 255)
    g[7][7] = (120, 95, 150, 255)
    g[8][7] = (160, 140, 190, 255)
    return g

def draw_depth_plate():
    g = empty_16x16()
    for y in range(2, 14):
        for x in range(2, 14):
            g[y][x] = (80, 80, 85, 255)

    for x in range(2, 14):
        g[2][x] = (190, 190, 200, 255)
        g[13][x] = (30, 30, 35, 255)
    for y in range(2, 14):
        g[y][2] = (160, 160, 170, 255)
        g[y][13] = (40, 40, 45, 255)

    for rx, ry in [(3, 3), (12, 3), (3, 12), (12, 12)]:
        g[ry][rx] = (230, 230, 240, 255)
        g[ry+1][rx] = (20, 20, 25, 255)

    for y in range(5, 11):
        for x in range(5, 11):
            g[y][x] = (45, 45, 50, 255)

    for x in range(6, 10):
        g[6][x] = (220, 130, 40, 255)
        g[8][x] = (190, 190, 200, 255)
    g[9][7] = (230, 230, 240, 255)
    g[9][8] = (230, 230, 240, 255)
    g[10][7] = (255, 255, 255, 255)
    return g

def draw_echo_plate():
    g = empty_16x16()
    for y in range(2, 14):
        for x in range(2, 14):
            g[y][x] = (11, 26, 36, 255)

    for x in range(2, 14):
        g[2][x] = (20, 130, 140, 255)
        g[13][x] = (5, 12, 18, 255)
    for y in range(2, 14):
        g[y][2] = (15, 100, 110, 255)
        g[y][13] = (5, 12, 18, 255)

    for rx, ry in [(3, 3), (12, 3), (3, 12), (12, 12)]:
        g[ry][rx] = (90, 243, 219, 255)
        g[ry+1][rx] = (5, 40, 45, 255)

    for y in range(5, 11):
        for x in range(6, 10):
            g[y][x] = (8, 60, 70, 255)

    crystal = [(7, 5), (8, 5), (6, 6), (7, 6), (8, 6), (9, 6), (6, 7), (7, 7), (8, 7), (9, 7), (6, 8), (7, 8), (8, 8), (9, 8), (7, 9), (8, 9), (7, 10), (8, 10)]
    for cx, cy in crystal:
        g[cy][cx] = (5, 178, 155, 255)
    for cx, cy in [(7, 6), (8, 6), (7, 7), (8, 7)]:
        g[cy][cx] = (162, 255, 255, 255)
    return g

def draw_lava_plate():
    g = empty_16x16()
    for y in range(2, 14):
        for x in range(2, 14):
            g[y][x] = (32, 12, 35, 255)

    for x in range(2, 14):
        g[2][x] = (160, 40, 100, 255)
        g[13][x] = (12, 4, 15, 255)
    for y in range(2, 14):
        g[y][2] = (120, 30, 80, 255)
        g[y][13] = (15, 5, 18, 255)

    cracks = [(5, 4), (6, 5), (7, 6), (8, 6), (9, 5), (10, 4), (6, 7), (7, 7), (8, 7), (9, 7), (7, 8), (8, 8), (7, 9), (8, 10), (6, 11), (9, 11)]
    for cx, cy in cracks:
        g[cy][cx] = (220, 60, 10, 255)
    for cx, cy in [(7, 7), (8, 7), (7, 8), (8, 8)]:
        g[cy][cx] = (255, 180, 20, 255)
    g[7][7] = (255, 240, 120, 255)

    for rx, ry in [(3, 3), (12, 3), (3, 12), (12, 12)]:
        g[ry][rx] = (210, 50, 180, 255)
        g[ry+1][rx] = (40, 10, 45, 255)
    return g

def draw_block_top():
    # 16x16 workstation top: anvil face, reinforced metal borders
    g = [[(35, 30, 42, 255) for _ in range(16)] for _ in range(16)]
    # Border
    for i in range(16):
        g[0][i] = (110, 110, 120, 255)
        g[15][i] = (20, 18, 25, 255)
        g[i][0] = (90, 90, 100, 255)
        g[i][15] = (25, 22, 30, 255)
    # Working steel plate in center
    for y in range(3, 13):
        for x in range(3, 13):
            g[y][x] = (60, 55, 68, 255)
    # Highlight
    for x in range(4, 12):
        g[3][x] = (140, 140, 150, 255)
    # Hammer engraving in center
    for x in range(6, 10):
        g[7][x] = (20, 16, 26, 255)
        g[8][x] = (20, 16, 26, 255)
    g[6][7] = (20, 16, 26, 255)
    g[9][7] = (20, 16, 26, 255)
    return g

def draw_block_side():
    # 16x16 deepslate / dark iron workstation side
    g = [[(40, 36, 45, 255) for _ in range(16)] for _ in range(16)]
    # Top metal rim
    for x in range(16):
        g[0][x] = (110, 110, 120, 255)
        g[1][x] = (70, 65, 75, 255)
        g[2][x] = (30, 26, 35, 255)
    # Bottom heavy stone base
    for x in range(16):
        g[14][x] = (25, 22, 30, 255)
        g[15][x] = (15, 12, 18, 255)
    # Vertical iron brackets
    for y in range(3, 14):
        g[y][2] = (85, 80, 90, 255)
        g[y][3] = (60, 55, 65, 255)
        g[y][12] = (85, 80, 90, 255)
        g[y][13] = (60, 55, 65, 255)
    # Rivets
    for y in [4, 12]:
        for x in [3, 13]:
            g[y][x] = (180, 180, 190, 255)
    return g

def draw_block_front():
    g = draw_block_side()
    # Front quenching basin / emblem
    for y in range(6, 11):
        for x in range(6, 10):
            g[y][x] = (25, 20, 30, 255)
    # Quenching water / glowing tempering oil in center
    for y in range(7, 10):
        for x in range(7, 9):
            g[y][x] = (200, 100, 30, 255) # glowing orange heat
    g[8][7] = (255, 180, 60, 255)
    return g

def draw_block_bottom():
    return [[(25, 22, 30, 255) for _ in range(16)] for _ in range(16)]

def draw_gui():
    # 176x166 GUI background
    W, H = 176, 166
    g = [[(198, 198, 198, 255) for _ in range(W)] for _ in range(H)]

    # Draw border
    for x in range(W):
        g[0][x] = (255, 255, 255, 255)
        g[1][x] = (255, 255, 255, 255)
        g[H-2][x] = (55, 55, 55, 255)
        g[H-1][x] = (0, 0, 0, 255)
    for y in range(H):
        g[y][0] = (255, 255, 255, 255)
        g[y][1] = (255, 255, 255, 255)
        g[y][W-2] = (55, 55, 55, 255)
        g[y][W-1] = (0, 0, 0, 255)

    def draw_slot(sx, sy, w=18, h=18):
        for y in range(sy, sy + h):
            for x in range(sx, sx + w):
                g[y][x] = (139, 139, 139, 255)
        for x in range(sx, sx + w):
            g[sy][x] = (55, 55, 55, 255)
            g[sy + h - 1][x] = (255, 255, 255, 255)
        for y in range(sy, sy + h):
            g[y][sx] = (55, 55, 55, 255)
            g[y][sx + w - 1] = (255, 255, 255, 255)

    # Tool Slot at (26, 46)
    draw_slot(26, 46)
    # Plate Slot at (75, 46)
    draw_slot(75, 46)
    # Result Slot at (133, 46) (large slot: 24x24)
    draw_slot(130, 43, 24, 24)

    # Plus sign between slot 0 and slot 1 at (54, 53)
    for i in range(-3, 4):
        g[54][55 + i] = (100, 100, 100, 255)
        g[54 + i][55] = (100, 100, 100, 255)

    # Arrow between slot 1 and result slot at (104, 52)
    arrow_pixels = [
        (103, 53), (104, 53), (105, 53), (106, 53), (107, 53), (108, 53), (109, 53), (110, 53),
        (103, 54), (104, 54), (105, 54), (106, 54), (107, 54), (108, 54), (109, 54), (110, 54),
        (108, 51), (109, 52), (111, 53), (111, 54), (109, 55), (108, 56)
    ]
    for ax, ay in arrow_pixels:
        g[ay][ax] = (90, 90, 90, 255)

    # Player inventory: 3 rows of 9 at (7, 83)
    for row in range(3):
        for col in range(9):
            draw_slot(7 + col * 18, 83 + row * 18)

    # Player hotbar: 1 row of 9 at (7, 141)
    for col in range(9):
        draw_slot(7 + col * 18, 141)

    return g

def draw_overclock_plate():
    g = empty_16x16()
    for y in range(2, 14):
        for x in range(2, 14):
            g[y][x] = (45, 10, 10, 255)
    for x in range(2, 14):
        g[2][x] = (240, 190, 50, 255)
        g[13][x] = (90, 15, 15, 255)
    for y in range(2, 14):
        g[y][2] = (190, 140, 30, 255)
        g[y][13] = (90, 15, 15, 255)
    for rx, ry in [(3, 3), (12, 3), (3, 12), (12, 12)]:
        g[ry][rx] = (255, 230, 100, 255)
        g[ry+1][rx] = (140, 80, 20, 255)
    # Redstone circuit & turbine blades
    for y in range(5, 11):
        for x in range(5, 11):
            g[y][x] = (180, 20, 20, 255)
    turbine = [(7, 4), (8, 4), (11, 7), (11, 8), (8, 11), (7, 11), (4, 8), (4, 7)]
    for tx, ty in turbine:
        g[ty][tx] = (255, 80, 60, 255)
    for cx, cy in [(7, 7), (8, 7), (7, 8), (8, 8)]:
        g[cy][cx] = (255, 250, 160, 255)
    return g

def draw_breeze_plate():
    g = empty_16x16()
    for y in range(2, 14):
        for x in range(2, 14):
            g[y][x] = (40, 45, 65, 255)
    for x in range(2, 14):
        g[2][x] = (140, 220, 245, 255)
        g[13][x] = (20, 22, 35, 255)
    for y in range(2, 14):
        g[y][2] = (100, 180, 210, 255)
        g[y][13] = (20, 22, 35, 255)
    for rx, ry in [(3, 3), (12, 3), (3, 12), (12, 12)]:
        g[ry][rx] = (200, 245, 255, 255)
        g[ry+1][rx] = (60, 65, 95, 255)
    # Wind swirls & breeze rods
    swirls = [(5, 6), (6, 5), (7, 5), (8, 5), (9, 6), (9, 7), (8, 8), (7, 8), (6, 9), (6, 10), (7, 11), (8, 11), (9, 10)]
    for sx, sy in swirls:
        g[sy][sx] = (215, 250, 255, 255)
    breeze_purple = [(7, 6), (8, 7), (7, 9), (8, 10)]
    for px, py in breeze_purple:
        g[py][px] = (175, 125, 245, 255)
    return g

def draw_auto_smelt_plate():
    g = empty_16x16()
    for y in range(2, 14):
        for x in range(2, 14):
            g[y][x] = (50, 20, 15, 255)
    for x in range(2, 14):
        g[2][x] = (220, 110, 45, 255)
        g[13][x] = (25, 10, 8, 255)
    for y in range(2, 14):
        g[y][2] = (180, 80, 30, 255)
        g[y][13] = (25, 10, 8, 255)
    for rx, ry in [(3, 3), (12, 3), (3, 12), (12, 12)]:
        g[ry][rx] = (255, 190, 70, 255)
        g[ry+1][rx] = (120, 45, 15, 255)
    # Flame core
    flames = [(7, 5), (8, 5), (6, 6), (7, 6), (8, 6), (9, 6), (6, 7), (7, 7), (8, 7), (9, 7), (6, 8), (7, 8), (8, 8), (9, 8), (7, 9), (8, 9), (7, 10), (8, 10)]
    for fx, fy in flames:
        g[fy][fx] = (245, 90, 20, 255)
    for cx, cy in [(7, 6), (8, 6), (7, 7), (8, 7), (7, 8), (8, 8)]:
        g[cy][cx] = (255, 235, 60, 255)
    g[7][7] = (255, 255, 200, 255)
    return g

def draw_reinforced_plate():
    g = empty_16x16()
    for y in range(2, 14):
        for x in range(2, 14):
            g[y][x] = (25, 30, 38, 255)
    for x in range(2, 14):
        g[2][x] = (180, 190, 210, 255)
        g[13][x] = (15, 18, 22, 255)
    for y in range(2, 14):
        g[y][2] = (130, 140, 160, 255)
        g[y][13] = (15, 18, 22, 255)
    for rx, ry in [(3, 3), (12, 3), (3, 12), (12, 12)]:
        g[ry][rx] = (230, 240, 255, 255)
        g[ry+1][rx] = (70, 80, 95, 255)
    # Diamond reinforced center diamond
    diamond_rim = [(7, 4), (8, 4), (6, 5), (9, 5), (5, 6), (10, 6), (4, 7), (11, 7), (4, 8), (11, 8), (5, 9), (10, 9), (6, 10), (9, 10), (7, 11), (8, 11)]
    for dx, dy in diamond_rim:
        g[dy][dx] = (20, 160, 175, 255)
    for y in range(6, 10):
        for x in range(6, 10):
            g[y][x] = (60, 230, 235, 255)
    for cx, cy in [(6, 6), (7, 6), (6, 7)]:
        g[cy][cx] = (210, 255, 255, 255)
    return g

def draw_amethyst_plate():
    g = empty_16x16()
    for y in range(2, 14):
        for x in range(2, 14):
            g[y][x] = (30, 18, 40, 255)
    for x in range(2, 14):
        g[2][x] = (200, 130, 230, 255)
        g[13][x] = (15, 8, 22, 255)
    for y in range(2, 14):
        g[y][2] = (160, 90, 190, 255)
        g[y][13] = (15, 8, 22, 255)
    for rx, ry in [(3, 3), (12, 3), (3, 12), (12, 12)]:
        g[ry][rx] = (235, 170, 255, 255)
        g[ry+1][rx] = (75, 30, 100, 255)
    # Amethyst crystal cluster
    shards = [(7, 4), (8, 5), (6, 6), (7, 6), (8, 6), (9, 6), (5, 7), (6, 7), (7, 7), (8, 7), (9, 7), (10, 7), (6, 8), (7, 8), (8, 8), (9, 8), (7, 9), (8, 9), (7, 10), (8, 11)]
    for sx, sy in shards:
        g[sy][sx] = (155, 65, 220, 255)
    highlights = [(7, 5), (7, 6), (8, 6), (6, 7), (7, 7)]
    for hx, hy in highlights:
        g[hy][hx] = (230, 160, 255, 255)
    return g

def draw_moss_plate():
    g = empty_16x16()
    for y in range(2, 14):
        for x in range(2, 14):
            g[y][x] = (25, 42, 20, 255)
    for x in range(2, 14):
        g[2][x] = (110, 190, 70, 255)
        g[13][x] = (10, 22, 8, 255)
    for y in range(2, 14):
        g[y][2] = (80, 150, 50, 255)
        g[y][13] = (10, 22, 8, 255)
    for rx, ry in [(3, 3), (12, 3), (3, 12), (12, 12)]:
        g[ry][rx] = (180, 245, 110, 255)
        g[ry+1][rx] = (30, 70, 20, 255)
    # Moss & Emerald sprout
    leaf = [(7, 4), (8, 5), (6, 6), (7, 6), (8, 6), (9, 6), (7, 7), (8, 7), (6, 8), (7, 8), (8, 8), (9, 8), (7, 9), (8, 10), (7, 11)]
    for lx, ly in leaf:
        g[ly][lx] = (45, 185, 75, 255)
    for sx, sy in [(7, 6), (8, 7), (7, 8)]:
        g[sy][sx] = (245, 235, 80, 255)
    return g

def draw_magnetic_plate():
    g = empty_16x16()
    for y in range(2, 14):
        for x in range(2, 14):
            g[y][x] = (35, 38, 45, 255)
    for x in range(2, 14):
        g[2][x] = (160, 165, 180, 255)
        g[13][x] = (18, 20, 25, 255)
    for y in range(2, 14):
        g[y][2] = (120, 125, 140, 255)
        g[y][13] = (18, 20, 25, 255)
    for rx, ry in [(3, 3), (12, 3), (3, 12), (12, 12)]:
        g[ry][rx] = (220, 225, 240, 255)
        g[ry+1][rx] = (60, 65, 75, 255)
    # Magnet horseshoe (Red left pole, Blue right pole)
    # Red pole (left)
    for y in range(5, 10):
        g[y][5] = (220, 40, 45, 255)
        g[y][6] = (180, 25, 30, 255)
    # Blue pole (right)
    for y in range(5, 10):
        g[y][9] = (30, 120, 235, 255)
        g[y][10] = (20, 80, 195, 255)
    # Bottom connector
    for x in range(6, 10):
        g[10][x] = (130, 135, 150, 255)
        g[11][x] = (90, 95, 110, 255)
    # Pole tips
    g[5][5] = (255, 120, 120, 255)
    g[5][10] = (120, 190, 255, 255)
    return g

print("Generators configured")


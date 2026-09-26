# Changelog

## v1.2.2 — Armor Render & Texture Fix

### Fixed
- **Player Avatar Armor Rendering** — Re-rendered all 10 armor sets (Copper, Deepslate, and Hardened variants for Leather, Copper, Deepslate, Chainmail, Iron, Gold, Diamond, Netherite) with authentic Minecraft humanoid armor UV maps, fine plate shading, facial/neck cutouts, shoulder pauldrons, knee guards, sole treads, and reinforced obsidian trims. Armor pieces now render realistically on the player avatar instead of distorted flat solid blocks.
- **Missing Hardened Gold Armor Textures** — Fixed naming mismatch where `hardened_gold` 3D layer textures were missing for Hardened Golden armor pieces, ensuring full compatibility across all armor slots.

## v1.2.1 — Bugfix

### Fixed
- **Magnetic Plate item duplication** — Items collected by the Magnetic Plate were appearing in both the player's inventory and as dropped entities in the world. Fixed by properly removing collected items from the block drop list.

## v1.2.0 — The Ranged Weapon Update

### Added
- **Tiered Bows (11 Bows)** — Complete bow progression across materials:
  - **Copper Bow** (300 dur, +0.5 damage, 1.15× draw speed)
  - **Deepslate Bow** (450 dur, +1.5 damage, heavy recurve)
  - **Iron Bow** (550 dur, +1.0 damage, 1.10× draw speed)
  - **Diamond Bow** (1,800 dur, +2.0 damage, 1.25× draw speed)
  - **Netherite Bow** (2,500 dur, +3.0 damage, 1.35× draw speed, fireproof)
  - **Hardened Variants** for all bows (including vanilla Wooden Bow: **Hardened Bow** with 1,150 dur) scaling up to **6,000 durability** and +4.5 damage!
- **Tiered Crossbows (11 Crossbows)** — Complete crossbow progression across materials:
  - **Copper Crossbow** (350 dur, +0.5 damage, 21 ticks reload)
  - **Deepslate Crossbow** (500 dur, +2.0 damage, heavy arbalest)
  - **Iron Crossbow** (600 dur, +1.0 damage, 22 ticks reload)
  - **Diamond Crossbow** (2,000 dur, +2.5 damage, 19 ticks reload)
  - **Netherite Crossbow** (2,800 dur, +3.5 damage, 17 ticks reload, fireproof)
  - **Hardened Variants** for all crossbows (including vanilla: **Hardened Crossbow** with 1,400 dur) scaling up to **6,500 durability** and +5.0 damage!
- **Ranged Upgrade Plate System** — Upgrade plates now empower Bows & Crossbows:
  - 🔥 **Thermal Core**: Incendiary arrows & bolts (innate Flame effect)
  - ⚡ **Redstone Turbine**: +30% to +35% faster draw time / rapid reload
  - 💨 **Wind Core**: Gale velocity boost with flatter projectile trajectory
  - 🧲 **Magnetic Vacuum**: Sniper Loot Magnet (mobs defeated at any range drop loot straight into player's inventory)
  - 💎 **Amethyst Shield**: Break protection (bow/crossbow never snaps, stops at 1 durability)
  - 💜 **Soul Repair**: Passive auto-repair (1 dur / 5s) + collected XP mending
  - 🔩 **Reinforced Frame**: 50% chance of zero durability loss per shot
  - 🔥 **Lava Seal**: Fireproof weapon that floats in lava
- **Hardening Station Ranged Support**:
  - Free material repairs (String, Sticks, Tripwire Hooks, Ingots, Diamonds) for 0 XP
  - Duplicate combining with enchantment merging + 12% bonus durability
  - Prior work anvil penalty reset
- **Full Visuals & Translations**:
  - Custom dynamic pulling, charged, arrow, and firework textures & model overrides for all 22 weapons
  - Full English and German localization with stat badges

## v1.1.1 — Bugfix

### Fixed
- **All tools now have proper attack damage and speed** — Swords, pickaxes, axes, shovels, hoes, hammers, excavators, and paxels were missing their combat attribute modifiers due to a Minecraft 1.21 API change. All tools now deal correct damage matching their tier.
- **Dedicated server crash on startup** — Removed client-only class reference (`RegisterMenuScreensEvent`) from the main mod constructor that caused `NoClassDefFoundError` on dedicated servers. Client screen registration now uses the safe `@EventBusSubscriber(value = Dist.CLIENT)` pattern.

## v1.1.0 — The Armor Update

### Added
- **Copper Armor** — Complete 4-piece armor set crafted from Copper Ingots (Helmet, Chestplate, Leggings, Boots)
- **Deepslate Armor** — Complete 4-piece armor set crafted from Cobbled Deepslate with innate +1 Toughness and Knockback Resistance
- **Hardened Armor Variants** — Reinforced versions of all armor sets (Leather, Copper, Deepslate, Chainmail, Iron, Gold, Diamond, Netherite) with ~2.5-3x durability and +1 Armor Toughness
- **Armor Upgrade Plates System** — All upgrade plates can now be installed onto armor pieces in the Hardening Station (stackable, per-piece activation):
  - **Lava Seal Plating**: Complete fire & lava damage immunity + fire extinguishing
  - **Wind Core Plate**: 100% fall damage immunity
  - **Amethyst Shield Plate**: Armor shatter protection (never breaks, stops at 1 durability)
  - **Magnetic Vacuum Plate**: Item Magnet Aura (passively draws drops within 8 blocks to player)
  - **Redstone Turbine Plate**: Kinetic Boost (+15% movement speed)
  - **Thermal Core Plate**: Flame Barrier (ignites attackers for 4s + freeze immunity)
  - **Soul Repair Core**: Passive armor auto-repair + XP mending
  - **Photosynthesis Plate**: Sunlight/nature armor repair + passive daylight regeneration
  - **Reinforced Frame Plate**: 50% chance to negate incoming durability damage to armor
  - **Depth Drill / Heavy Plating**: 12% incoming damage reduction per piece
- **Hardening Station Armor Support** — Upgrade base armor to Hardened variants, free raw material repair (0 XP), and duplicate combining with enchantment merging
- **Trimmable Armor Support** — All 40 armor pieces support vanilla armor trims at the smithing table

## v1.0.1 — Hotfix

### Fixed
- Wooden Hammer and Wooden Excavator can now be crafted with **all plank types** (Birch, Spruce, Acacia, etc.), not just Oak

## v1.0.0 — Initial Release

### Added
- **Copper Tools** — Full tool set (Sword, Pickaxe, Axe, Shovel, Hoe) crafted from Copper Ingots
- **Deepslate Tools** — Full tool set crafted from Cobbled Deepslate
- **Hammers** — 3×3 area pickaxes for all 8 tiers (Wood through Netherite)
- **Excavators** — 3×3 area shovels for all 8 tiers
- **Paxels** — Multi-tools (Pickaxe + Axe + Shovel + Hoe) for all 8 tiers
- **Hardening Station** — New workstation block with custom GUI for tool upgrades and free repairs
- **Hardened Tool Variants** — Reinforced versions of all 80+ tools with ~2.5-4× durability and improved stats
- **11 Upgrade Plates**:
  - Hardening Plate (tier upgrade / full repair)
  - Depth Drill Plate (3×3×2 mining for Hammers & Excavators)
  - Soul Repair Core (passive auto-repair + XP mending)
  - Lava Seal Plating (fireproof tools)
  - Redstone Turbine Plate (+35% speed + mining streak Haste)
  - Wind Core Plate (no underwater / airborne speed penalty)
  - Thermal Core Plate (auto-smelts mined drops)
  - Reinforced Frame Plate (50% durability negation chance)
  - Amethyst Shield Plate (prevents tool breaking at 1 durability)
  - Photosynthesis Plate (sunlight / nature repair)
  - Magnetic Vacuum Plate (drops go to inventory)
- **Free material repair** in Hardening Station (50% durability per item, 0 XP, resets anvil penalty)
- **Tool combining** with enchantment merging and 12% bonus durability
- **Full localization** in English and German
- **110+ items** with custom textures and crafting recipes
- Compatible with dedicated servers

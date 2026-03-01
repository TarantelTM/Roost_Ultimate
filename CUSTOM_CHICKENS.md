# Custom Chickens — `crlib/custom/`

Chicken Roost supports registering custom chickens via JSON files in the `crlib/custom/` directory. This allows modpack developers to add new chicken types without modifying the main config.

The mod scans all `.json` files in this directory at startup and registers items and entities for each valid entry. Files starting with `_` are ignored (use them for examples or notes).

## Client + Server Requirement

Custom chicken files **must be identical on both client and server**.

Item and entity registration happens at mod startup via `DeferredRegister` — before any server connection is established. If custom chickens are registered only on the server, clients will be rejected with:

```
Incompatible registry: unknown keys
ResourceKey[minecraft:item / chicken_roost:c_example],
ResourceKey[minecraft:entity_type / chicken_roost:c_example]
```

**Solution:** copy the entire `crlib/custom/` directory to both sides. When updating the modpack, make sure the files stay in sync. If using a launcher with auto-updates, include `crlib/` in the distribution package.

## JSON Format

### Minimal (only `id`, `tier`, `dropitem` required)

```json
[
  { "id": "c_example", "tier": 3, "dropitem": "minecraft:diamond" }
]
```

### Full

```json
[
  {
    "ChickenName": "Example Chicken",
    "MobOrMonster": "Mob",
    "id": "c_example",
    "itemtexture": "whitechicken",
    "mobtexture": "whitechicken",
    "dropitem": "minecraft:diamond",
    "eggtime": 600,
    "tier": 3
  }
]
```

Fields omitted in the minimal format are filled automatically:

| Field | Default |
|---|---|
| `ChickenName` | Generated from id (`"c_example"` → `"Example Chicken"`) |
| `itemtexture` | `"whitechicken"` |
| `mobtexture` | `"whitechicken"` |
| `MobOrMonster` | `"Mob"` |
| `eggtime` | `600` |

## Stoneblock 4 Example

File: `crlib/custom/ftb_chickens.json`

These chickens are not included in the default config but are required by the Stoneblock 4 KubeJS recipes:

```json
[
  {
    "ChickenName": "Dust Chicken",
    "MobOrMonster": "Mob",
    "id": "c_dust",
    "itemtexture": "sandchicken",
    "mobtexture": "sandchicken",
    "dropitem": "ftbstuff:dust",
    "eggtime": 600,
    "tier": 2
  },
  {
    "ChickenName": "Fluorite Chicken",
    "MobOrMonster": "Monster",
    "id": "c_fluorite",
    "itemtexture": "quartzchicken",
    "mobtexture": "quartzchicken",
    "dropitem": "ftbmaterials:fluorite_gem",
    "eggtime": 600,
    "tier": 4
  },
  {
    "ChickenName": "Skystone Chicken",
    "MobOrMonster": "Mob",
    "id": "c_skystone",
    "itemtexture": "skystonechicken",
    "mobtexture": "skystonechicken",
    "dropitem": "ae2:sky_stone_block",
    "eggtime": 600,
    "tier": 6
  },
  {
    "ChickenName": "Otherrock Chicken",
    "MobOrMonster": "Mob",
    "id": "c_otherrock",
    "itemtexture": "otherrockchicken",
    "mobtexture": "otherrockchicken",
    "dropitem": "occultism:otherrock",
    "eggtime": 600,
    "tier": 7
  },
  {
    "ChickenName": "Time Crystal Chicken",
    "MobOrMonster": "Mob",
    "id": "c_time",
    "itemtexture": "timechicken",
    "mobtexture": "timechicken",
    "dropitem": "justdirethings:time_crystal",
    "eggtime": 600,
    "tier": 9
  },
  {
    "ChickenName": "Neutron Chicken",
    "MobOrMonster": "Mob",
    "id": "c_neutron",
    "itemtexture": "neutronchicken",
    "mobtexture": "neutronchicken",
    "dropitem": "avaritia:neutron_pile",
    "eggtime": 600,
    "tier": 9
  },
  {
    "ChickenName": "Chaos Chicken",
    "MobOrMonster": "Mob",
    "id": "c_chaos",
    "itemtexture": "draconiumawakenedchicken",
    "mobtexture": "draconiumawakenedchicken",
    "dropitem": "draconicevolution:small_chaos_frag",
    "eggtime": 600,
    "tier": 9
  },
  {
    "ChickenName": "Antimatter Chicken",
    "MobOrMonster": "Mob",
    "id": "c_antimatter",
    "itemtexture": "antimatterchicken",
    "mobtexture": "antimatterchicken",
    "dropitem": "mekanism:pellet_antimatter",
    "eggtime": 600,
    "tier": 9
  }
]
```

| Chicken | Tier | Texture | Drop Item |
|---|---|---|---|
| Dust | 2 | `sandchicken` | `ftbstuff:dust` |
| Fluorite | 4 | `quartzchicken` | `ftbmaterials:fluorite_gem` |
| Skystone | 6 | `skystonechicken` | `ae2:sky_stone_block` |
| Otherrock | 7 | `otherrockchicken` | `occultism:otherrock` |
| Time Crystal | 9 | `timechicken` | `justdirethings:time_crystal` |
| Neutron | 9 | `neutronchicken` | `avaritia:neutron_pile` |
| Chaos | 9 | `draconiumawakenedchicken` | `draconicevolution:small_chaos_frag` |
| Antimatter | 9 | `antimatterchicken` | `mekanism:pellet_antimatter` |

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
  { "id": "c_dust", "tier": 2, "dropitem": "ftbstuff:dust" },
  { "id": "c_fluorite", "tier": 4, "dropitem": "ftbmaterials:fluorite_gem" },
  { "id": "c_skystone", "tier": 6, "dropitem": "ae2:sky_dust" },
  { "id": "c_otherrock", "tier": 7, "dropitem": "occultism:otherrock" },
  { "id": "c_time", "tier": 9, "dropitem": "justdirethings:time_crystal" },
  { "id": "c_neutron", "tier": 9, "dropitem": "avaritia:neutron_pile" },
  { "id": "c_chaos", "tier": 9, "dropitem": "draconicevolution:small_chaos_frag" },
  { "id": "c_antimatter", "tier": 9, "dropitem": "mekanism:pellet_antimatter" }
]
```

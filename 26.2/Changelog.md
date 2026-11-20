# Changelog

## v4.2.3b

### Player Changelog

- Added Pipe Upgrade Chips for Tiers 2 through 5. Right-click a pipe one tier below the chip to upgrade up to eight pipes, starting at the clicked pipe and selecting the closest connections of the same type and tier. Input, Output, and Pass-through settings carry over. Tier 5 chips work with fluid pipes only.
- Added five color-coded Fluid Pipe tiers. Set the face at the source tank to Input and the face at the destination tank to Output. Pipes connect to the same type and tier.
- Item pipes can empty fluid eggs, buckets, and compatible fluid containers into tanks. The entire contents must fit in one tank. Buckets and fluid eggs are consumed after a successful transfer, and reusable containers are returned empty to the source inventory.
- Trainers and Roosts can grant several levels from one feed and save leftover whole XP on the chicken, even when it reaches the level cap. The Trainer awards the full food value and the Roost awards its configured share.
- Item and fluid pipes can be picked up when broken normally. Pipe routes refresh when a line is rebuilt.
- Right-click a pipe face with the Chicken Stick or a compatible wrench to cycle that face through Pass-through, Input, and Output. Shift-right-click dismantles the selected pipe and puts the drop in your inventory if there is room.
- Item and fluid pipes continue routing through intermediate pipes with inputs or outputs. Each input rotates through all reachable output faces and skips those that cannot accept the transfer.

### UI Changelog

- Redesigned the Feeder and Collector inventory screens to match Minecraft's inventory layout. Both show the machine name and switch between storage and settings in the same window, with the player inventory visible in both views.
- Added readable target names, selection checkboxes, scrolling, range controls, and coordinate tooltips. The Feeder groups Round Robin, send amount, and per-target seed selection in its settings. Seed assignments now save and sync correctly.
- Restored the animated Trainer model and removed the broken model reference that produced a missing-texture cube.
- Fixed the Trainer's training and idle animations so they play continuously between state changes.
- Empty-hand right-click and Jade show the mode of the targeted pipe face. Item tooltips show throughput and range, Jade shows throughput, and pipe hitboxes include the visible connections.
- Item and fluid pipes show a gold ring on Input faces and two green stripes on Output faces. The markings update as you configure each face.
- Added distinct sprites and localized item names and tooltips for all four upgrade chips.


## v4.2.3 — RoostTile Performance Optimization
### Performance Comparison

| Metric                         | v4.2.2   | v4.2.3  | Reduction |
|--------------------------------|----------|---------|-----------|
| **RoostTile.tick() total**     | 7.19%    | 0.99%   | **-86%**  |
| RecipeManager.getRecipeFor()   | 4.71%    | 0%      | -100%     |
| ItemStack.copy() in getItem()  | 2.83%    | 0%      | -100%     |
| RoostRecipe.matches()          | 4.00%    | 0%      | -100%     |
| hasNeighborSignal()            | 1.53%    | 0%      | -100%     |

---

### Changes

1. **Recipe caching** — Recipe lookup via `RecipeManager.getRecipeFor()` was called every tick, streaming through all registered recipes. Now the result is cached and only re-queried when slot 0 (food) or slot 1 (chicken) contents change.

2. **Removed ItemStack.copy() from RecipeInput** — `getItem()` was returning a defensive copy on every call during recipe matching. Since `Ingredient.test()` is read-only, the copy was unnecessary and has been removed.

3. **Eliminated double recipe lookup** — `hasRecipe()` and `craftItem()` both performed independent recipe lookups. Now `craftItem()` reuses the cached recipe from `hasRecipe()`.

4. **Redstone signal caching** — `level.hasNeighborSignal(pos)` was polled every tick. Now the signal is cached and updated only when `neighborChanged()` fires on `RoostBlock`.

5. **Early exit for empty chicken slot** — If slot 1 is empty, all recipe logic is skipped entirely.

6. **Early exit for full output slot** — If the output slot is at max stack size, processing is paused until space is available.

7. **Conditional setChanged()** — Previously called unconditionally every tick. Now only called when state actually changes.

# Changelog

## v4.2.3 — RoostTile Performance Optimization

### Summary

Comprehensive optimization of `RoostTile.tick()` — the main server-side processing loop for Roost blocks. Total CPU usage reduced **from 7.19% to 1.01%** (~7x improvement).

---

### Before (v4.2.2)

```
RoostBlock$Lambda.tick()                          7.19%  (12944ms)
  RoostTile.tick()                                7.18%
    RoostTile.hasRecipe()                         5.11%
      RecipeManager.getRecipeFor()                4.71%
        stream.findFirst()                        4.60%
          RoostRecipe.matches()                   4.00%
            RoostTile$3.getItem()                 2.90%
              ItemStack.copy()                    2.83%
                ItemStack.<init>()                2.66%
    SignalGetter.hasNeighborSignal()               —
```

### After (v4.2.3)

```
RoostBlock$Lambda.tick()                          1.01%
  RoostTile.tick()                                0.99%
    RoostTile.tryPushOutputDown()                 0.32%
      BigInventoryHandler.insertItem()            0.09%
```

---

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

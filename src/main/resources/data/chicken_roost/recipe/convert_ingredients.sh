#!/usr/bin/env bash

# Ordner mit deinen Rezepten (anpassen, falls nötig)
ROOT="roost"

echo "Normalizing roost_output recipes..."

find "$ROOT" -type f -name "*.json" | while read -r file; do
  echo "  -> $file"

  perl -0777 -i -pe '
    # food: { "tag": "x" } -> food: "#x"
    s/"food"\s*:\s*\{\s*"tag"\s*:\s*"([^"]+)"\s*\}/"food": "#$1"/sg;

    # food: { "item": "x" } -> food: "x"
    s/"food"\s*:\s*\{\s*"item"\s*:\s*"([^"]+)"\s*\}/"food": "$1"/sg;

    # chicken: { "tag": "x" } -> chicken: "#x"
    s/"chicken"\s*:\s*\{\s*"tag"\s*:\s*"([^"]+)"\s*\}/"chicken": "#$1"/sg;

    # chicken: { "item": "x" } -> chicken: "x"
    s/"chicken"\s*:\s*\{\s*"item"\s*:\s*"([^"]+)"\s*\}/"chicken": "$1"/sg;
  ' "$file"

done

echo "All roost_output recipes normalized."
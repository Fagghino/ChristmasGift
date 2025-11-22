# ChristmasGift Plugin - Build Completato ✅

## ✅ Compilazione Riuscita!

Il plugin è stato compilato con successo e si trova in:
```
target/ChristmasGift-1.0.0.jar
```

## 🎁 Tutte le Funzionalità Disponibili

### ✅ Cosa funziona:
✅ Tutti i comandi (`/cg give`, `/cg stats`, `/cg leaderboard`, ecc.)  
✅ Sistema di piazzamento e claim dei blocchi regalo  
✅ Salvataggio dati in `data.yml`  
✅ Sistema di classifica  
✅ Configurazione completa in `config.yml` e `messages.yml`  
✅ Supporto blocchi normali (DIAMOND_BLOCK, GOLD_BLOCK, ecc.)  
✅ **Supporto COMPLETO per texture Base64 custom!** 🎨

### ⏸️ Opzionale (può essere aggiunto dopo):
⏸️ PlaceholderAPI per placeholder (`%christmasgift_found%`, ecc.)

## 🎨 Come Usare Texture Personalizzate

**NON serve più HeadDatabase!** Il plugin supporta direttamente le texture Base64.

### Passaggi:

1. **Trova una testa su minecraft-heads.com:**
   - Vai su https://minecraft-heads.com/
   - Cerca la categoria che ti interessa (es. "Christmas")
   - Clicca sulla testa che vuoi

2. **Copia il Value:**
   - Clicca su "Value" (non Minecraft URL!)
   - Copia la stringa Base64 (esempio: `eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6...`)

3. **Incolla nel config.yml:**
   ```yaml
   gift-block:
     type: PLAYER_HEAD
     display-name: "&c&lChristmas Gift"
     head-texture: "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDFjYTMxMWFmYTlhOTBmYzBmZmFkYWQzMGNkZTA5NGI4M2RmYTQxNjRkZGM0ZjhmZTA5MzUxZDlhYzQ5YzY4MyJ9fX0="
   ```

4. **Riavvia o usa `/cg reload`**

✨ **Funziona immediatamente senza plugin aggiuntivi!**

## 📦 Installazione sul Server

1. Copia `target/ChristmasGift-1.0.0.jar` nella cartella `plugins/` del tuo server
2. (Opzionale) Installa PlaceholderAPI se vuoi i placeholder
3. Avvia il server
4. Configura `config.yml` e `messages.yml` nella cartella `plugins/ChristmasGift/`
5. Imposta una texture personalizzata (vedi sopra)
6. Usa `/cg give` per ottenere un blocco regalo

## 🎮 Comandi Principali

- `/cg give` - Ottieni un blocco regalo (admin)
- `/cg stats` - Mostra le tue statistiche
- `/cg leaderboard` - Mostra la classifica (admin)
- `/cg remove <x> <y> <z>` - Rimuovi un blocco regalo (admin)
- `/cg removeall` - Rimuovi tutti i blocchi (admin)
- `/cg reload` - Ricarica la configurazione (admin)

## 📝 Permessi

- `christmasgift.use` - Comandi base (default: tutti)
- `christmasgift.admin` - Comandi admin (default: op)

## 🎄 Esempio Configurazione Natalizia

```yaml
gift-block:
  type: PLAYER_HEAD
  display-name: "&c&l🎁 &a&lRegalo di Natale &c&l🎁"
  # Texture di un regalo natalizio (esempio da minecraft-heads.com)
  head-texture: "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDFjYTMxMWFmYTlhOTBmYzBmZmFkYWQzMGNkZTA5NGI4M2RmYTQxNjRkZGM0ZjhmZTA5MzUxZDlhYzQ5YzY4MyJ9fX0="
  
  replace-after-claim: true
  
  replacement-block:
    type: AIR
```

## ℹ️ Supporto PlaceholderAPI (Opzionale)

Per abilitare i placeholder:

1. Scarica PlaceholderAPI da Spigot
2. Rinomina `ChristmasGiftExpansion.java.disabled` → `ChristmasGiftExpansion.java`
3. Decomm enta il codice PlaceholderAPI in `ChristmasGift.java`
4. Aggiungi la dipendenza al `pom.xml`
5. Ricompila con `mvn clean package`

Placeholder disponibili:
- `%christmasgift_found%` - Regali trovati
- `%christmasgift_rank%` - Posizione in classifica
- `%christmasgift_<position>_name%` - Nome al posto X
- `%christmasgift_<position>_displayname%` - Display name al posto X
- `%christmasgift_<position>_value%` - Valore al posto X

---

**Il plugin è completamente funzionante con texture personalizzate!** 🎄🎁

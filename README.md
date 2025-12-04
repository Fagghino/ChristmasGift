# ChristmasGift 🎁

[![Version](https://img.shields.io/badge/version-1.0.0-blue.svg)]()
[![Minecraft](https://img.shields.io/badge/minecraft-1.20+-green.svg)](https://www.minecraft.net/)
[![License](https://img.shields.io/badge/license-MIT-yellow.svg)](LICENSE)

**ChristmasGift** è un plugin Minecraft per Spigot/Paper che permette di creare una caccia al tesoro natalizia con blocchi regalo nascosti nel mondo. I giocatori possono cercare e raccogliere i regali per competere nella classifica globale!

## 📋 Caratteristiche

- **🎁 Blocchi Regalo Personalizzabili**: Usa blocchi normali o teste con texture Base64 custom
- **🎨 Texture da minecraft-heads.com**: Aggiungi texture personalizzate senza plugin aggiuntivi
- **📊 Sistema di Classifica**: Traccia quali giocatori hanno trovato più regali
- **💾 Persistenza Dati**: Salvataggio automatico in `data.yml`
- **🏆 Statistiche Giocatore**: Ogni player può vedere quanti regali ha trovato
- **🔄 Sostituzione Blocchi**: Configura cosa succede dopo che un regalo viene raccolto
- **📝 Messaggi Personalizzabili**: Tutti i messaggi sono configurabili in `messages.yml`
- **🔌 PlaceholderAPI Integration**: Supporto per placeholder opzionale
- **⚙️ Comandi Completi**: Gestione admin con comandi intuitivi
- **🎯 Tab Completion**: Autocompletamento comandi per facilità d'uso

## 🚀 Installazione

1. **Scarica il file `.jar`** del plugin
2. **Inseriscilo nella cartella `plugins/`** del tuo server
3. **(Opzionale)** Installa PlaceholderAPI per i placeholder
4. **Avvia/riavvia il server**
5. **Configura** `config.yml` e `messages.yml` nella cartella `plugins/ChristmasGift/`
6. **Imposta una texture** personalizzata (vedi sotto)
7. Usa `/cg give` per ottenere blocchi regalo

## 🎨 Texture Personalizzate

### Come Usare Texture Custom da minecraft-heads.com

Il plugin supporta **texture Base64** per le teste dei blocchi regalo senza bisogno di plugin esterni!

### Passaggi per Aggiungere Texture:

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

## 🎮 Comandi

| Comando | Descrizione | Permesso | Alias |
|---------|-------------|----------|-------|
| `/cg give` | Ottieni un blocco regalo | `christmasgift.admin` | `/gift give`, `/christmasgift give` |
| `/cg stats` | Mostra le tue statistiche | `christmasgift.use` | `/gift stats`, `/christmasgift stats` |
| `/cg leaderboard` | Mostra la classifica globale | `christmasgift.admin` | `/cg lb`, `/gift lb` |
| `/cg remove <x> <y> <z>` | Rimuovi un blocco regalo specifico | `christmasgift.admin` | `/gift remove` |
| `/cg removeall` | Rimuovi tutti i blocchi regalo | `christmasgift.admin` | `/gift removeall` |
| `/cg reset <player>` | Reset statistiche di un giocatore | `christmasgift.admin` | `/gift reset` |
| `/cg resetall` | Reset di tutte le statistiche | `christmasgift.admin` | `/gift resetall` |
| `/cg reload` | Ricarica la configurazione | `christmasgift.admin` | `/gift reload` |

**Alias principali:** `/cg`, `/gift`, `/christmasgift`

## 🔑 Permessi

| Permesso | Descrizione | Default |
|----------|-------------|---------|
| `christmasgift.use` | Permette di usare i comandi base | Tutti |
| `christmasgift.claim` | Permette di raccogliere i blocchi regalo | Tutti |
| `christmasgift.admin` | Permette di usare i comandi admin | OP |

## ⚙️ Configurazione

### config.yml

```yaml
gift-block:
  type: PLAYER_HEAD                    # PLAYER_HEAD o blocco normale
  display-name: "&c&l🎁 &a&lRegalo di Natale &c&l🎁"
  
  # Texture Base64 da minecraft-heads.com (solo per PLAYER_HEAD)
  head-texture: "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDFjYTMxMWFmYTlhOTBmYzBmZmFkYWQzMGNkZTA5NGI4M2RmYTQxNjRkZGM0ZjhmZTA5MzUxZDlhYzQ5YzY4MyJ9fX0="
  
  replace-after-claim: true            # Sostituisci il blocco dopo il claim
  
  replacement-block:
    type: AIR                          # Blocco sostitutivo (AIR per rimuoverlo)
```

### messages.yml

Tutti i messaggi del plugin sono personalizzabili:

```yaml
prefix: "&c[ChristmasGift] &r"
gift-claimed: "&aHai trovato un regalo!"
gift-already-claimed: "&cQuesto regalo è già stato raccolto!"
no-permission: "&cNon hai il permesso per fare questo!"
stats-message: "&aHai trovato &e{found}&a regali!"
# ...altri messaggi...
```

## 🔌 PlaceholderAPI (Opzionale)

Se hai PlaceholderAPI installato, sono disponibili questi placeholder:

| Placeholder | Descrizione |
|-------------|-------------|
| `%christmasgift_found%` | Regali trovati dal giocatore |
| `%christmasgift_rank%` | Posizione in classifica |
| `%christmasgift_<position>_name%` | Nome al posto X |
| `%christmasgift_<position>_displayname%` | Display name al posto X |
| `%christmasgift_<position>_value%` | Valore al posto X |
| `%christmasgift_total_gifts%` | Numero totale di regali nel server |
| `%christmasgift_claimed_gifts%` | Numero di regali trovati in totale |
| `%christmasgift_unclaimed_gifts%` | Numero di regali non ancora trovati |

**Esempio:** `%christmasgift_1_name%` mostra il nome del primo in classifica

## 📖 Come Funziona

1. **L'admin ottiene un blocco regalo:**
   ```
   /cg give
   ```

2. **L'admin piazza il blocco regalo** dove vuole nel mondo

3. **I giocatori cercano e trovano** i blocchi regalo

4. **Click destro sul blocco** per raccoglierlo (primo arrivato, primo servito!)

5. **Il blocco viene sostituito** o rimosso (configurabile)

6. **Le statistiche vengono salvate** automaticamente in `data.yml`

7. **Usa `/cg stats`** per vedere i propri progressi

8. **Usa `/cg leaderboard`** per vedere la classifica globale

## 📦 Requisiti

- **Java**: 17+
- **Spigot/Paper**: 1.20.1+
- **Maven**: (per la compilazione)

## 🔨 Compilazione

Per compilare il plugin da sorgente:

```bash
# Clona il repository
git clone https://github.com/franchino961/ChristmasGift.git
cd ChristmasGift

# Compila il progetto
mvn clean package
```

Il file `.jar` compilato si troverà in `target/ChristmasGift-1.0.0.jar`

## 📚 Dipendenze Maven

```xml
<dependencies>
    <!-- Spigot API -->
    <dependency>
        <groupId>org.spigotmc</groupId>
        <artifactId>spigot-api</artifactId>
        <version>1.20.1-R0.1-SNAPSHOT</version>
        <scope>provided</scope>
    </dependency>
    
    <!-- PlaceholderAPI (Opzionale) -->
    <dependency>
        <groupId>me.clip</groupId>
        <artifactId>placeholderapi</artifactId>
        <version>2.11.3</version>
        <scope>provided</scope>
    </dependency>
</dependencies>
```

## 🎯 Struttura del Progetto

```
ChristmasGift/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── franchino961/
│       │           └── christmasgift/
│       │               ├── ChristmasGift.java           # Classe principale
│       │               ├── commands/
│       │               │   └── ChristmasGiftCommand.java # Gestione comandi
│       │               ├── listeners/
│       │               │   └── GiftBlockListener.java   # Listener eventi
│       │               ├── managers/
│       │               │   ├── DataManager.java         # Gestione dati
│       │               │   └── LeaderboardManager.java  # Gestione classifica
│       │               └── placeholders/
│       │                   └── ChristmasGiftExpansion.java # PAPI
│       └── resources/
│           ├── plugin.yml
│           ├── config.yml
│           └── messages.yml
├── pom.xml
└── README.md
```

## 🐛 Troubleshooting

### Il blocco non ha la texture personalizzata

1. Verifica che hai copiato il "Value" corretto da minecraft-heads.com (non l'URL!)
2. Assicurati che `type: PLAYER_HEAD` sia impostato nel config.yml
3. Usa `/cg reload` dopo aver modificato la configurazione
4. Controlla che la stringa Base64 sia completa e tra virgolette

### I giocatori non possono raccogliere i regali

1. Verifica che abbiano il permesso `christmasgift.claim`
2. Controlla che i regali non siano già stati raccolti
3. Verifica nei log del server per eventuali errori

### Le statistiche non vengono salvate

1. Controlla che il plugin abbia i permessi di scrittura nella cartella `plugins/ChristmasGift/`
2. Verifica che il file `data.yml` non sia corrotto
3. Controlla i log del server per errori di I/O

## 💡 Esempio Configurazione Completa

```yaml
gift-block:
  type: PLAYER_HEAD
  display-name: "&c&l🎁 &a&lRegalo di Natale &c&l🎁"
  head-texture: "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDFjYTMxMWFmYTlhOTBmYzBmZmFkYWQzMGNkZTA5NGI4M2RmYTQxNjRkZGM0ZjhmZTA5MzUxZDlhYzQ5YzY4MyJ9fX0="
  
  replace-after-claim: true
  
  replacement-block:
    type: AIR
```

## 📄 Licenza

Questo progetto è rilasciato sotto licenza MIT. Vedi il file [LICENSE](LICENSE) per maggiori dettagli.

## 👤 Autore

**Franchino961**

- GitHub: [@Franchino961](https://github.com/Franchino961)

## 🤝 Contribuire

Contributi, problemi e richieste di funzionalità sono benvenuti!
Sentiti libero di controllare la [pagina delle issues](../../issues).

## 📝 Changelog

### v1.0.0
- 🎉 Release iniziale
- ✅ Sistema completo di blocchi regalo
- ✅ Supporto texture Base64 custom
- ✅ Sistema di classifica e statistiche
- ✅ Integrazione PlaceholderAPI
- ✅ Comandi admin completi
- ✅ Configurazione flessibile
- ✅ Messaggi personalizzabili

## ⭐ Supporto

Se trovi utile questo plugin, considera di lasciare una stella ⭐ al repository!

## 🔗 Link Utili

- [minecraft-heads.com](https://minecraft-heads.com/) - Database di teste personalizzate
- [PlaceholderAPI](https://www.spigotmc.org/resources/6245/) - Plugin per placeholder
- [Spigot Resources](https://www.spigotmc.org/) - Risorse per server Minecraft

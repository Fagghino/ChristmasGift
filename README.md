# ChristmasGift Plugin

Un plugin Minecraft per Spigot/Paper che permette di creare una caccia al tesoro natalizia con blocchi regalo nascosti.

## Requisiti

- Java 17
- Minecraft 1.20.x
- Spigot/Paper 1.20.x
- Maven per la compilazione

## Dipendenze Opzionali

- **PlaceholderAPI** - Per i placeholder (https://www.spigotmc.org/resources/6245/)

## Supporto Texture Custom

Il plugin supporta **texture Base64** per le teste dei blocchi regalo senza bisogno di plugin esterni!

Per usare una testa personalizzata:
1. Vai su https://minecraft-heads.com/ o simili
2. Cerca la testa che vuoi
3. Copia il valore "Value" (Base64)
4. Incollalo nel `config.yml` sotto `gift-block.head-texture`

Esempio:
```yaml
gift-block:
  type: PLAYER_HEAD
  head-texture: "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDFjYTMxMWFmYTlhOTBmYzBmZmFkYWQzMGNkZTA5NGI4M2RmYTQxNjRkZGM0ZjhmZTA5MzUxZDlhYzQ5YzY4MyJ9fX0="
```

## Compilazione

```bash
mvn clean package
```

Il file JAR compilato si troverà nella cartella `target/`.

## Installazione

1. Scarica PlaceholderAPI (opzionale ma consigliato)
2. Metti il file JAR del plugin nella cartella `plugins/` del tuo server
3. Riavvia il server
4. Configura il plugin nei file `config.yml` e `messages.yml`
5. Imposta una texture custom se desiderato (vedi sezione sopra)

## Comandi

### Comandi per tutti i giocatori:
- `/christmasgift stats` - Mostra le tue statistiche (quanti regali hai trovato)

### Comandi per amministratori:
- `/christmasgift give` - Ricevi un blocco regalo da piazzare
- `/christmasgift leaderboard` (o `/cg lb`) - Mostra la classifica
- `/christmasgift remove <x> <y> <z>` - Rimuovi un blocco regalo specifico
- `/christmasgift removeall` - Rimuovi tutti i blocchi regalo
- `/christmasgift reload` - Ricarica la configurazione

Alias disponibili: `/cg`, `/gift`

## Permessi

- `christmasgift.use` - Permette di usare i comandi base (default: tutti)
- `christmasgift.admin` - Permette di usare i comandi admin (default: op)

## PlaceholderAPI

Il plugin supporta i seguenti placeholder:

- `%christmasgift_found%` - Numero di regali trovati dal giocatore
- `%christmasgift_rank%` - Posizione del giocatore nella classifica
- `%christmasgift_<position>_name%` - Nome del giocatore in posizione X
- `%christmasgift_<position>_displayname%` - Display name del giocatore in posizione X
- `%christmasgift_<position>_value%` - Numero di regali trovati dal giocatore in posizione X

Esempio: `%christmasgift_1_name%` mostra il nome del primo in classifica

## Configurazione

### config.yml

Il file `config.yml` permette di configurare:
- Tipo di blocco regalo (normale o player head)
- Texture Base64 per head personalizzate (da minecraft-heads.com)
- Nome visualizzato del blocco
- Se sostituire il blocco dopo il claim
- Tipo di blocco sostitutivo

### messages.yml

Tutti i messaggi del plugin sono personalizzabili nel file `messages.yml`.
Supporta i colori con il simbolo `&`.

## Come funziona

1. L'admin usa `/christmasgift give` per ottenere un blocco regalo
2. L'admin piazza il blocco regalo dove vuole nel mondo
3. I giocatori trovano il blocco e ci cliccano sopra con il tasto destro
4. Il primo giocatore che clicca riscuote il regalo
5. Il blocco viene sostituito o rimosso (configurabile)
6. Le statistiche vengono salvate e aggiornate

## Funzionalità principali

- ✅ Supporto per blocchi normali e player head custom con texture Base64
- ✅ Texture personalizzate da minecraft-heads.com (senza plugin aggiuntivi!)
- ✅ Sistema di classifica
- ✅ Statistiche per giocatore
- ✅ Salvataggio automatico dei dati
- ✅ Placeholders per PlaceholderAPI (opzionale)
- ✅ Messaggi completamente personalizzabili
- ✅ Supporto per blocchi sostitutivi
- ✅ Comandi con tab completion

## Supporto

Per bug o suggerimenti, apri una issue su GitHub.

## Licenza

Vedi file LICENSE

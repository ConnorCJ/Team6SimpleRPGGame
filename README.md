# SER 322 PROJECT - SIMPLE RPG GAME

## Description
This game is a simple RPG taking the player through a journey between towns until a final boss is reached. Along the way items 
like weapons, armor, food, etc may be bought to assist in battles and heal a player. Between each town the player is faced with 
many events. These events include enemy encounters (battles) and tests of the players attributes that provide 
opportunities to gain/lose gold, HP, and EXP. Once the player reaches the final town they will face a boss enemy in a final battle
to put their skills to the test!

## Database
**Entities**
- `Character`
- `Enemy`
- `Item`
- `Town`
- `Event`
- `ItemInShop`
- `ItemOnChar`

The database used must be in the project file in order to start. The database file contains enemies, towns, events, items, and shops preloaded that will be used in the game. The player will enter a name
for their character which will be added to the `Character` table in the database with base attributes for starting the game. 

Towns are loaded in order using the townID attribute. Each town has specific items that can be purchased in it. The `ItemsInShop` table stores what itemIDs are available in each town. As a character gains
items they are added to the `ItemsOnChar` table. When an item like potion or food is used they are deleted from the table. 

The `Enemy` table contains all enemies that can be faced. Enemies have a townID indicating which town they may be encountered after. These encounters are triggered by events. In the `Event` table if an encounter is a monster the eventType is a 1. 

A character can be saved at any time. Clicking the save button will allow a player to close the program and continue again from the spot they save at. When save is clicked the character database is updated saving the town, items, attributes, etc of the character. 

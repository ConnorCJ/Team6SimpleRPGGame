# SER 322 PROJECT - SIMPLE RPG GAME

## Description
This game is a simple RPG taking the player through a journey between towns until a final boss is reached. Along the way items 
like weapons, armor, food, etc may be bought to assist in battles and heal a player. Between each town the player is faced with 
many events. These events include enemy encounters (battles) and tests of the players attributes that provide 
opportunities to gain/lose gold, HP, and EXP. Once the player reaches the final town they will face a boss enemy in a final battle
to put their skills to the test!

## Database
The database used must be in the project file in order to start. The database file contains:

**Entities**
- `Character`
- `Enemy`
- `Event`
- `Item`
- `Town`
- `ItemInShop`
- `ItemOnChar`

### Character
The player will enter a name for their character which will be added to the `Character` table in the database with base attributes for starting the game. A character can be saved at any time. When the save button is pressed, the characters current town, attributes, health, etc are updated in the database and saved. 

### Town
Towns are loaded in order using the townID attribute. Each town has specific items that can be purchased in it. The `ItemsInShop` table stores what itemIDs are available in each town using itemID and townID. As a character gains
items they are added to the `ItemsOnChar` table which stores the charID along with their itemIDs. When an item like potion or food is used they are deleted from that table. 

### Enemy
The `Enemy` table contains all enemies that can be faced. Enemies have a townID indicating which town they may be encountered after and an enemyID to discern them. Enemies also have the attributes a character has, such as attack, defense, HP, and EXP used in battles. Encounters with enemies are triggered by events. In the `Event` table if an encounter is an enemy the eventType is a 1. 

### Event
The `Event` table contains all possible events that can be faced by a character. Events have descriptions that explain them to the character and outcomes based on whether the character wins or loses. The database is checked to see if the characters attribute for the given event is higher or lower than the needed eventThreshold. If the player wins they are rewarded. What they recieve is stored in event stat and the amount is stored in eventValue. 

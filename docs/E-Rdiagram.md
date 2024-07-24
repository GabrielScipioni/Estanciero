# Entity-Relationship Diagram


### Index
1. [Diagram](#Diagram)
2. [Explanation](#Explanation)
   - [User](#user)
   - [Game](#game)
   - [Player](#player)
   - [Deck](#Deck)
   - [Card](#Card)
   - [Player Card](#Player_Card)
   - [Board player](#board_player)
   - [Square](#square)
   - [Movement Type](#movement-type)
   - [Card Type](#card-type)

---
## Diagram
![Diagrama UML](/docs/DER/DEREstanciero.drawio%20(1).png)
---
## Explanation
We will proceed to explain the database tables that were seen previously. We will start with the `user class`

### User
The user has a username, an email and password that will be saved to log in to the application with a specific account
### Game
Contains general info from each game session such as the last date the game was played,
a unique identifier, game status (if the session was finished already or not) and which player had the
current turn
### Board
The board contains the data of what game is playing, every square, every player, and a morgaged value that is a boolean, so the board basically contains every state of
the actual game, with their players, using also the boolean to mark down properties that are under morgage.
### Board Upgrade
This middle table contains the relations bewteen Board and Upgrade Types, witht his we can store the Upgrades of every property on the map.
### Player
Stores information about each player participating in the game whether human or machine-controlled
- **game id** this game ID serves to associate each player with the specific game session they are part of,
  allowing for multiple concurrent game sessions to be managed within the database
- **human** this field indicates whether the player is human or a bot
### Deck
This table stores the cards from the Cards table, managing their specific order and sequence.
### Card
This table contains a type of action indicating the task to be performed based on the card, along with a descriptive message. It also includes Square ID and Amount, indicating the destination square if the card requires moving to a certain square, and the penalty amount if applicable.
### Player_card
PlayerID, which refers to the Players table mentioned earlier, and a CardID, also mentioned previously, which refers to the "Cards" table. Additionally, there's a "Used" column, this column is specific for certain cases. There are different types of cards in the game, but there's a specific type that we can keep, and we can even sell it. This card is the "Get Out of Jail" card, so it will belong to a player, have a specific card ID, and indicate whether it has been used or not.
### Board_Player
The `Board_Player` table acts as a bridge between the `Board` and `Player` tables, enabling many-to-many relationships. It records connections between specific players and boards through two key columns: **BoardID** and **PlayerID**. This setup allows players to participate in multiple games across different boards and enables boards to host multiple players simultaneously.
### Square
Provides detailed information about each square on the game board.
Includes data such as the square's unique identifier, name, type id, mortgage value and
property-related info (zone id and province id) that will be nullable in case the square type
do not represent a property
### Rent
`Rent` manages the data of the event of ending up in a `Square` that has the type of a property, so when a player lands on this type of square, a certain amount of rent
will be substracted from the players money account, thus ending in the bank, so we store the data of this _rent_, meaning we store the square it belongs in, the _basic_ amount of money to pay, the amount of upgrades, and finally the calculated price.
### Square Type
This stores what type of Square we are representing, for example Property, Event, Tax, Train Station, etc.
### Upgrade Type
Were the posible types of upgrades are stored, so we have _Chacras_ and _Estancias_, each of them respectedly are represented in the physical board using diferent plastic houses, using an id we can determine in other tables what type of upgrade we have and wich house are we adding, if its Chacras or if its Estancia
## Upgrade Purchase Value
This table stores the diferent cost of upgrade of every different property in the game, since low cost properties have cheap prices as rather compared to higher denomination properties like Cordoba as an example.
This stores the property as well as the upgrade type, with the cost of the upgrade.
### Movement Type
Stores different types of movements triggered by cards in the game,
that instruct players to move to specific squares on the board
### Card Type
Represents the different types of cards in the game, such as Luck cards or Chance cards

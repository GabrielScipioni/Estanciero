# _*Estanciero's Project: Abstractions*_

# Index
   - [Square](#square)
   - [Player](#player)
   - [Bot](#bot)
   - [Transaction](#transaction)
   - [Card](#card)
   - [Card movement](#CardMovement)
   


## _*Square*_

The type of square is also abstracted, focusing on the main action performed on each square. It may have a method executeSquare() that provides actions such as charging a certain amount (a transaction), holding you from taking turns until a flag indicates it (rolling doubles), or paying you for passing through that square (not necessarily landing on it). In this case, we abstract away from the different types of squares.

There are squares such as:

* Start: Pays you an amount.
* Unowned Province: Offers you the possibility to purchase this square and become its owner.
* Unowned Company: Offers you the possibility to purchase this square and become its owner.
* Owned Province: Requires you to pay a certain amount to the owner.
* Among others

With these "summarized" data, we can further abstract and focus on squares:

* Payment
* Collection
* Card
* Turn loss
* Send to another square

However, when trying to abstract even further, we realize that all these "types" are part of a class that can provide data or methods to all these "types".
For this purpose, the Square abstraction exists to abstract as much as possible from the different types of squares.

The executeSquare() method would have other methods inside ensuring that other methods are executed to ensure its different actions, such as:

- `pay(player1, player2, amount){}`
- `drawCard(cardType, player){}`
- `holdPlayer(player){}`
- `releasePlayer(player){}`
- `movePlayer(player, square){}`



***
## _*Player*_

When implementing the abstraction of the _**'Player'**_ class, our focus is on defining generic methods and attributes that all players must possess regardless of whether it's a bot or the user.

We will reinforce this abstraction through _**class inheritance**_, providing flexible and reusable code to define the general behavior of players, while allowing subclasses (User and Bot) to specifically implement certain methods according to their particular needs.
For example, the Player class can have the following methods:

- `bankruptCheck()`
- `tradePlayer()` 
- `buy()`
- `upgrade()`

Of these, `tradePlayer()` will be abstract methods whose implementation will be re-implemented by the Bot and User classes according to their specific needs and context.
On the other hand, `buy()` ,` upgrade()` and `bankruptCheck()` represent standard operations, so they are absolute methods that both players will implement regardless of their nature.


***
## _*Bot*_

In the case of the _**Bot**_ class, the necessity becomes apparent to assign various behavior strategies to the bot for each difficulty level, in a simple and abstract manner.

To achieve this abstraction is attained, for instance, by **defining a common interface** (Strategy) that all strategies implement. Then, the bot interacts with the strategies through this common interface, **without needing to know the specific details of each strategy**.

Thus, instead of directly assigning a specific strategy to the bot (such as EasyStrategy), the bot can receive an object of type Strategy as a parameter.

With that being done, the bot doesn't need to know the concrete implementations of the strategies; it simply interacts with the common Strategy interface, thereby abstracting the assignment of difficulties to each Bot instance.

***

## _*Transaction*_

Within the _**transaction**_ abstraction we generalize the transactions used consistently throughout the game. Particularly useful as they pertain to the same entity, the _**Bank**_, they have the same inputs and must execute a series of basic steps to ensure compliance with the ACID properties (Atomicity, Consistency, Isolation, Durability).


In summary, this allows us to reuse code by defining common attributes and methods that will be specified by each concrete class extending from it, implementing the necessary logic for each particular transaction. For this reason, both the Bank and the players are abstracted from knowing the details of the transactions carried out and are limited to using the results of those transactions.


Among the common attributes, we can name:


- `propertyValue` 
- `mortgageValue`
- `rentValue`


Among the common behaviors, we can name:


- `ExecuteTransaction()` 
- `CommitTransaction()`


The `CommitTransaction()` method is a standard method that returns the state of the transaction, attempting to commit it if it has been successfully executed or rolling it back and handling any exceptions that may occur due to unexpected errors. Its return type would be a boolean.


The `ExecuteTransaction()` method will be overridden by the classes that extend the Transaction abstraction and will contain the specific logic for each type of transaction. It will return a Boolean data type, using the standard CommitTransaction() method as the final line of the block.

***

## _*Card*_

There are two reasons to make this abstraction because there exist different _**types of cards**_ (luck and destiny) and __**types of movement**_ (pay other players, pay to bank, go to jail, etc).  Since the number of combinations for type of card and type of movement is large, if we were to add a new particular case, it would be necessary to duplicate the definitions for the possible combinations.
Some common attributes are message (reading to show to the player), amount (which works as a generic attribute, that could refer to money or amount of steps).
We were able to identify two common methods, like:
- `getMessage()`: No show the message in prompt window
- `executeCard()`: That would allow the card to execute the necessary movements, according to the type.

***
## _*CardMovement*_

Card movement refers to the movement or action that a card can have. These could be money movement, go to x square, charge other players, between others. Since we’ve recognized 9 movements and we could imagine future type additions, it's useful to abstract from these types and the way that are executed. So every movement of a particular card could be executed within it, independent from its type.
Every movement has a type and an execution process, its name would be `executeMovement()`, so we could ask a movement to execute independently from its type.


























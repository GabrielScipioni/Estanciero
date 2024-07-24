# *The Estanciero User Experience Explained*

We can navegate accross the game using **numbers**, because the game runs in **Console**. So anytime a *option* is made, we choose a number, **the number in this document is not final and is subject to change in the future.**

![FlowChartPng.drawio.png](/docs/UXDiagram/FlowChartPng.drawio.png)

## *Everything before the start of the Game*

### _Account_

Our account will manage the data of the played games we have, so having an account is key for being able to continue playing the game later on.

The first thing we have on the screen is a choice between Loging in into your pre-existing account or Register a new account if you dont have one.

In the case of choosing a `1) Login` option, we can put our credentials in to have access to our account. 

In the other hand, if we dont have an account we can `2) Register` to create one.

### _Starting Menu_

So the start menu has 3 options, `1) Show the Rules` of the Game for the people that doesnt know how to play it, we have all the information aviable in a file about the game.
We have an option to `2) Start` the game, and an option to `3) Exit`

### _Game Start_

2 Options will appear if we choose to start the game, the first one is
`1) New Game`, and the second one will be `2) Load Game`

If we choose the *New Game* option we can start a fresh game and later on choose a save file to store the game progress up to that point.

Alternatively we can just choose an existing save file by choosing *load game*.


## --------------------------------------------------------

![GameMenu.drawio.png](/docs/UXDiagram/GameMenu/GameMenu.drawio.png)

## *Every Game Menu Option (Paths Included!)*
Ok lets start by the basics.
So the Console-Guided-Menu-System has a set of diferent states, that are influenced by the player's current situation that he is involved in.

The player will always have some fixed options that he can choose from, some of them with a certain requierement like `End Turn` or `Throw Dices`, since you can only "End a Turn" if certain actions have been taken place, like, for example having the dices thrown already and landed somewhere, after the ***event*** of the Square you landed in triggers, you can only then *end your turn*. 

Same goes for the "Trow Dice" action, you can only trow your dice again if you have gotten a pair of *doubles*, so after throwing the dice once and not getting doubles the "trow dice" option is not aviable as a fixed option anymore.

With that said lets look at some of the **Fixed** options or choices that we have inside the game menu:

### _Fixed Options or Choices_

***Throw Dice:***
You throw the dices and in return get 2 values, the first dice and the second, originally thought as a **array** of 2 places **(May be subject to change in the future)**, The dice always returns an int array with 2 values, so when we throw the dice we move by the **sum** of these 2 values and **if** we get doubles, we can throw again instead of ending a turn.

*Its important to notice that a streak of 3 doubles results in going to Jail*

***Statistics***
the stats option will show you information about the game, like a list of properties and railroads you own, with the upgrade lvl, a list of cards for getting out of jail if you have any, and overall views of the current match like what properties others have.

This probably will be a recurring option to check for the state of the game

*notice that if we completed at least 1 province we can get an alternative option to upgrade a property, this will result in a list of every property that meets the requierements for upgrading , meaning that having more than 1 upgrade in comparison with the rest of the properties of this province is not posible.*


`If the player owns 1 or more properties`

***Auction a Property***
Maybe you are running low on money and wanna excange one of your properties for some cash at the bank, the bank will start an auction offering your property and having other players give an offer, with this you quit being the owner and give all your upgrades with it to the bank, the current property will sell again like its brand new and with 0 upgrades for the next buyer

***Morgage a Property***
By choosing morgage you get less money than selling it to the bank but have an option to pay up the morgage later on to get your property back

***Exit***
By choosing exit you leave to the main menu with 2 posible choices, you either `Save` your game within a save file or choose not to, not saving the match and losing all the progress.

### _Event Enabled Options_

`By landing on a Property you'll get:`

***Buy Property*** 
`If the property has no owner` you can always buy it for a price, if you choose to pay up, the property will be added in your list of properties, and if its the final one to own the *zone* you can also buy it and upgrade it on the same turn

***Upgrade Property**
`If im the owner and also own the whole zone` I can choose to upgrade the current property I landed on, having levels of upgrade going from 0 to 4,

***Offer to Buy this property***
`By landing on a property thats owned by another player` we can *(after paying rent of course)* offer to buy him the property he owns, maybe we find it usefull for our collection or for tactical advantage.

If we choose this option a separate menu will open with our offer to pay and a response from our rival.

### _Rest Square_

***Skip this turn***
If we landed on the parking lot we have the option to skip this turn for a total of `2 times`, **ONLY** if we choose to `skip the turn` also we are requiered to **throw** the dices once, `if we get doubles we cant skip the turn and we are forced to move automatically` acording to the dices result.

### _In Jail_

***Skip this turn***
Its also posible to `skip` a turn while on Jail, we could choose to not trow dices by playing tactical on key ocasions, anyways the option will be open if we are on jail

***Use a _Get out of Jail_ Card***
By using one of these cards we can skip being on Jail and simply throwing the dice again and keep playing, its like nothing ever happened, except for the fact that we are now in the jail square...
and we also didnt pick up the extra cash at the *start* line.

***Buying a _Get out of Jail_ Card***
We can offer to pay up an ammount to a player that `owns` one of these cards, by picking this option we can see who owns it and offer up, if noone of the rival players shows up, it because noone yet owns one of these cards sadly

### _Trade Offers !_
So another player wants something we have, its either a `Property` or a `"Get out of Jail" Card`, we will have a small option to warn us about a new ***Trade request*** and inside this option we will be able to choose if we want to either:
`1) Accept`
or
`2) Decline`

Either way this is part of the Trading interface, it only triggers if another player lands on *Jail* and we have a card, or if a player is *Interested* in one of our properties.
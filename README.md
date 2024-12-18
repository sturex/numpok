## Number Poker Game Research and Development
### Project Goal
The main goal of the project is to develop Core Abstractions to handle games with hidden information.

### Current State

A Spring project containing [GraphQL controller](https://github.com/sturex/numpok/blob/master/src/main/java/com/numpok/controller/GraphQlController.java) to run NumPok games with [SelfPlay service](https://github.com/sturex/numpok/blob/master/src/main/java/com/numpok/service/SelfPlayServiceImpl.java).
Three very [simple AI bots](https://github.com/sturex/numpok/blob/master/src/main/java/com/numpok/game/ai/DumbBots.java) are implemented at the moment: Random, Rule-Based and Fraud.

The development of [Core package](https://github.com/sturex/numpok/tree/master/src/main/java/com/numpok/core) has just started.

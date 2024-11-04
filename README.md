## Pokedex

This is a small project that uses the [Pokémon TCG API](https://dev.pokemontcg.io/) to display a list of 
Pokémon cards based on selected card sets. The user can browse through the cards and view detailed information including
the card's image, name, artist, rarity and price on [CardMarket](https://www.cardmarket.com/en).

### Screens

- **Home**: Displays a list of card sets based on a list chosen by the user.
- **Set List**: Displays a list of cards based on the selected card set.
- **Card Detail**: Displays detailed information about the selected card.

### Technologies

This app uses a fairly modern Android stack consisting of:
- [Kotlin](https://kotlinlang.org/) (Language)
- [Compose/Material3](https://m3.material.io/develop/android/jetpack-compose) (UI)
- [Hilt](https://dagger.dev/hilt/) (Dependency Injection)
- [Coil](https://coil-kt.github.io/coil/) (Image loading)
- [Retrofit](https://square.github.io/retrofit/) (Networking)
- [Proto Datastore](https://developer.android.com/topic/libraries/architecture/datastore) (Data persistence)

### Setup

To run this project, you need to create a `local.properties` file in the root directory of the project and add the following line:
```
POKEMON_TCG_API_KEY="your_api_key_here"
```
You can generate this yourself at [Pokémon TCG API](https://dev.pokemontcg.io/) or sending me a message.

After that, you can build either debug or release versions of the app.

### Testing

You can either run the tests from Android Studio or run the following command in the terminal from the root directory of the project:

- Instrumented Tests
```shell
./gradlew :app:connectedDebugAndroidTest
```

- Unit Tests
```shell
./gradlew :app:testDebugUnitTest
```

# GameDB
A simple game database application

## Introduction
GameDB is an Android Kotlin application that shows a list of games. The data source of this application is from RAWG REST API web service. This app fetches the game list from the network using the Retorfit library and Moshi to handle the deserialization of the returned JSON to Kotlin data objects.
The app also leverages ViewModel, LiveData, Data Binding with binding adapters, Room for the database, Coroutines, and Glide to load and cache images by URL.

## Features
- Home -> Display top 10 games on this month
- Search -> searching for games
- Favourites -> Display favourited games
- Detail -> Display detail information about the game and add/remove the game from Favourites

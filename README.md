# GameDB
A simple game database application

![SplashScreen](https://user-images.githubusercontent.com/22924857/111578292-7334cc80-87e6-11eb-95e5-29014567a80d.png)

## Introduction
GameDB is an Android Kotlin application that shows a list of games. The data source of this application is from RAWG REST API web service. This app fetches the game list from the network using the Retorfit library and Moshi to handle the deserialization of the returned JSON to Kotlin data objects.

The app also leverages ViewModel, LiveData, Data Binding with binding adapters, Room for the database, Coroutines, and Glide to load and cache images by URL.

## Features
- Home -> Display top 10 games on this month

![Home](https://user-images.githubusercontent.com/22924857/111578300-79c34400-87e6-11eb-9747-92454374851f.png)

- Search -> searching for games

![Search](https://user-images.githubusercontent.com/22924857/111578327-86479c80-87e6-11eb-9bc4-c4bd6508a8d8.png)
![Search with keyword](https://user-images.githubusercontent.com/22924857/111578342-8ba4e700-87e6-11eb-96e7-92283e0c4857.png)

- Favourites -> Display favourited games

![Empty Favourites](https://user-images.githubusercontent.com/22924857/111578412-ad05d300-87e6-11eb-9ce0-11b94e99c235.png)
![Favourites](https://user-images.githubusercontent.com/22924857/111578459-c3ac2a00-87e6-11eb-86cc-671b06e12218.png)

- Detail -> Display detail information about the game and add/remove the game from Favourites

![Detail](https://user-images.githubusercontent.com/22924857/111578477-cc9cfb80-87e6-11eb-91c3-51667abb5c24.png)
![Add to Favourites](https://user-images.githubusercontent.com/22924857/111578487-d161af80-87e6-11eb-93f8-839027af6f85.png)
![Delete from Favourites](https://user-images.githubusercontent.com/22924857/111578503-d7f02700-87e6-11eb-8450-098fe5bcd0b9.png)

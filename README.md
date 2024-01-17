# CatCompanionApp
CatCompanionApp 😼 is a Kotlin-based Android application built with Jetpack Compose, following the MVVM architecture. It utilizes the Cat API to provide users with a delightful experience exploring various cat breeds. The app features a clean and intuitive UI, allowing users to view cat images, search for breeds, mark favorites, and learn more about each breed's details. With offline functionality, error handling, and unit testing, CatCompanionApp is designed for both cat enthusiasts and developers seeking a well-structured Android app example.

The main purpose of this project is to develop my Android development skills, as well as to showcase my software architecture, planning and project execution skills.

> [!IMPORTANT]
> I'm not an Android developer and my knowledge of Android is limited to managing development teams. This is an opportunity to deepen my knowledge in this area, as well as to effectively update myself on the latest technologies available and the current state of Android development.

## The Challenge 🎯

Write an application using **Kotlin** that utilises the Cat API (https://thecatapi.com/). I can implement the UI as I prefer. I'm free to use any libraries that I consider appropriate solutions to my technical needs. Also, I should write a short readme explaining the strategies decided during the development of this challenge.

> [!NOTE]
> Tip: The Cat API provides OpenAPI Spec and Postman docs.

## The Product Requirements 📋

My Application should have the following functionalities:
1. A screen with a list of cat breeds, with the following info:
   - a. Cat image;
   - b. Breed name.
3. The cat breeds screen should contain a search bar to filter the list by breed name.
4. The cat breeds screen should contain a button to mark the breed as favourite.
5. Implement a new screen to show the breeds marked as favourites.
a. Show the average lifespan of all the favourite breeds (I can use either
the lower or the higher value in the range).
6. Implement a screen with a detailed view of a breed, with the following info:
   - a. Breed Name;
   - b. Origin;
   - c. Temperament;
   - d. Description;
   - e. A button to add/remove the breed from the favourites.
8. In order to navigate between the different screens I should use a Jetpack Navigation
Component.
9. Pressing on one of the list elements (be it in whatever screen) it should open the
detailed view of a breed.

## The Technical Requirements 🤖

* MVVM architecture
* Usage of Jetpack Compose for UI building
* Unit test coverage
* Offline functionality (i.e: Let's consider using [Room](https://developer.android.com/training/data-storage/room) for data persistence)

Bonus points:

* Error Handling
* Pagination for the list of cat breeds
* Modular design
* Integration and E2e tests

## Some Considerations 🤔

This app shoulb be evaluated on:
1. Implementation of the stated requirements
2. Application Architecture
3. The general quality of the code and it's resistance to crashing
4. My use of Android coding conventions
5. Knowledge and usage of Android libraries and SDKs
6. UI and UX -- I'm should not spend any time creating custom icons or other
assets, the app should look clean and generally obey the Human Interface
Guidelines

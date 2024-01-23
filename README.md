# CatCompanionApp
CatCompanionApp 😼 is a Kotlin-based Android application built with Jetpack Compose, following the MVVM architecture. It utilizes the Cat API to provide users with a delightful experience exploring various cat breeds. The app features a clean and intuitive UI, allowing users to view cat images, search for breeds, mark favorites, and learn more about each breed's details. With offline functionality, error handling, and unit testing, CatCompanionApp is designed for both cat enthusiasts and developers seeking a well-structured Android app example.

The main purpose of this project is to develop my Android development skills, as well as to showcase my software architecture, planning and project execution skills.

> [!IMPORTANT]
> I'm not an Android developer and my knowledge of Android is limited to managing development teams. This is an opportunity to deepen my knowledge in this area, as well as to effectively update myself on the latest technologies available and the current state of Android development.

## Main Decisions 📞

- **Choice of Android Studio Version:** Opted for the latest stable version, "Hedgehog | 2023.1.1 Patch 1," aligning with the official Android Studio releases as of January 2024.

- **Minimum SDK Version:** Set the minimum API level to 21, in accordance with the requirements specified in the Jetpack Compose setup guidelines.

- **Jetpack Navigation Component:** Incorporated the Jetpack Navigation Component to simplify navigation between screens, emphasizing the use of Navigation Graphs, NavHosts, NavControllers, Actions, Safe Args, Deep Linking, Animated Transitions, and Back Stack Management. The option was create a nested navigation strategy where main activity contains the home and detail screens and home screen itself contains the list breeds and the list favorites.

- **MVVM Architecture:** Adopted the MVVM (Model-View-ViewModel) architecture pattern, organizing code into model, viewmodel, repository, and view packages.

- **HTTP Requests:** Chose to make HTTP requests using the Retrofit library, leveraging its documentation, simplicity, and support within the Android community.

- **Offline and Database Support:** Selected Room as the persistence library for offline and database support, benefiting from its integration with LiveData and providing an abstraction layer over SQLite.

- **Programming Paradigms:** Embraced various programming paradigms, emphasizing the importance of procedural, object-oriented, functional, and declarative programming approaches, depending on the problem at hand.

- **Base Breed View Model:** Implemented a unified code base for View Models, utilizing inheritance to handle common features across different screens, reducing code duplication.

- **Declarative Component Approach:** Utilized the declarative component approach to simplify and clarify code, unifying components like the list of cat breeds, cat breed cards, and search result lines.

- **User Experience:** We decided to keep the favorites in the favorites list, even after they get removed until the list gets reload to allow users to revert a possible mistake where they would remove it by accident. Also there are some other considerations and improvements to be done, all listed on the [Wiki](https://github.com/andrefilipesilva73/cat-companion-app/wiki/4.-Results-and-Next-Steps-%F0%9F%8E%81%E2%8F%A9#improvements).

- **Integration and E2E Tests:** Implemented integration tests to verify interactions between different components and end-to-end tests to validate overall app functionality, simulating real user scenarios.

- **Continuous Integration:** Initiated continuous integration in every push to the "main" branch, ensuring the code is compilable; planned for future enhancements including Firebase Test Lab and Release Drafter.

- **Continuous Deployment:** Established continuous deployment on every tag on the "main" branch, uploading the APK to GitHub releases, with potential future expansions to Firebase App Distribution and Google Play Internal Testing.

## Full Documentation 📘

See the [Wiki](https://github.com/andrefilipesilva73/cat-companion-app/wiki) for full documentation, planning, execution, decisions, operational details and other information.

## Bugs and Feedback 🐞 🗣️

For bugs, questions and discussions please use the [GitHub Issues](https://github.com/andrefilipesilva73/cat-companion-app/issues).

## LICENSE 👨‍🎓

The source code for the site is licensed under the MIT license, which you can find in the [MIT-LICENSE.txt](MIT-LICENSE.txt) file.

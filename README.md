# 🍽️ MealMate – Android App (Kotlin + Jetpack Compose)

MealMate is a modern Android application developed using **Kotlin**, **Jetpack Compose**, **Retrofit**, **RxKotlin**, and **Koin**.  
It connects to **TheMealDB Public API** to fetch real-time meal and recipe data, displaying it in a clean, interactive UI with beautiful animations, shimmer loading effects, and structured details.

---

## 🧭 Overview

MealMate was built to demonstrate a complete end-to-end Android architecture — from data fetching and dependency injection to a reactive Compose UI layer.  
The project fetches data from a **public API**, renders **two separate datasets** (Meals & Categories) simultaneously, and displays **detailed meal information** with ingredients and recipes.

This project was created to satisfy the following **assignment requirements**:
> Build an Android app that fetches data from a public API and displays it in a list with detailed views.

---

## 🎯 Project Goals

1. **Use a Public API** to fetch real-world data (TheMealDB).
2. **Show multiple data lists** on the home screen using tabs.
3. **Render efficiently** using Jetpack Compose’s `LazyColumn`.
4. **Display shimmer loading animations** while fetching data.
5. **Implement clean MVVM architecture** with Dependency Injection (Koin).
6. **Handle API errors gracefully**, avoiding crashes.
7. **Use RxKotlin** (`Single.zip`) to perform **parallel API calls**.
8. **Create an appealing, professional UI** using Jetpack Compose.

---

## 📲 Application Flow

### 🏠 1. Start Screen
- Acts as the app’s landing page.
- Displays the app name “MealMate” with a gradient background.
- A **Start** button takes the user to the Home screen.
- UI created with custom gradient backgrounds and frosted card layouts.

### 🍴 2. Home Screen
- The core dashboard of the app.
- Displays **two datasets**:
  - Meals fetched using `search.php?f=a`
  - Categories fetched using `categories.php`
- Uses **Tabs** at the top to switch between “Meals” and “Categories”.
- Data fetched **simultaneously using RxKotlin’s `Single.zip()`**.
- During data loading, a **shimmer effect** placeholder animates on screen.
- Clicking on any meal or category navigates to its detailed view.

### 🍳 3. Detail Screen
- Displays complete details of a selected item:
  - Meal image, name, area, and category.
  - A **structured ingredients table** showing ingredient–measure pairs.
- A **“View Recipe”** button opens the Recipe Instructions page.
- Clean layout ensures readability and modern aesthetics.

### 📜 4. Recipe Screen
- Displays a well-formatted recipe guide.
- Each step is presented in a **frosted card** with a **colored step number box**.
- Steps are **justified**, visually aligned, and easy to read.
- Includes a **Share** button to share the recipe text.
- Fully consistent with the app’s theme and design language.

---

## ⚙️ Architecture & Design Pattern

MealMate follows the **MVVM (Model–View–ViewModel)** architecture pattern for better modularity, testability, and maintainability.

---

## 🌐 API Integration

The app consumes **TheMealDB public API**  
**Base URL:** `https://www.themealdb.com/api/json/v1/1/`

### Endpoints Used
| Purpose | Endpoint | Example |
|:--|:--|:--|
| Fetch Meals | `/search.php?f=a` | https://www.themealdb.com/api/json/v1/1/search.php?f=a |
| Fetch Categories | `/categories.php` | https://www.themealdb.com/api/json/v1/1/categories.php |
| Fetch Meal Details | `/lookup.php?i={id}` | https://www.themealdb.com/api/json/v1/1/lookup.php?i=52772 |

### Parallel Fetch Example (RxKotlin)
```kotlin
Single.zip(
    api.searchMealsByFirstLetter("a"),
    api.getCategories(),
    BiFunction { meals, categories ->
        Pair(meals, categories)
    }
)
```
## 🧠 Dependency Injection (Koin)
### Dependency injection ensures clean, decoupled code and easy testing.

## 🧾 Error Handling

### The app includes robust error handling for:
	•	Internet connectivity loss
	•	Empty API responses
	•	Parsing or network timeout errors

## 🎨 UI and Design Details
### •	Jetpack Compose: Entire UI is built using composables (no XML)
	• Color Theme: Gradient backgrounds (primary blue → violet) for consistent branding
	•	Typography: Bold headers, smooth rounded buttons, readable justified text
	•	Reusable UI Components:
	•	PrimaryButton
	•	SolidTopBar
	•	ShimmerList
	•	IngredientsTable
	•	StepCard
	•	Shimmer Effect: Custom modifier using animated gradient brush
	•	Frosted Cards: Soft transparency and rounded edges for modern aesthetics

  ## 💬 Future Scope
	•	Add search functionality to filter meals dynamically
	•	Enable bookmarking of favorite recipes
	•	Offline caching with Room or DataStore
	•	Dark theme toggle
	•	Animated transitions between screens


👨‍💻 Author

Your Name
🎓 B.Tech (CSE), VIT Bhopal University
💼 Android Developer | Kotlin | Compose | AI Integration

🌐 GitHub￼
🔗 LinkedIn￼

⸻

🪪 License

This project uses free, publicly available data from TheMealDB.com￼.
Source code © 2025 – Created for educational and demonstration purposes.

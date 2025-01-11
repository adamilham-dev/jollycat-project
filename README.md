# School Project: Catshop App "JollyCat"

JollyCat is an Android-based application that makes it easy for users to purchase and manage pet products, specifically for cats. This app is built using Kotlin, SQLite for local data storage, and integrates with Google Cloud Platform (GCP) for location-based map features.

---

## Main Features

1. **Cat Product Catalog**\
   Offers a variety of cat products such as food, accessories, and healthcare items with an attractive interface and easy navigation.

2. **Shopping Cart Management**\
   Users can add, remove, and edit items in the shopping cart before checkout.

3. **Location Feature with Google Maps**

   - Integration with Google Maps to display nearby store locations.
   - Store locations are displayed in real-time using Google Cloud Platform.

4. **Local Data Storage**\
   SQLite is used to store data such as product details, purchase history, and user information.

5. **Interactive Animations**\
   Displays engaging animations to enhance user experience.

---

## Technologies Used

- **Kotlin**: The primary programming language for Android app development.
- **SQLite**: Local database to store user and product data.
- **Google Cloud Platform (GCP)**:
  - Google Maps API for location features.
  - Google Cloud Console for managing API keys and related services.

---

## Installation and Configuration Steps

### 1. Clone the Repository

```bash
$ git clone https://github.com/adamilham-dev/catshop-app-training.git
$ cd catshop-app-training
```

### 2. Configure Google Maps API

1. Open the `local.properties` file in the `Gradle Scripts` folder.
2. Add your Google Maps API key:
   ```
   MAPS_API_KEY="AIzaSyDGLEMcn93z687HF6JbGPw3ap9CaZI52-0"
   ```
3. Ensure the SDK format is correct before adding the API key.

### 3. Run the Application

- Open the project in Android Studio.
- Select an emulator or physical device.
- Click the **Run** button (or `Shift + F10`).

---

## Upcoming Features

1. **Reward System**: Points for purchases that can be redeemed for discounts.
2. **Live Chat**: Feature to consult with veterinarians.
3. **Online Payment Integration**: Support for various payment methods.
4. **Publication on Play Store**: Expanding user reach.
5. And many more..

---

## Contribution

We welcome contributions to the development of this app. If you are interested, please create a pull request or email me at [adamilham3004@gmail.com](adamilham3004@gmail.com).

---

## External Documentation (in Bahasa)

For additional documentation, visit [this link](https://drive.google.com/file/d/1rvmUaFPgIWV4TVP0yb1j0581bW1tTagB/view?usp=sharing).

---

## License

This project is open-source code, so you can use it as learning purpose or your project template.


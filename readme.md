# Welcome to the Book Store API!
Welcome to the Book Store API, your ultimate solution for transforming the way you manage your bookstore, whether it's physical or online. With our powerful API, you can easily handle your book inventory, keep customer data organized, and conduct seamless sales transactions. Get ready to revolutionize your book business!

## Technologies and Tools Used
- **Spring Boot**: A scalable and easy-to-maintain application framework.
- **Hibernate**: Simplifies database interaction, making it intuitive.
- **Spring Security**: Ensures data protection and authorized access.
- **JWT (JSON Web Tokens)**: Provides secure authentication and authorization.
- **Spring Data JPA**: Effortlessly manages book data in the database.

- **Liquibase**: Manages database schema changes seamlessly.
- **Swagger**: Allows easy API exploration and interaction.
- **Docker**: Enables containerization for deployment and scalability.
- **Postman**: Offers a collection of requests for effective API testing.

## API Features
- **Book Management**: Efficiently discover books with our advanced search features. Browse by categories, authors, titles, or IDs. Our pagination support ensures seamless exploration of our extensive catalog without overwhelming search results. 
- **Shopping Cart**: Explore our diverse book collection and effortlessly add your favorites to the shopping cart. Enjoy the flexibility to review, edit, and finalize your selections before moving to checkout.
- **Order Management**: Experience hassle-free order management. Review your chosen books, add or remove items, and proceed with ease to complete your purchase.
- **Security**: Your security is our priority. We've implemented robust measures using Spring Security to protect your data and transactions. JWT (JSON Web Tokens) guarantee secure authentication and authorization, allowing access only to authorized users for sensitive functionalities.

## Controllers Functionalities

- **AuthenticationController**: Handles user registration and login requests, supporting both email and password authentication and JWT token-based authentication.
- **BookController**: Dedicated to tasks like adding, updating, retrieving, and searching for books.
- **CategoryController**: Manages requests for adding, updating, and retrieving categories, as well as fetching all books within a specific category.
- **ShoppingCartController**: Deals with actions such as adding, deleting, and updating books in the shopping cart, and retrieving the user's cart content.
- **OrderController**: Responsible for requests related to creating and retrieving orders, as well as updating order statuses.

# How to install and use API

#### Make sure you have the following requirements installed on your system before you start:
- [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Docker](https://docs.docker.com/get-docker/)
- [MySQL database](https://www.mysql.com/downloads/)

### Installation with docker

Here are the steps to set up and run the Book Store API locally:

1. **Clone the Repository:**

      ```shell
      git clone https://github.com/ArtemReizer/book-store.git
      ```
2. **Build with Maven:**
   ```shell
   mvn clean install
   ```
3. **Docker Image Creation:**
   ```shell
   docker build -t your-image-name .
   ```
4. **Docker Compose:**
   ```shell
   docker-compose build
   docker-compose up
   ```
   
### Installation without docker

Here are the steps to set up and run the Book Store API locally without docker:

1. **Clone the Repository:**
      ```shell
      git clone https://github.com/ArtemReizer/book-store.git
      ```
2. **Build with Maven:**
   ```shell
   mvn clean install
   ```
3. **Comment docker compose dependency:**

   Please comment out lines 99-102 in the pom.xml file and then reload all Maven projects. Ensure the dependency appears as follows:
   ```shell 
      <!--<dependency>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-docker-compose</artifactId>
       </dependency>-->
   ```
4. **Configure a DB on your local machine:**

   Create a *book_store* schema and update the username and password in *application.properties* with your credentials
      **Congratulations, you've successfully set up and launched the Book Store API on your local machine!**

## Postman Collection

To simplify your experience, I've curated a Postman collection containing sample requests for different API endpoints. Feel free to download it [here](Book%20Store.postman_collection.json) and import it into your Postman workspace. This will enable you to swiftly initiate your testing and exploration of the Book Store API. Happy testing!

## Challenges and Solutions

During the development of the Online Book Store project, we encountered several challenges that shaped our journey in creating a robust e-commerce platform. Let's delve into these challenges and the solutions we implemented to overcome them.

### Challenge 1: Data Modeling and Building Different Views

**Issue**: Designing a flexible data model for books, users, orders, and categories while ensuring users only see necessary data.

**Solution**: We utilized Spring Data JPA and Liquibase to create an adaptable schema. Additionally, we implemented a pattern Dto to present customized views of the data.

### Challenge 2: Security

**Issue**: Ensuring robust data security and user authentication to safeguard user information.

**Solution**: We implemented Spring Security and JWT tokens, providing a secure environment and reliable user authentication mechanism.

### Challenge 3: Exception Handling

**Issue**: Systematically handling errors and exceptions to improve user experience.

**Solution**: We developed a global error handler and custom exceptions, enhancing error reporting and ensuring a seamless user experience.

## Video presentation of the project [here](https://www.loom.com/share/9b42f2edb5394992aa39f8715dd2c467?sid=ccf1222b-12fd-4e6e-a746-c32ffa940a21) 

## Conclusion

The Bookstore API offers a robust framework for constructing a book-selling platform. Whether you're launching a new online bookstore or refining an existing one, this API streamlines the management of books, orders, and customers with great efficiency.

We appreciate your interest in the Book Store API! Thank you!

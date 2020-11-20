# Business analysis:
  - securing application!- done
  - endpoints to show:
    - [X] display all books
    - [x] get books by name
    - [X] get books by id
    - [x] crud for book
    - [ ] crud for cart
    - [x] get books by author
    - [ ] get all books on sale
    - [ ] list all orders by user 
    - [X] list all categories
    - [X] get all books in this category
    - [ ] list all shops
    - [ ] list shops where this book is
    - [ ] adding reviews and comments
    - [X] adding votes
  - entities:
    - [x] Book
    - [x] Author
    - [X] Cart
    - [ ] Order
    - [X] Additional Service ( services like book packaging )
    - [x] Publisher
    - [X] User
    - [ ] Admin
    - [X] Category of the book 
    - [ ] Shop
  - repositories:
    - [X] user repository
    - [X] book repository
    - [X] authors repository 
    - [X] publishers repository
    - [ ] orders repository
    - [X] cart repository 
    - [X] category repository
    - [ ] shop data repository
    - [ ] a possible feature to implement in the future: how many books are available in location
    
    
    
##Used design patterns:
  - [X] Singleton\
  com.bookshop.security.JwtAuthenticationFilter.doFilterInternal and com.bookshop.service.AuthService.getCurrentUser\
  Used in logger in BookService\
  SecurityContextHolder is an example of a Singleton class. It is static and has only one instance, because we need one instance of a class, which is holding our credentials.
  - [X] MVC  
  Standard Spring architecture with Controllers, Services and Repository layers
  - [X] Builder  
  Used in com.bookshop.service.AuthService.refreshToken and .login() to build an AuthenticationResponse 
  - [X] Dependency Injection  
   Used in com.bookshop.config.AppConfig. Part of a Spring Boot engine.
  - [X] Data mapper Pattern\
    According to Wikipedia "A Data Mapper is a Data Access Layer that performs bidirectional transfer of data between a persistent data store 
    (often a relational database), and an in-memory data representation (the domain layer)."\
    In my project whole com.bookshop.mapper package contains mapper classes, which map (Entities) DAO <-> DTO.
  - [X] Strategy\
  com.bookshop.security.JwtAuthenticationFilter- A filter is an object that performs filtering tasks on either the request to a resource (a servlet or static content), or on the response from a resource, or both.
  I create an instance of a Filter(JwtAuthenticationFilter) in security config, all the filters implement the Filter interface.\
  Filter is set to usage in com.bookshop.config.SecurityConfig class.
  Regard to "an example of strategy usage in JDK":\
  https://javadeveloper.pl/wzorzec-strategia/ \
  https://refactoring.guru/pl/design-patterns/strategy/java/example  
  implementation- paying for order.
  - [ ] Adapter\
  find a place to implement
  - [X] Factory\
  Creating a logger in com.bookshop.service.BookService
  - [ ] State\
  Of an order\
  User and admin filter?
  - [ ] Chain of responsibility\
  could be useful to research
  - [ ] Facade\
  get object (entity) from some service?
  
  
  
    
     


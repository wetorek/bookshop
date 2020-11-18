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
    https://www.freecodecamp.org/news/the-basic-design-patterns-all-developers-need-to-know/
  - [ ] singleton ?\
  ads
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
  - [ ] Strategy\
  https://refactoring.guru/pl/design-patterns/strategy/java/example  
  implementation- paying for order
  - [ ] Adapter\
  find a place to implement
  - [ ] Factory\
  Creating a logger in Bookservice\
  to create verification token in AuthService OOORRR... create a new cart?
  - [ ] State\
  Of an order\
  User and admin filter?
  - [ ] Chain of responsibility\
  could be useful to research
  - [ ] Facade\
  get object (entity) from some service?
  
  
  
    
     


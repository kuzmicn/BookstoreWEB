# Bookstore app
## Usage
The app simulates a Bookstore where users can buy books, review them, see other reviews and mark books as their favourites.
The app also has admin accounts that enables administrators to create sales report and manage books, genres and authors.
## Features
* Login and signup
* Oauth login with github
* Regular user
  * Book search by different criteria
    * Last search criteria and page are stored in session
  * Add books to cart
    * Cart is stored in session
  * Book buying
    * Bill is generated and sent to mail if mail is known
    * User can also download bill manually
  * Add book to favourites
  * View book
    * Show reviews
  * See another user favourites
  * Leave review for a book
* Admin
  * Add another admin
  * Create or update:
    * Books
    * Genres
    * Authors
  * Generate sales report
    * Report sent by mail
    * Option to download bill manually

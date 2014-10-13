Author: Xia Wu
Date: 10/13/2014

Summary:
In this assignment, I used URL class in the java.net package to fetch the web page. And I used an external library jsoup to parse the html. Basically, I analyzed how the query url should be composed and looked into the html file to locate the information I need.

How is the query composed?
The walmart query is composed in the following way: 
http://www.walmart.com/search/?query=[keyword1]%20[keyword2]%20...%20[keywordn]&page=[pagenum]
So I read the input arguments to fill the query address.

Determine query type?
Two kinds of queries need to be supported.
This was checked by how many arguments are input. If one argument is input, then the query is asking the number of results. If two arguments are input, then the query is asking for listing all the results.

How to load the HTML?
I used URL class to load the HTML.

How to parse the HTML?
I used an external library jsoup to parse the HTML.

How to get the number of results?
In the HTML file, the total number result info is warpped in a div with class of "result-summary-container". The text is "Showing num1 of num2 results". So the num2 is the number we want.

How to get all the results?
Firstly I created a class Result containing title and price to present an item.
Each of the item is wrapped in a div containing attribute "data-item-id". When we reached the item, the title is under class "js-product-title" and price is under class "tile-row". So a Result object is created for each item found, and it is added into an ArrayList object.

Note:
1. I tested on several examples, one thing happened once was that the content had been read before the prices are fully loaded. This was probably because the webpage's content is dynamically loaded based on the query url. Try to run the query again can solve this problem.
2. The program doesn't support redirecting after the query address. For example, if search "computer", walmart will redirect the page to a landing page for computer. The landing page's structure is totally different than normal query we need to deal with.
3. Since there are different types of prices listed in the items such as normal price, list price(final price can only be shown at check out), out of stock(no price shown), out of stock online(price still shown, but the item cannot be bought)... So I decided to print the price as well as special status. This can give full information to the query.
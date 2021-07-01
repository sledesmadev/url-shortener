# URL SHORTENER
Application for shorten urls and redirect them to the actual url.

# Spring Boot API with Angular UI
 
**Prerequisites:** [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) and [Node.js](https://nodejs.org/).

The database is created in H2

## Getting Started

To install this example application, run the following commands:

```bash
git clone https://github.com/sledesmadev/url-shortener.git
cd urlshortener
```

This will get a copy of the project installed locally. To install all of its dependencies and start each app, follow the instructions below.

To run the server, cd into the `server` folder and run:
 
```bash
./mvnw spring-boot:run
```

To run the client, cd into the `client` folder and run:
 
```bash
npm install && npm start
```

# API
Our Spring boot application will start at: http://localhost:8080

### POST - **/shorten**
The only required value of the requested body is url, alias can be null or empty.
```
{
  "url":<the full url that we want to be shortened>,
  "alias":<the custon name for our shortened url (optional)>
}
```
### Response
```
{
  "url":<It returns the shorten url>
}
```

### GET - **/{createdShortenedUrl}**
### Response
```
It redirects to the full url of the shortened url.
```


# Working example
Our angular application will start at: http://localhost:4200 </br>

![image](https://user-images.githubusercontent.com/70598604/124075171-c274d500-da44-11eb-9498-4f14998e34d8.png)
<br/>We have the following inputs:<br/>
**FULL URL field ->** Here we write the url we want to short, for example: https://www.neueda.com/</br>
**ALIAS field ->** Optional input where we will write our custom shorten link name, for example: __neueda__

![image](https://user-images.githubusercontent.com/70598604/124075601-6494bd00-da45-11eb-9aca-e0c8ece8d131.png)

If there is no error it will show our shorten URL:

![image](https://user-images.githubusercontent.com/70598604/124076247-48dde680-da46-11eb-9d15-31b12431d60c.png)

If we don't set a value for alias input, it will generate a random alphanumeric value of 8 positions.


# ISSUES

- Docker not implemented 
- Getting not existing Shortened URL doesn't return to main page in front-end (but spring returns error)
- Code coverage (some test missing, incomplete)












